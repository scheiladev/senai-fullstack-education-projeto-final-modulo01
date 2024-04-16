package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

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
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataEntrada;

  @OneToOne(optional = false, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "id_usuario", nullable = false, unique = true)
  private UsuarioEntity usuario;

}
