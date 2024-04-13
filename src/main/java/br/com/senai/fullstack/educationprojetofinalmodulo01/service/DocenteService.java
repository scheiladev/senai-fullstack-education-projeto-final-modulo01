package br.com.senai.fullstack.educationprojetofinalmodulo01.service;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.AlterarDocenteRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarDocenteRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.DocenteResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.DocenteEntity;

import java.util.List;

public interface DocenteService {

  List<DocenteResponse> buscarTodos(String token);

  DocenteResponse buscarPorId(Long id, String token);

  DocenteResponse cadastrar(CadastrarDocenteRequest request, String token);

  DocenteResponse alterar(Long id, AlterarDocenteRequest request, String token);

  void apagar(Long id, String token);

}