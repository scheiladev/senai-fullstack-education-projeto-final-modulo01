package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.LoginRequest;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

@Data
@Entity
@Table(name = "usuario")
public class UsuarioEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 120)
  private String login;

  @Column(nullable = false)
  private String senha;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_papel", nullable = false)
  private PapelEntity papel;

  public boolean validarSenha(LoginRequest loginRequest, BCryptPasswordEncoder bCryptEncoder) {
    return bCryptEncoder.matches(
      loginRequest.senha(),
      this.senha
    );
  }
}
