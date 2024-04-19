package br.com.senai.fullstack.educationprojetofinalmodulo01.service;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.NotaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.NotaResponse;

import java.util.List;

public interface NotaService {

  List<NotaResponse> buscarNotasPorAluno(Long id, String token);

  NotaResponse buscarPorId(Long id, String token);

  NotaResponse cadastrar(NotaRequest request, String token);

  NotaResponse alterar(Long id, NotaRequest request, String token);

  void apagar(Long id, String token);

}
