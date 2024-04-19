package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request;

import java.time.LocalDate;

public record CadastrarDocenteRequest(String nome, LocalDate dataEntrada, String usuario) {
}

