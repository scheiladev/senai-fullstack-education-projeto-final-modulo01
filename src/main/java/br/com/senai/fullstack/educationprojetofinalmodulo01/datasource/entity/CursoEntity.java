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

  @Column(nullable = false, unique = true)
  private String nome;

  @OneToMany(mappedBy = "curso", fetch = FetchType.EAGER)
  private List<TurmaEntity> turmas;

  @OneToMany(mappedBy = "curso", fetch = FetchType.EAGER)
  private List<MateriaEntity> materias;

}
