package com.senai.crud_veiculos_teste.implemente;

import com.senai.crud_veiculos_teste.entidades.Veiculo;
import com.senai.crud_veiculos_teste.repositorio.VeiculoRepositorio;
import com.senai.crud_veiculos_teste.service.VeiculoService;

import java.util.List;

public class VeiculoImpl implements VeiculoService {

    private VeiculoRepositorio veiculoRepositorio;

    @Override
    public Veiculo findById(Long id) {
        return veiculoRepositorio.findById(id).orElse(null);
    }

    @Override
    public Veiculo findByPlaca(String placa) {
        return veiculoRepositorio.findByPlaca(placa);
    }

    @Override
    public Veiculo create(Veiculo veiculo) {
        return  veiculoRepositorio.save(veiculo);
    }

    @Override
    public Veiculo update(Veiculo veiculo) {
        return veiculoRepositorio.save(veiculo);
    }

    @Override
    public void delete(Veiculo veiculo) {
        veiculoRepositorio.delete(veiculo);
    }

    @Override
    public List<Veiculo> findAll() {
        return veiculoRepositorio.findAll();
    }
}
