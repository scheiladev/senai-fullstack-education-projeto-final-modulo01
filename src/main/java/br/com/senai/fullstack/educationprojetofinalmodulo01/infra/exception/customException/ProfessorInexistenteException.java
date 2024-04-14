package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException;

public class ProfessorInexistenteException extends RuntimeException {

  public ProfessorInexistenteException() {
  }

  public ProfessorInexistenteException(String message) {
    super(message);
  }
}
