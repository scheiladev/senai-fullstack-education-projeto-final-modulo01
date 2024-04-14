package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository;

import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<NotaEntity, Long> {

  @Query(
    " select nota from NotaEntity nota " +
      " where nota.aluno.id = :id"
  )
  List<NotaEntity> findAllNotasByAlunoId(@Param("id") Long id);
}
