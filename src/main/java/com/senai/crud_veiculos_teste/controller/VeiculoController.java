package com.senai.crud_veiculos_teste.controller;

import com.senai.crud_veiculos_teste.entidades.Veiculo;
import com.senai.crud_veiculos_teste.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/veiculo")
@CrossOrigin(origins = "*")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public List<Veiculo> findAll() {
        return veiculoService.findAll();
    }

    @GetMapping("/{id}")
    public Veiculo findById(@PathVariable UUID id) {
        return veiculoService.findById(id);
    }

    @PostMapping
    public Veiculo create(@RequestBody Veiculo veiculo) {
        System.out.println("Criando veículo: " + veiculo);
        return veiculoService.create(veiculo);
    }

    @PutMapping("/{id}")
    public Veiculo update(@PathVariable UUID id, @RequestBody Veiculo veiculo) {
        veiculo.setId(id);
        System.out.println("Atualizando veículo: " + veiculo);
        return veiculoService.update(veiculo);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        veiculoService.delete(veiculoService.findById(id));
    }
}
