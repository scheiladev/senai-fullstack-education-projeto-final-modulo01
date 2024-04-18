package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception;

import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.*;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handler(Exception e) {
    log.error("[STATUS 500] Erro inesperado: {}, {}", e.getMessage(), e.getCause().getStackTrace());
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("500")
      .mensagem(e.getMessage())
      .build();
    return ResponseEntity.status(500).body(exceptionDto);
  }

  @ExceptionHandler(AcessoNaoAutorizadoException.class)
  public ResponseEntity<?> handler(AcessoNaoAutorizadoException e) {
    log.error("[STATUS 401] Credencial inválida: {}", e.getMessage());
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("401")
      .mensagem(e.getMessage())
      .build();
    return ResponseEntity.status(401).body(exceptionDto);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<?> handler(NoResourceFoundException e) {
    log.error("[STATUS 404] Recurso não encontrado: {}", e.getMessage());
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("404")
      .mensagem(e.getMessage())
      .build();
    return ResponseEntity.status(404).body(exceptionDto);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handler(NotFoundException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("404")
      .mensagem(e.getMessage())
      .build();
    log.error("[STATUS 404] Dados não encontrados: {}", e.getMessage());
    return ResponseEntity.status(404).body(exceptionDto);
  }

  @ExceptionHandler(UsuarioInvalidoException.class)
  public ResponseEntity<?> handle(UsuarioInvalidoException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("403")
      .mensagem(e.getMessage())
      .build();
    log.error("[STATUS 403] Usuário inválido: {}", e.getMessage());
    return ResponseEntity.status(403).body(exceptionDto);
  }

  @ExceptionHandler(ExclusaoNaoPermitidaException.class)
  public ResponseEntity<?> handle(ExclusaoNaoPermitidaException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("403")
      .mensagem(e.getMessage())
      .build();
    log.error("[STATUS 403] Exclusão não permitida: {}", e.getMessage());
    return ResponseEntity.status(403).body(exceptionDto);
  }

  @ExceptionHandler(CodigoInvalidoException.class)
  public ResponseEntity<?> handle(CodigoInvalidoException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("400")
      .mensagem(e.getMessage())
      .build();
    log.error("[STATUS 400] Código inválido: {}", e.getMessage());
    return ResponseEntity.status(400).body(exceptionDto);
  }

  @ExceptionHandler(RequisicaoInvalidaException.class)
  public ResponseEntity<?> handle(RequisicaoInvalidaException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("400")
      .mensagem(e.getMessage())
      .build();
    log.error("[STATUS 400] Dados ausentes ou incorretos: {}", e.getMessage());
    return ResponseEntity.status(400).body(exceptionDto);
  }

  @ExceptionHandler(ConflitoDeDadosException.class)
  public ResponseEntity<?> handle(ConflitoDeDadosException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("403")
      .mensagem(e.getMessage())
      .build();
    log.error("[STATUS 403] Conflito de dados: {}", e.getMessage());
    return ResponseEntity.status(400).body(exceptionDto);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handler(DataIntegrityViolationException e) {
    log.error("[STATUS 403] Violação de integridade: {}", e.getMessage());
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("403")
      .mensagem(e.getMessage())
      .build();
    return ResponseEntity.status(403).body(exceptionDto);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<?> handler(IllegalStateException e) {
    log.error("[STATUS 409] Erro ao gerar a solicitação: {}", e.getMessage());
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("409")
      .mensagem(e.getMessage())
      .build();
    return ResponseEntity.status(409).body(exceptionDto);
  }

  @ExceptionHandler(UnsupportedOperationException.class)
  public ResponseEntity<?> handler(UnsupportedOperationException e) {
    log.error("[STATUS 403] Operação não aceita: {}", e.getMessage());
    ExceptionDto exceptionDto = ExceptionDto.builder()
      .codigo("403")
      .mensagem(e.getMessage())
      .build();
    return ResponseEntity.status(403).body(exceptionDto);
  }

}
