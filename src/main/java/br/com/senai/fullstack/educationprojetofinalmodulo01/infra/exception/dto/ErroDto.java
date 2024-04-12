package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErroDto {
  private String codigo;
  private String mensagem;
}
