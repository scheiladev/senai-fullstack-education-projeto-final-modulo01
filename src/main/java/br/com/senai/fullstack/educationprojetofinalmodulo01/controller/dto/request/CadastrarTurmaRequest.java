package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request;

public record CadastrarTurmaRequest(String nome, Long professorId, Long cursoId) {
}

