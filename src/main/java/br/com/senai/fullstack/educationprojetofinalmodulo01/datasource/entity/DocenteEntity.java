package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity;

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
  private Date dataEntrada;

  @OneToOne(optional = false)
  @JoinColumn(name = "id_usuario", nullable = false, unique = true)
  private UsuarioEntity usuario;


}
