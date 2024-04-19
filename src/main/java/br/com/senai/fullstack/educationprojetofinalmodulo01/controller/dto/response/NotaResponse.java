package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response;

import java.time.LocalDate;

public record NotaResponse(Long id, String alunoNome, String professorNome, String materiaNome, Double valor, LocalDate data) {
}

