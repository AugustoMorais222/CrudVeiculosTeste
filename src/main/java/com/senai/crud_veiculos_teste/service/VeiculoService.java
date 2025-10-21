package com.senai.crud_veiculos_teste.service;

import com.senai.crud_veiculos_teste.entidades.Veiculo;

import java.util.List;

public interface VeiculoService {
    //crud
    Veiculo findById(Long id);

    Veiculo findByPlaca(String placa);

    Veiculo create(Veiculo veiculo);
    Veiculo update(Veiculo veiculo);
    void delete(Veiculo veiculo);
    List<Veiculo> findAll();

}
