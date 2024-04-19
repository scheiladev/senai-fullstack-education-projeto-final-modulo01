package br.com.senai.fullstack.educationprojetofinalmodulo01.controller;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarUsuarioRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.UsuarioResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.utils.JsonUtil;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class UsuarioController {

  private final UsuarioService usuarioService;

  @PostMapping("cadastro")
  public ResponseEntity<UsuarioResponse> cadastrarUsuario(
      @Validated @RequestBody CadastrarUsuarioRequest cadastrarUsuarioRequest,
      @RequestHeader(name = "Authorization") String token) {

    UsuarioResponse usuarioResponse = usuarioService.cadastrarUsuario(cadastrarUsuarioRequest, token.substring(7));
    log.info("POST /cadastro -> 201 CREATED");
    log.debug("POST /cadastro -> Response Body:\n{}", JsonUtil.objetoParaJson(usuarioResponse.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
  }

}

