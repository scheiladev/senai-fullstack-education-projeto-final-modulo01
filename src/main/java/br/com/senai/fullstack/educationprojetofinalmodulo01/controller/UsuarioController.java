package br.com.senai.fullstack.educationprojetofinalmodulo01.controller;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarUsuarioRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

  @PostMapping("cadastro")
  public ResponseEntity<String> cadastrarUsuario(@Validated @RequestBody CadastrarUsuarioRequest cadastrarUsuarioRequest) {
    usuarioService.cadastrarUsuario(cadastrarUsuarioRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso.");
  }

}
