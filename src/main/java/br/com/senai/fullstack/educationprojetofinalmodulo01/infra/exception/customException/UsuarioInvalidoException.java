package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException;

public class UsuarioInvalidoException extends RuntimeException {

  public UsuarioInvalidoException() {
  }

  public UsuarioInvalidoException(String message) {
    super(message);
  }

}
