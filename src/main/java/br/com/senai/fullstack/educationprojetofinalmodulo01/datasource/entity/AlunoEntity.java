package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;

  @OneToOne(optional = false, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "id_usuario", nullable = false, unique = true)
  private UsuarioEntity usuario;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_turma", nullable = false)
  private TurmaEntity turma;

  @OneToMany(mappedBy = "aluno")
  private List<NotaEntity> notas;

}
