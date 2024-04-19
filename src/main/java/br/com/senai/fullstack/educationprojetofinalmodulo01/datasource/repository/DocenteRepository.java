package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository;

import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {

  @Query(
    " select docente from DocenteEntity docente " +
    " join UsuarioEntity usuario on docente.usuario.id = usuario.id " +
    " join PapelEntity papel on usuario.papel.id = papel.id " +
    " where papel.nome = 'PROFESSOR'"
  )
  List<DocenteEntity> findAllDocentesWithPapelProfessor();

  @Query(
    " select docente from DocenteEntity docente " +
    " join UsuarioEntity usuario on docente.usuario.id = usuario.id " +
    " join PapelEntity papel on usuario.papel.id = papel.id " +
    " where papel.nome = 'PROFESSOR'" +
    " and docente.id = :id"
  )
  DocenteEntity findByIdWithPapelProfessor(@Param("id") Long id);

}

