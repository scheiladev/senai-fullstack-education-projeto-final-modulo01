package br.com.senai.fullstack.educationprojetofinalmodulo01.controller;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.MateriaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.MateriaResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.utils.JsonUtil;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.MateriaService;
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
public class MateriaController {

  private final MateriaService materiaService;

  @GetMapping("cursos/{id}/materias")
  public ResponseEntity<List<MateriaResponse>> buscarMateriasPorCurso(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    List<MateriaResponse> listaMaterias = materiaService.buscarMateriasPorCurso(id, token.substring(7));
    log.info("GET /cursos/{}/materias -> 200 OK", id);
    log.info("GET /cursos/{}/materias -> Foram encontrados {} registros", id, listaMaterias.size());
    log.debug("GET /cursos/{}/materias -> Response Body:\n{}", id, JsonUtil.objetoParaJson(listaMaterias.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(listaMaterias);
  }

  @GetMapping("materias/{id}")
  public ResponseEntity<MateriaResponse> buscarPorId(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    MateriaResponse materiaResponse = materiaService.buscarPorId(id, token.substring(7));
    log.info("GET /materias/{} -> 200 OK", id);
    log.debug("GET /materias/{} -> Response Body:\n{}", id, JsonUtil.objetoParaJson(materiaResponse.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(materiaResponse);
  }

  @PostMapping("materias")
  public ResponseEntity<MateriaResponse> cadastrarMateria(
      @Validated @RequestBody MateriaRequest materiaRequest,
      @RequestHeader(name = "Authorization") String token) {

    MateriaResponse materiaResponse = materiaService.cadastrar(materiaRequest, token.substring(7));
    log.info("POST /materias -> 201 CREATED");
    log.debug("POST /materias -> Response Body:\n{}", JsonUtil.objetoParaJson(materiaResponse.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(materiaResponse);
  }

  @PutMapping("materias/{id}")
  public ResponseEntity<MateriaResponse> alterarMateria(
      @PathVariable Long id,
      @Validated @RequestBody MateriaRequest materiaRequest,
      @RequestHeader(name = "Authorization") String token) {

    MateriaResponse materiaResponse = materiaService.alterar(id, materiaRequest, token.substring(7));
    log.info("PUT /materias/{} -> Atualizada com sucesso", id);
    log.info("PUT /materias/{} -> 200 OK", id);
    log.debug("PUT /materias/{} -> Response Body:\n{}", id, JsonUtil.objetoParaJson(materiaResponse.toString()));

    return ResponseEntity.status(HttpStatus.OK).body(materiaResponse);
  }

  @DeleteMapping("materias/{id}")
  public ResponseEntity<Void> apagarMateria(
      @PathVariable Long id,
      @RequestHeader(name = "Authorization") String token) {

    materiaService.apagar(id, token.substring(7));
    log.info("DELETE /materias/{} -> Excluída com sucesso", id);
    log.info("DELETE /materias/{} -> 204 NO CONTENT", id);

    return ResponseEntity.noContent().build();
  }

}
