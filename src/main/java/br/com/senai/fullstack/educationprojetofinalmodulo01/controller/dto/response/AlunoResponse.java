package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response;

import java.time.LocalDate;

public record AlunoResponse(Long id, String nome, LocalDate dataNascimento, Long turmaId, String usuario) {
}
