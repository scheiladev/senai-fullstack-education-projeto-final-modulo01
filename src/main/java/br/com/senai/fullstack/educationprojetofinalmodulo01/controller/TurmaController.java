package br.com.senai.fullstack.educationprojetofinalmodulo01.controller;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.AlterarTurmaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarTurmaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.TurmaResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.utils.JsonUtil;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TurmaService;
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
@RequestMapping("turmas")
public class TurmaController {

  private final TurmaService turmaService;

  @GetMapping
  public ResponseEntity<List<TurmaResponse>> buscarTodos(
      @RequestHeader(name = "Authorization") String token) {

    List<TurmaResponse> listaTurmas = turmaService.buscarTodos(token.substring(7));

    if (listaTurmas.isEmpty()) {
      log.info("GET /turmas -> 404 Não há turmas cadastradas");
    } else {
      log.info("GET /turmas -> 200 OK");
      log.info("GET /turmas -> Foram encontrados {} registros", listaTurmas.size());
      log.debug("GET /turmas -> Response Body:\n{}", JsonUtil.objetoParaJson(listaTurmas.toString()));
    }

    return ResponseEntity.status(HttpStatus.OK).body(listaTurmas);
  }

  @GetMapping("{id}")
  public ResponseEntity<TurmaResponse> buscarPorId(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    TurmaResponse turmaResponse = turmaService.buscarPorId(id, token.substring(7));

    if (turmaResponse == null) {
      log.info("GET /turmas -> 404 Turma não encontrada");
    } else {
      log.info("GET /turmas -> 200 OK");
      log.debug("GET /turmas -> Response Body:\n{}", JsonUtil.objetoParaJson(turmaResponse.toString()));
    }

    return ResponseEntity.status(HttpStatus.OK).body(turmaResponse);
  }

  @PostMapping
  public ResponseEntity<TurmaResponse> cadastrarTurma(
      @Validated @RequestBody CadastrarTurmaRequest cadastrarTurmaRequest,
      @RequestHeader(name = "Authorization") String token) {

    TurmaResponse turmaResponse = turmaService.cadastrar(cadastrarTurmaRequest, token.substring(7));
    log.info("POST /turmas -> 201 CREATED");
    log.debug("POST /turmas -> Response Body:\n{}", JsonUtil.objetoParaJson(turmaResponse.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(turmaResponse);
  }

  @PutMapping("{id}")
  public ResponseEntity<TurmaResponse> alterarTurma(
      @PathVariable Long id,
      @Validated @RequestBody AlterarTurmaRequest alterarTurmaRequest,
      @RequestHeader(name = "Authorization") String token) {

    TurmaResponse turmaResponse = turmaService.alterar(id, alterarTurmaRequest, token.substring(7));
    log.info("PUT /turmas/{} -> Atualizada com sucesso", id);
    log.info("PUT /turmas/{} -> 200 OK", id);
    log.debug("PUT /turmas/{} -> Response Body:\n{}", id, JsonUtil.objetoParaJson(turmaResponse.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(turmaResponse);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> apagarTurma(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    turmaService.apagar(id, token.substring(7));
    log.info("DELETE /turmas/{} -> Excluída com sucesso", id);
    log.info("DELETE /turmas/{} -> 204 NO CONTENT", id);

    return ResponseEntity.noContent().build();
  }

}
