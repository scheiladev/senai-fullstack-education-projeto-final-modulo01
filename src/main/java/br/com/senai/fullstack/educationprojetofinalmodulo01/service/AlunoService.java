package br.com.senai.fullstack.educationprojetofinalmodulo01.service;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.AlterarAlunoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarAlunoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.AlunoResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.PontuacaoResponse;

import java.util.List;

public interface AlunoService {

  List<AlunoResponse> buscarTodos(String token);

  AlunoResponse buscarPorId(Long id, String token);

  PontuacaoResponse buscarPontuacao(Long id, String token);

  AlunoResponse cadastrar(CadastrarAlunoRequest request, String token);

  AlunoResponse alterar(Long id, AlterarAlunoRequest request, String token);

  void apagar(Long id, String token);

}
