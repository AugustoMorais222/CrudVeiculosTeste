package com.senai.crud_veiculos_teste.service;

import com.senai.crud_veiculos_teste.entidades.Veiculo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface VeiculoService {
    Veiculo findById(UUID id);
    Veiculo create(Veiculo veiculo);
    Veiculo update(Veiculo veiculo);
    void delete(Veiculo veiculo);
    List<Veiculo> findAll();

}
