package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "curso")
public class CursoEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @OneToMany(mappedBy = "curso")
  private List<TurmaEntity> turmas;

  @OneToMany(mappedBy = "curso")
  private List<MateriaEntity> materias;
}
