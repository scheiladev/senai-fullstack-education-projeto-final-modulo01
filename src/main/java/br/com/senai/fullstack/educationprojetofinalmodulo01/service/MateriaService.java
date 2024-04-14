package br.com.senai.fullstack.educationprojetofinalmodulo01.service;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.MateriaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.MateriaResponse;

import java.util.List;

public interface MateriaService {

  List<MateriaResponse> buscarTodos(String token);

  MateriaResponse buscarPorId(Long id, String token);

  MateriaResponse cadastrar(MateriaRequest request, String token);

  MateriaResponse alterar(Long id, MateriaRequest request, String token);

  void apagar(Long id, String token);

}