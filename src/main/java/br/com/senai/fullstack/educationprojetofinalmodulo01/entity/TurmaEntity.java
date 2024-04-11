package br.com.senai.fullstack.educationprojetofinalmodulo01.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "turma")
public class TurmaEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @ManyToOne
  @JoinColumn(name = "docente_id", nullable = false)
  private DocenteEntity professor;

  @ManyToOne(optional = false)
  @JoinColumn(name = "curso_id", nullable = false)
  private CursoEntity id_curso;

  @OneToMany(mappedBy = "turma")
  private List<AlunoEntity> alunos;

}
