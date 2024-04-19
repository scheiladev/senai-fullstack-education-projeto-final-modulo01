package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository;

import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {

  @Query(
    " select materia from MateriaEntity materia " +
    " where materia.curso.id = :id"
  )
  List<MateriaEntity> findAllMateriasByCursoId(@Param("id") Long id);

  @Query(
    " select materia from MateriaEntity materia " +
    " join CursoEntity curso on curso.id = materia.curso.id" +
    " join TurmaEntity turma on turma.curso.id = curso.id" +
    " join AlunoEntity aluno on aluno.turma.id = turma.id" +
    " where aluno.id = :id"
  )
  List<MateriaEntity> findAllMateriasByCursoByAlunoId(@Param("id") Long id);

}

