package br.com.senai.fullstack.educationprojetofinalmodulo01.controller;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CursoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.CursoResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.utils.JsonUtil;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.CursoService;
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
@RequestMapping("cursos")
public class CursoController {

  private final CursoService cursoService;

  @GetMapping
  public ResponseEntity<List<CursoResponse>> buscarTodos(
      @RequestHeader(name = "Authorization") String token) {

    List<CursoResponse> listaCursos = cursoService.buscarTodos(token.substring(7));
    log.info("GET /cursos -> 200 OK");
    log.info("GET /cursos -> Foram encontrados {} registros", listaCursos.size());
    log.debug("GET /cursos -> Response Body:\n{}", JsonUtil.objetoParaJson(listaCursos.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(listaCursos);
  }

  @GetMapping("{id}")
  public ResponseEntity<CursoResponse> buscarPorId(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    CursoResponse cursoResponse = cursoService.buscarPorId(id, token.substring(7));
    log.info("GET /cursos/{} -> 200 OK", id);
    log.debug("GET /cursos/{} -> Response Body:\n{}", id, JsonUtil.objetoParaJson(cursoResponse.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(cursoResponse);
  }

  @PostMapping
  public ResponseEntity<CursoResponse> cadastrarCurso(
      @Validated @RequestBody CursoRequest cursoRequest,
      @RequestHeader(name = "Authorization") String token) {

    CursoResponse cursoResponse = cursoService.cadastrar(cursoRequest, token.substring(7));
    log.info("POST /cursos -> 201 CREATED");
    log.debug("POST /cursos -> Response Body:\n{}", JsonUtil.objetoParaJson(cursoResponse.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(cursoResponse);
  }

  @PutMapping("{id}")
  public ResponseEntity<CursoResponse> alterarCurso(
      @PathVariable Long id,
      @Validated @RequestBody CursoRequest cursoRequest,
      @RequestHeader(name = "Authorization") String token) {

    CursoResponse cursoResponse = cursoService.alterar(id, cursoRequest, token.substring(7));
    log.info("PUT /cursos/{} -> Atualizado com sucesso", id);
    log.info("PUT /cursos/{} -> 200 OK", id);
    log.debug("PUT /cursos/{} -> Response Body:\n{}", id, JsonUtil.objetoParaJson(cursoResponse.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(cursoResponse);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> apagarCurso(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    cursoService.apagar(id, token.substring(7));
    log.info("DELETE /cursos/{} -> Excluído com sucesso", id);
    log.info("DELETE /cursos/{} -> 204 NO CONTENT", id);

    return ResponseEntity.noContent().build();
  }

}
