package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException;

public class CodigoInvalidoException extends RuntimeException {

  public CodigoInvalidoException() {
  }

  public CodigoInvalidoException(String message) {
    super(message);
  }

}

