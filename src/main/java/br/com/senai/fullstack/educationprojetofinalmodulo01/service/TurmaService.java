package br.com.senai.fullstack.educationprojetofinalmodulo01.service;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.AlterarTurmaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarTurmaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.TurmaResponse;

import java.util.List;

public interface TurmaService {

  List<TurmaResponse> buscarTodos(String token);

  TurmaResponse buscarPorId(Long id, String token);

  TurmaResponse cadastrar(CadastrarTurmaRequest request, String token);

  TurmaResponse alterar(Long id, AlterarTurmaRequest request, String token);

  void apagar(Long id, String token);

}
