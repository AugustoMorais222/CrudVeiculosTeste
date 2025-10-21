package com.senai.crud_veiculos_teste.repositorio;

import com.senai.crud_veiculos_teste.entidades.Veiculo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Transactional
@Repository
public interface VeiculoRepositorio extends JpaRepository<Veiculo, UUID> {

    Veiculo findByPlaca(String placa);
}
