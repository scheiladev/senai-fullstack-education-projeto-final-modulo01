package br.com.senai.fullstack.educationprojetofinalmodulo01.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "notas")
public class NotaEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "aluno_id", nullable = false)
  private AlunoEntity id_aluno;

  @ManyToOne(optional = false)
  @JoinColumn(name = "docente_id", nullable = false)
  private DocenteEntity id_professor;

  @ManyToOne(optional = false)
  @JoinColumn(name = "materia_id", nullable = false)
  private MateriaEntity id_materia;

  @Column(nullable = false)
  private Double valor;

  @Column(nullable = false)
  private Date data;

}
