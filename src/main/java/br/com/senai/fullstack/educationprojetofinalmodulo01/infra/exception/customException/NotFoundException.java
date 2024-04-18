package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {
  }

  public NotFoundException(String message) {
    super(message);
  }

}
