package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception;

import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.RequisicaoInvalidaException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.NotFoundException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.dto.ErroDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handler(Exception e) {
    log.error("[STATUS 500] Erro inesperado: {}, {}", e.getMessage(), e.getCause().getStackTrace());
    ErroDto erroDto = ErroDto.builder()
      .codigo("500")
      .mensagem(e.getMessage())
      .build();
    return ResponseEntity.status(500).body(erroDto);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handler(NotFoundException e) {
    ErroDto erroDto = ErroDto.builder()
      .codigo("404")
      .mensagem(e.getMessage())
      .build();
    log.error("[STATUS 404] Dados não encontrados: {}", e.getMessage());
    return ResponseEntity.status(404).body(erroDto);
  }

  @ExceptionHandler(RequisicaoInvalidaException.class)
  public ResponseEntity<?> handle(RequisicaoInvalidaException e) {
    ErroDto erroDto = ErroDto.builder()
      .codigo("400")
      .mensagem(e.getMessage())
      .build();
    log.error("[STATUS 400] Dados ausentes ou incorretos: {}", e.getMessage());
    return ResponseEntity.status(400).body(erroDto);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handler(DataIntegrityViolationException e) {
    log.error("[STATUS 400] Violação de integridade: {}", e.getMessage());
    ErroDto erroDto = ErroDto.builder()
      .codigo("400")
      .mensagem(e.getMessage())
      .build();
    return ResponseEntity.status(400).body(erroDto);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<?> handler(IllegalStateException e) {
    log.error("[STATUS 409] Erro ao gerar a solicitação: {}", e.getMessage());
    ErroDto erroDto = ErroDto.builder()
      .codigo("409")
      .mensagem(e.getMessage())
      .build();
    return ResponseEntity.status(409).body(erroDto);
  }

  @ExceptionHandler(UnsupportedOperationException.class)
  public ResponseEntity<?> handler(UnsupportedOperationException e) {
    log.error("[STATUS 403] Operação não aceita: {}", e.getMessage());
    ErroDto erroDto = ErroDto.builder()
      .codigo("403")
      .mensagem(e.getMessage())
      .build();
    return ResponseEntity.status(403).body(erroDto);
  }
}
