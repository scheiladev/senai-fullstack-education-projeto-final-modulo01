package br.com.senai.fullstack.educationprojetofinalmodulo01.controller;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.AlterarDocenteRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarDocenteRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.DocenteResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.utils.JsonUtil;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.DocenteService;
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
@RequestMapping("docentes")
public class DocenteController {

  private final DocenteService docenteService;

  @GetMapping
  public ResponseEntity<List<DocenteResponse>> buscarTodos(
      @RequestHeader(name = "Authorization") String token) {

    List<DocenteResponse> listaDocentes = docenteService.buscarTodos(token.substring(7));
    log.info("GET /docentes -> 200 OK");
    log.info("GET /docentes -> Foram encontrados {} registros", listaDocentes.size());
    log.debug("GET /docentes -> Response Body:\n{}", JsonUtil.objetoParaJson(listaDocentes.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(listaDocentes);
  }

  @GetMapping("{id}")
  public ResponseEntity<DocenteResponse> buscarPorId(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    DocenteResponse docenteResponse = docenteService.buscarPorId(id, token.substring(7));
    log.info("GET /docentes/{} -> 200 OK", id);
    log.debug("GET /docentes/{} -> Response Body:\n{}", id, JsonUtil.objetoParaJson(docenteResponse.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(docenteResponse);
  }

  @PostMapping
  public ResponseEntity<DocenteResponse> cadastrarDocente(
      @Validated @RequestBody CadastrarDocenteRequest cadastrarDocenteRequest,
      @RequestHeader(name = "Authorization") String token) {

    DocenteResponse docenteResponse = docenteService.cadastrar(cadastrarDocenteRequest, token.substring(7));
    log.info("POST /docentes -> 201 CREATED");
    log.debug("POST /docentes -> Response Body:\n{}", JsonUtil.objetoParaJson(docenteResponse.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(docenteResponse);
  }

  @PutMapping("{id}")
  public ResponseEntity<DocenteResponse> alterarDocente(
      @PathVariable Long id,
      @Validated @RequestBody AlterarDocenteRequest alterarDocenteRequest,
      @RequestHeader(name = "Authorization") String token) {

    DocenteResponse docenteResponse = docenteService.alterar(id, alterarDocenteRequest, token.substring(7));
    log.info("PUT /docentes/{} -> Atualizado com sucesso", id);
    log.info("PUT /docentes/{} -> 200 OK", id);
    log.debug("PUT /docentes/{} -> Response Body:\n{}", id, JsonUtil.objetoParaJson(docenteResponse.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(docenteResponse);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> apagarDocente(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    docenteService.apagar(id, token.substring(7));
    log.info("DELETE /docentes/{} -> Excluído com sucesso", id);
    log.info("DELETE /docentes/{} -> 204 NO CONTENT", id);

    return ResponseEntity.noContent().build();
  }

}

