package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity;

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

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "id_professor", nullable = false)
  private DocenteEntity professor;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "id_curso", nullable = false)
  private CursoEntity curso;

  @OneToMany(mappedBy = "turma")
  private List<AlunoEntity> alunos;

}
