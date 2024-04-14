package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response;

import java.time.LocalDate;

public record DocenteResponse(Long id, String nome, LocalDate dataEntrada, String usuario, String papel) {
}
