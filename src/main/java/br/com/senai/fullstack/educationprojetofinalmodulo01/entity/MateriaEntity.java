package br.com.senai.fullstack.educationprojetofinalmodulo01.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "materia")
public class MateriaEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_curso", nullable = false)
  private CursoEntity curso;

}
