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
        // Configura o driver do Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Configura wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    static void tearDownAll() {
       if (driver != null) {
            driver.quit(); // Fecha o navegador
    }
    }

    // Abre a página principal e espera tabela carregar
    private void abrirPagina() {
        driver.get("http://localhost:8080/index.html");
        esperarPor(By.id("vehiclesTableBody"));
    }

    // Espera até que o elemento esteja visível
    private WebElement esperarPor(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    // Espera até que o texto apareça na tabela
    private void esperarTextoNaTabela(String texto) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("vehiclesTableBody"), texto));
    }

    // Espera até que o texto desapareça da tabela
    private void esperarTextoNaoExisteNaTabela(String texto) {
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElementLocated(By.id("vehiclesTableBody"), texto)
        ));
    }

    @Test
    @Order(1)
    @DisplayName("1️⃣ Criar veículo")
    void criarVeiculo() {
        abrirPagina();

        // Preenche o formulário
        esperarPor(By.name("descricao")).sendKeys("Carro de Teste");
        driver.findElement(By.name("fabricante")).sendKeys("Fiat");
        driver.findElement(By.name("placa")).sendKeys("ABC1234");
        driver.findElement(By.name("cor")).sendKeys("Vermelho");

        // Clica em Salvar
        driver.findElement(By.cssSelector("#createForm button[type='submit']")).click();

        // Espera a tabela atualizar com o novo veículo
        esperarTextoNaTabela("Carro de Teste");

        // Valida se o veículo foi criado
        WebElement linha = driver.findElement(By.xpath("//td[contains(text(),'Carro de Teste')]"));
        Assertions.assertNotNull(linha, "O veículo deve estar listado na tabela");
    }

    @Test
    @Order(2)
    @DisplayName("2️⃣ Editar veículo")
    void editarVeiculo() {
        abrirPagina();

        // Espera a tabela carregar
        esperarPor(By.id("vehiclesTableBody"));

        // Clica no botão Editar da primeira linha
        List<WebElement> botoesEditar = driver.findElements(By.cssSelector(".btn-outline-primary"));
        Assertions.assertFalse(botoesEditar.isEmpty(), "Deveria existir pelo menos um botão Editar");
        botoesEditar.get(0).click();

        // Espera o modal abrir
        WebElement modal = esperarPor(By.id("editModal"));

        // Edita todos os campos do modal
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

        // Salva
        driver.findElement(By.cssSelector("#editForm button[type='submit']")).click();

        // Espera o modal fechar (agora mais confiável)
        wait.until(ExpectedConditions.invisibilityOf(modal));

        // Espera a tabela atualizar com a nova descrição
        esperarTextoNaTabela("Carro Editado");

        // Valida se a tabela contém a descrição atualizada
        String tabela = driver.findElement(By.id("vehiclesTableBody")).getText();
        Assertions.assertTrue(tabela.contains("Carro Editado"), "A tabela deve conter o veículo editado");
        Assertions.assertTrue(tabela.contains("Volkswagen"), "A tabela deve conter o fabricante atualizado");
        Assertions.assertTrue(tabela.contains("XYZ9876"), "A tabela deve conter a placa atualizada");
        Assertions.assertTrue(tabela.contains("Azul"), "A tabela deve conter a cor atualizada");
    }


    @Test
    @Order(3)
    @DisplayName("3️⃣ Excluir veículo")
    void excluirVeiculo() {
        abrirPagina();

        // Espera a tabela carregar
        esperarPor(By.id("vehiclesTableBody"));

        // Localiza botão Excluir
        List<WebElement> botoesExcluir = driver.findElements(By.cssSelector(".btn-outline-danger"));
        Assertions.assertFalse(botoesExcluir.isEmpty(), "Deveria existir pelo menos um botão Excluir");

        // Clica em Excluir do primeiro registro
        botoesExcluir.get(0).click();

        // Confirma o alerta
        Alert alerta = wait.until(ExpectedConditions.alertIsPresent());
        alerta.accept();
    }
}
