package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "aluno")
public class AlunoEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private LocalDate dataNascimento;

  @OneToOne(optional = false)
  @JoinColumn(name = "id_usuario", nullable = false, unique = true)
  private UsuarioEntity usuario;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_turma", nullable = false)
  private TurmaEntity turma;
}
