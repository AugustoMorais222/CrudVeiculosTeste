package com.senai.crud_veiculos_teste;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "${info.build.name}", version = "${info.build.version}", description = "${info.app.description}", contact = @io.swagger.v3.oas.annotations.info.Contact(name = "Time Arquitetura e APIs", email = "Marcosantoniop47@gmail.com")))
@SpringBootApplication
public class CrudVeiculosTesteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudVeiculosTesteApplication.class, args);
	}

}
