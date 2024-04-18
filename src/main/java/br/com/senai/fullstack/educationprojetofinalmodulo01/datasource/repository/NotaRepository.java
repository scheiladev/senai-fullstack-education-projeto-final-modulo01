package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository;

import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<NotaEntity, Long> {

  List<NotaEntity> findAllNotasByAlunoId(Long id);

  @Query(
    "select count(*) > 0 " +
    " from AlunoEntity aluno " +
    " join TurmaEntity turma on turma.id = aluno.turma.id " +
    " join DocenteEntity professor on professor.id = turma.professor.id " +
    " join MateriaEntity materia on materia.curso.id = turma.curso.id " +
    " where aluno.id = :alunoId "+
    " and professor.id = :professorId"+
    " and materia.id = :materiaId"
  )
  boolean existsByAlunoAndProfessorAndMateria(Long alunoId, Long professorId, Long materiaId);

}
