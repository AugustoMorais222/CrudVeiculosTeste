package com.senai.crud_veiculos_teste.controller;

import com.senai.crud_veiculos_teste.entidades.Veiculo;
import com.senai.crud_veiculos_teste.repositorio.VeiculoRepositorio;
import com.senai.crud_veiculos_teste.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/veiculo")
@CrossOrigin("*")
public class VeiculoController {

    @Autowired
    public VeiculoService veiculoService;

    @GetMapping
    public List<Veiculo> findAll() {
        return veiculoService.findAll();
    }

    @PostMapping
    public Veiculo create(Veiculo veiculo) {
        return veiculoService.create(veiculo);
    }
    
    @PutMapping
    public Veiculo update(Veiculo veiculo) {
        return veiculoService.update(veiculo);
    }
    @GetMapping("/{id}")
    public Veiculo findById(@PathVariable UUID id) {
        return veiculoService.findById(id);
    }
    @DeleteMapping
    public void deleteById(@PathVariable UUID id) {
        veiculoService.delete(veiculoService.findById(id));
    }
}
