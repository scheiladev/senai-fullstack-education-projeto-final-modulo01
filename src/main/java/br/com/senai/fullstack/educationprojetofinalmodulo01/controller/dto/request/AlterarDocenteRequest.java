package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request;

import java.time.LocalDate;

public record AlterarDocenteRequest(Long id, String nome, LocalDate dataEntrada, String usuario) {
}

