package br.com.senai.fullstack.educationprojetofinalmodulo01.controller;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.NotaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.NotaResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.utils.JsonUtil;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.NotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class NotaController {

  private final NotaService notaService;

  @GetMapping("alunos/{id}/notas")
  public ResponseEntity<List<NotaResponse>> buscarNotasPorAluno(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    List<NotaResponse> listaNotas = notaService.buscarNotasPorAluno(id, token.substring(7));
    log.info("GET /alunos/{}/notas -> 200 OK", id);
    log.info("GET /alunos/{}/notas -> Foram encontrados {} registros", id, listaNotas.size());
    log.debug("GET /alunos/{}/notas -> Response Body:\n{}", id, JsonUtil.objetoParaJson(listaNotas.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(listaNotas);
  }

  @GetMapping("notas/{id}")
  public ResponseEntity<NotaResponse> buscarPorId(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    NotaResponse notaResponse = notaService.buscarPorId(id, token.substring(7));
    log.info("GET /notas/{} -> 200 OK", id);
    log.debug("GET /notas/{} -> Response Body:\n{}", id, JsonUtil.objetoParaJson(notaResponse.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(notaResponse);
  }

  @PostMapping("notas")
  public ResponseEntity<NotaResponse> cadastrarNota(
      @Validated @RequestBody NotaRequest notaRequest,
      @RequestHeader(name = "Authorization") String token) {

    NotaResponse notaResponse = notaService.cadastrar(notaRequest, token.substring(7));
    log.info("POST /notas -> 201 CREATED");
    log.debug("POST /notas -> Response Body:\n{}", JsonUtil.objetoParaJson(notaResponse.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(notaResponse);
  }

  @PutMapping("notas/{id}")
  public ResponseEntity<NotaResponse> alterarNota(
      @PathVariable Long id,
      @Validated @RequestBody NotaRequest notaRequest,
      @RequestHeader(name = "Authorization") String token) {

    NotaResponse notaResponse = notaService.alterar(id, notaRequest, token.substring(7));
    log.info("PUT /notas/{} -> Atualizada com sucesso", id);
    log.info("PUT /notas/{} -> 200 OK", id);
    log.debug("PUT /notas/{} -> Response Body:\n{}", id, JsonUtil.objetoParaJson(notaResponse.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(notaResponse);
  }

  @DeleteMapping("notas/{id}")
  public ResponseEntity<Void> apagarNota(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    notaService.apagar(id, token.substring(7));
    log.info("DELETE /notas/{} -> Excluída com sucesso", id);
    log.info("DELETE /notas/{} -> 204 NO CONTENT", id);

    return ResponseEntity.noContent().build();
  }

}
