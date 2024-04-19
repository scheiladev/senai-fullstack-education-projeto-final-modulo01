package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request;

public record AlterarTurmaRequest(Long id, String nome, Long professorId, Long cursoId) {
}

