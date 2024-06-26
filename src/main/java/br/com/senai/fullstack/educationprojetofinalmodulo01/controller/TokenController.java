package br.com.senai.fullstack.educationprojetofinalmodulo01.controller;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.LoginRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.LoginResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TokenController {

  private final TokenService tokenService;

  @PostMapping("login")
  public ResponseEntity<LoginResponse> gerarToken(
      @RequestBody LoginRequest loginRequest) {

    LoginResponse loginResponse = tokenService.gerarToken(loginRequest);
    log.info("POST /login -> 200 Login bem sucedido");

    return ResponseEntity.ok(loginResponse);
  }

}


