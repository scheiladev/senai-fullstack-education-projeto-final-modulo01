package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException;

public class ExclusaoNaoPermitidaException extends RuntimeException {

  public ExclusaoNaoPermitidaException() {
  }

  public ExclusaoNaoPermitidaException(String message) {
    super(message);
  }

}

