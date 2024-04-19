package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository;

import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {

  @Query(
    " select turma " +
    " from TurmaEntity turma " +
    " where turma.curso.id = :cursoId " +
    " and turma.nome = :nomeTurma"
  )
  List<TurmaEntity> findAllCursoIdAndNomeTurma(@Param("cursoId") Long cursoId, @Param("nomeTurma") String nomeTurma);

}

