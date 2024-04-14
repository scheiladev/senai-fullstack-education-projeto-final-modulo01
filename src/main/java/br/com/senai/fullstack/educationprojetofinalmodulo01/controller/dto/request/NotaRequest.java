package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request;

import java.time.LocalDate;

public record NotaRequest(Long alunoId, Long professorId, Long materiaId, Double valor, LocalDate data) {
}
