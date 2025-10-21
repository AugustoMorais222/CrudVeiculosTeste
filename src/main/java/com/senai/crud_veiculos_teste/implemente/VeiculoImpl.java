package com.senai.crud_veiculos_teste.implemente;

import com.senai.crud_veiculos_teste.entidades.Veiculo;
import com.senai.crud_veiculos_teste.repositorio.VeiculoRepositorio;
import com.senai.crud_veiculos_teste.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class VeiculoImpl implements VeiculoService {

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    public Veiculo findById(UUID id) {
        return veiculoRepositorio.findById(id).orElse(null);
    }

    public Veiculo findByPlaca(String placa) {
        return veiculoRepositorio.findByPlaca(placa);
    }

    public Veiculo create(Veiculo veiculo) {
        return  veiculoRepositorio.save(veiculo);
    }

    public Veiculo update(Veiculo veiculo) {
        return veiculoRepositorio.save(veiculo);
    }

    public void delete(Veiculo veiculo) {
        veiculoRepositorio.delete(veiculo);
    }

    public List<Veiculo> findAll() {
        return veiculoRepositorio.findAll();
    }
}
