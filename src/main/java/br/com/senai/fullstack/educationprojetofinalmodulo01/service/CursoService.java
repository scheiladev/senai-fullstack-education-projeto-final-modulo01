package br.com.senai.fullstack.educationprojetofinalmodulo01.service;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CursoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.CursoResponse;

import java.util.List;

public interface CursoService {

  List<CursoResponse> buscarTodos(String token);

  CursoResponse buscarPorId(Long id, String token);

  CursoResponse cadastrar(CursoRequest request, String token);

  CursoResponse alterar(Long id, CursoRequest request, String token);

  void apagar(Long id, String token);

}
