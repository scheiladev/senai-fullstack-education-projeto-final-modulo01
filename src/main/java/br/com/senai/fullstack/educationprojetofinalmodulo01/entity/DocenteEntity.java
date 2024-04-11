package br.com.senai.fullstack.educationprojetofinalmodulo01.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "docente")
public class DocenteEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private Date data_entrada;

  @OneToOne(optional = false)
  @JoinColumn(name = "usuario_id", nullable = false, unique = true)
  private UsuarioEntity id_usuario;


}
