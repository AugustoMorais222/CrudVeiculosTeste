package com.senai.crud_veiculos_teste.repositorio;

import com.senai.crud_veiculos_teste.entidades.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {

    Veiculo findByPlaca(String placa);
}
