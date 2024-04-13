package br.com.senai.fullstack.educationprojetofinalmodulo01.service;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.LoginRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.LoginResponse;

public interface TokenService {

  LoginResponse gerarToken(LoginRequest loginRequest);

  String buscarCampo(String token, String claim);
}
