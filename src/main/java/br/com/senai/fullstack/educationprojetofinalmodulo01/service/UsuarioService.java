package br.com.senai.fullstack.educationprojetofinalmodulo01.service;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarUsuarioRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.UsuarioResponse;

public interface UsuarioService {

  UsuarioResponse cadastrarUsuario(CadastrarUsuarioRequest cadastrarUsuarioRequest, String token);
}
