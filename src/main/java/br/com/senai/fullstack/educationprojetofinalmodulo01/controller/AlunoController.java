package br.com.senai.fullstack.educationprojetofinalmodulo01.controller;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.AlterarAlunoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarAlunoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.AlunoResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.PontuacaoResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.utils.JsonUtil;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.AlunoService;
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
@RequestMapping("alunos")
public class AlunoController {

  private final AlunoService alunoService;

  @GetMapping
  public ResponseEntity<List<AlunoResponse>> buscarTodos(
      @RequestHeader(name = "Authorization") String token) {

    List<AlunoResponse> listaAlunos = alunoService.buscarTodos(token.substring(7));

    if (listaAlunos.isEmpty()) {
      log.info("GET /alunos -> 404 Não há alunos cadastrados");
    } else {
      log.info("GET /alunos -> 200 OK");
      log.info("GET /alunos -> Foram encontrados {} registros", listaAlunos.size());
      log.debug("GET /alunos -> Response Body:\n{}", JsonUtil.objetoParaJson(listaAlunos.toString()));
    }

    return ResponseEntity.status(HttpStatus.OK).body(listaAlunos);
  }

  @GetMapping("{id}")
  public ResponseEntity<AlunoResponse> buscarPorId(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    AlunoResponse alunoResponse = alunoService.buscarPorId(id, token.substring(7));

    if (alunoResponse == null) {
      log.info("GET /alunos -> 404 Aluno não encontrado");
    } else {
      log.info("GET /alunos -> 200 OK");
      log.debug("GET /alunos -> Response Body:\n{}", JsonUtil.objetoParaJson(alunoResponse.toString()));
    }

    return ResponseEntity.status(HttpStatus.OK).body(alunoResponse);
  }

  @GetMapping("{id}/pontuacao")
  public ResponseEntity<PontuacaoResponse> buscarPontuacao(
    @PathVariable Long id,
    @RequestHeader(name = "Authorization") String token) {

    PontuacaoResponse pontuacaoResponse = alunoService.buscarPontuacao(id, token.substring(7));

    if (pontuacaoResponse == null) {
      log.info("GET /alunos -> 404 Aluno não encontrado");
    } else {
      log.info("GET /alunos -> 200 OK");
      log.debug("GET /alunos -> Response Body:\n{}", JsonUtil.objetoParaJson(pontuacaoResponse.toString()));
    }

    return ResponseEntity.status(HttpStatus.OK).body(pontuacaoResponse);
  }

  @PostMapping
  public ResponseEntity<AlunoResponse> cadastrarAluno(
      @Validated @RequestBody CadastrarAlunoRequest cadastrarAlunoRequest,
      @RequestHeader(name = "Authorization") String token) {

    AlunoResponse alunoResponse = alunoService.cadastrar(cadastrarAlunoRequest, token.substring(7));
    log.info("POST /alunos -> 201 CREATED");
    log.debug("POST /alunos -> Response Body:\n{}", JsonUtil.objetoParaJson(alunoResponse.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(alunoResponse);
  }

  @PutMapping("{id}")
  public ResponseEntity<AlunoResponse> alterarAluno(
      @PathVariable Long id,
      @Validated @RequestBody AlterarAlunoRequest alterarAlunoRequest,
      @RequestHeader(name = "Authorization") String token) {

    AlunoResponse alunoResponse = alunoService.alterar(id, alterarAlunoRequest, token.substring(7));
    log.info("PUT /alunos/{} -> Atualizado com sucesso", id);
    log.info("PUT /alunos/{} -> 200 OK", id);
    log.debug("PUT /alunos/{} -> Response Body:\n{}", id, JsonUtil.objetoParaJson(alunoResponse.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(alunoResponse);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> apagarAluno(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    alunoService.apagar(id, token.substring(7));
    log.info("DELETE /alunos/{} -> Excluído com sucesso", id);
    log.info("DELETE /alunos/{} -> 204 NO CONTENT", id);

    return ResponseEntity.noContent().build();
  }

}
