package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDto {
  private String codigo;
  private String mensagem;
}

