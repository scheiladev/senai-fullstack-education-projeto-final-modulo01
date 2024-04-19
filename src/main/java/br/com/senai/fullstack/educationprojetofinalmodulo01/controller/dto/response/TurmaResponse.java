package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response;

public record TurmaResponse(Long id, String nome, Long professorId, String professorNome, Long cursoId, String cursoNome) {
}

