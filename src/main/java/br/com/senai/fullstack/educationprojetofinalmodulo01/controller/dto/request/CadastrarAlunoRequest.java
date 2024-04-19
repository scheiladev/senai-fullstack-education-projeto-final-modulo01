package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request;

import java.time.LocalDate;

public record CadastrarAlunoRequest(String nome, LocalDate dataNascimento, Long turmaId, String usuario) {
}

