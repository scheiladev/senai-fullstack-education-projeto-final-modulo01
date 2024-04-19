package br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository;

import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<NotaEntity, Long> {

  List<NotaEntity> findAllNotasByAlunoId(Long id);

}

