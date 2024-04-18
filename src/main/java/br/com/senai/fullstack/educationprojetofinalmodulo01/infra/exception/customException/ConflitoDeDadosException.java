package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException;

public class ConflitoDeDadosException extends RuntimeException {

  public ConflitoDeDadosException() {
  }

  public ConflitoDeDadosException(String message) {
    super(message);
  }

}

