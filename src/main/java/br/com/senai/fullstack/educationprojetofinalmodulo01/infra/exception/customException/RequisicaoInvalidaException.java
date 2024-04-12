package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException;

public class RequisicaoInvalidaException extends RuntimeException {

  public RequisicaoInvalidaException() {
  }

  public RequisicaoInvalidaException(String message) {
    super(message);
  }
}
