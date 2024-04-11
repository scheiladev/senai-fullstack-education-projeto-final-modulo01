package br.com.senai.fullstack.educationprojetofinalmodulo01.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "nota")
public class NotaEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_aluno", nullable = false)
  private AlunoEntity aluno;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_professor", nullable = false)
  private DocenteEntity professor;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_materia", nullable = false)
  private MateriaEntity materia;

  @Column(nullable = false)
  private Double valor;

  @Column(nullable = false)
  private Date data;

}
