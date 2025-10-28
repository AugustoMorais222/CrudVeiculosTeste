package com.senai.crud_veiculos_teste;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CrudVeiculosTesteApplicationTests {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    static void tearDownAll() {
       if (driver != null) {
            driver.quit();
    }
    }

    private void abrirPagina() {
        driver.get("http://localhost:8080/index.html");
        esperarPor(By.id("vehiclesTableBody"));
    }

    private WebElement esperarPor(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private void esperarTextoNaTabela(String texto) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("vehiclesTableBody"), texto));
    }

    private void esperarTextoNaoExisteNaTabela(String texto) {
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElementLocated(By.id("vehiclesTableBody"), texto)
        ));
    }

    @Test
    @Order(1)
    @DisplayName("Criar veículo")
    void criarVeiculo() {
        abrirPagina();

        esperarPor(By.name("descricao")).sendKeys("Carro de Teste");
        driver.findElement(By.name("fabricante")).sendKeys("Fiat");
        driver.findElement(By.name("placa")).sendKeys("ABC1234");
        driver.findElement(By.name("cor")).sendKeys("Vermelho");

        driver.findElement(By.cssSelector("#createForm button[type='submit']")).click();

        esperarTextoNaTabela("Carro de Teste");

        WebElement linha = driver.findElement(By.xpath("//td[contains(text(),'Carro de Teste')]"));
        Assertions.assertNotNull(linha, "O veículo deve estar listado na tabela");
    }

    @Test
    @Order(2)
    @DisplayName("Editar veículo")
    void editarVeiculo() {
        abrirPagina();

        esperarPor(By.id("vehiclesTableBody"));

        List<WebElement> botoesEditar = driver.findElements(By.cssSelector(".btn-outline-primary"));
        Assertions.assertFalse(botoesEditar.isEmpty(), "Deveria existir pelo menos um botão Editar");
        botoesEditar.get(0).click();

        WebElement modal = esperarPor(By.id("editModal"));

        WebElement descricaoInput = esperarPor(By.name("descricao_modal"));
        descricaoInput.clear();
        descricaoInput.sendKeys("Carro Editado");

        WebElement fabricanteInput = driver.findElement(By.name("fabricante_modal"));
        fabricanteInput.clear();
        fabricanteInput.sendKeys("Volkswagen");

        WebElement placaInput = driver.findElement(By.name("placa_modal"));
        placaInput.clear();
        placaInput.sendKeys("XYZ9876");

        WebElement corInput = driver.findElement(By.name("cor_modal"));
        corInput.clear();
        corInput.sendKeys("Azul");

        driver.findElement(By.cssSelector("#editForm button[type='submit']")).click();

        wait.until(ExpectedConditions.invisibilityOf(modal));

        esperarTextoNaTabela("Carro Editado");

        String tabela = driver.findElement(By.id("vehiclesTableBody")).getText();
        Assertions.assertTrue(tabela.contains("Carro Editado"), "A tabela deve conter o veículo editado");
        Assertions.assertTrue(tabela.contains("Volkswagen"), "A tabela deve conter o fabricante atualizado");
        Assertions.assertTrue(tabela.contains("XYZ9876"), "A tabela deve conter a placa atualizada");
        Assertions.assertTrue(tabela.contains("Azul"), "A tabela deve conter a cor atualizada");
    }


    @Test
    @Order(3)
    @DisplayName("Excluir veículo")
    void excluirVeiculo() {
        abrirPagina();

        esperarPor(By.id("vehiclesTableBody"));

        wait.until(ExpectedConditions.not(
            ExpectedConditions.textToBePresentInElementLocated(
                By.id("vehiclesTableBody"), 
                "Carregando..."
            )
        ));

        List<WebElement> linhasAntes = driver.findElements(
            By.cssSelector("#vehiclesTableBody tr")
        );
        int quantidadeAntes = linhasAntes.size();

        Assertions.assertTrue(quantidadeAntes > 0, 
            "Deveria existir pelo menos um veículo para excluir");

        WebElement primeiraLinha = linhasAntes.get(0);
        String idVeiculoExcluido = primeiraLinha.findElement(
            By.cssSelector("td:nth-child(1)")
        ).getText();
        String descricaoExcluida = primeiraLinha.findElement(
            By.cssSelector("td:nth-child(2)")
        ).getText();

        List<WebElement> botoesExcluir = driver.findElements(
            By.cssSelector(".btn-outline-danger")
        );
        Assertions.assertFalse(botoesExcluir.isEmpty(), 
            "Deveria existir pelo menos um botão Excluir");

        botoesExcluir.get(0).click();

        Alert confirmacao = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertTrue(
            confirmacao.getText().contains("Excluir"), 
            "Deveria mostrar mensagem de confirmação"
        );
        confirmacao.accept();

        wait.until(ExpectedConditions.not(
            ExpectedConditions.textToBePresentInElementLocated(
                By.id("vehiclesTableBody"), 
                "Carregando..."
            )
        ));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<WebElement> linhasDepois = driver.findElements(
            By.cssSelector("#vehiclesTableBody tr")
        );
        int quantidadeDepois = linhasDepois.size();

        Assertions.assertEquals(quantidadeAntes - 1, quantidadeDepois,
            "A quantidade de veículos deveria ter diminuído em 1");

        boolean veiculoAindaExiste = linhasDepois.stream()
            .anyMatch(linha -> {
                String id = linha.findElement(By.cssSelector("td:nth-child(1)")).getText();
                return id.equals(idVeiculoExcluido);
            });

        Assertions.assertFalse(veiculoAindaExiste,
            "O veículo com ID " + idVeiculoExcluido + " ainda existe na tabela");

    }
}
