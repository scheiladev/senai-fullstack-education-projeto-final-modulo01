package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request;

import java.time.LocalDate;

public record AlterarAlunoRequest(Long id, String nome, LocalDate dataNascimento, Long turmaId, String usuario) {
}
