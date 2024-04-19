package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException;

public class AcessoNaoAutorizadoException extends RuntimeException {

  public AcessoNaoAutorizadoException() {
  }

  public AcessoNaoAutorizadoException(String message) {
    super(message);
  }

}

