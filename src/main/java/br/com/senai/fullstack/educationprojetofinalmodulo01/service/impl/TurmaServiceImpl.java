package br.com.senai.fullstack.educationprojetofinalmodulo01.service.impl;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.AlterarTurmaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarTurmaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.TurmaResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.CursoEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.DocenteEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.TurmaEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.CursoRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.TurmaRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.DocenteRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.*;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TurmaService;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurmaServiceImpl implements TurmaService {

  private final TurmaRepository turmaRepository;
  private final DocenteRepository docenteRepository;
  private final CursoRepository cursoRepository;
  private final TokenService tokenService;

  @Override
  public List<TurmaResponse> buscarTodos(String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    List<TurmaEntity> listaTurmas = turmaRepository.findAll();

    if (listaTurmas.isEmpty()) {
      throw new NotFoundException("Não há turmas cadastradas.");
    }

    return listaTurmas.stream()
      .map(turma -> new TurmaResponse(
        turma.getId(),
        turma.getNome(),
        turma.getProfessor().getId(),
        turma.getProfessor().getNome(),
        turma.getCurso().getId(),
        turma.getCurso().getNome()))
      .collect(Collectors.toList());
  }

  @Override
  public TurmaResponse buscarPorId(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    TurmaEntity turma = turmaRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Turma não encontrada"));

    return new TurmaResponse(
      turma.getId(),
      turma.getNome(),
      turma.getProfessor().getId(),
      turma.getProfessor().getNome(),
      turma.getCurso().getId(),
      turma.getCurso().getNome());
  }

  @Override
  public TurmaResponse cadastrar(CadastrarTurmaRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    if (request.nome() == null) {
      throw new RequisicaoInvalidaException("Campo 'nome' é obrigatório.");
    }

    if (request.professorId() == null) {
      throw new RequisicaoInvalidaException("Campo 'professorId' é obrigatório.");
    }

    if (request.cursoId() == null) {
      throw new RequisicaoInvalidaException("Campo 'cursoId' é obrigatório.");
    }

    DocenteEntity professor = docenteRepository.findById(request.professorId())
      .orElseThrow(() -> new NotFoundException("Professor não encontrado."));

    CursoEntity curso = cursoRepository.findById(request.cursoId())
      .orElseThrow(() -> new NotFoundException("Curso não encontrado."));

    if (!professor.getUsuario().getPapel().getNome().equals("PROFESSOR")) {
      throw new CodigoInvalidoException("Código não é de Professor.");
    }

    TurmaEntity turma = new TurmaEntity();
    turma.setNome(request.nome());
    turma.setProfessor(professor);
    turma.setCurso(curso);

    turmaRepository.save(turma);

    return new TurmaResponse(
      turma.getId(),
      turma.getNome(),
      turma.getProfessor().getId(),
      turma.getProfessor().getNome(),
      turma.getCurso().getId(),
      turma.getCurso().getNome());
  }

  @Override
  public TurmaResponse alterar(Long id, AlterarTurmaRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    TurmaEntity turma = turmaRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Turma não encontrada"));

    if (request.nome() != null && !turma.getNome().equals(request.nome())) {
      turma.setNome(request.nome());
    }

    if (request.professorId() != null && !turma.getProfessor().getId().equals(request.professorId())) {
      DocenteEntity professor = docenteRepository.findById(request.professorId())
        .orElseThrow(() -> new NotFoundException("Professor não encontrado."));

      turma.setProfessor(professor);
    }

    if (request.cursoId() != null && !turma.getCurso().getId().equals(request.cursoId())) {
      CursoEntity curso = cursoRepository.findById(request.cursoId())
        .orElseThrow(() -> new NotFoundException("Curso não encontrado."));

      turma.setCurso(curso);
    }

    turma.setId(id);
      turmaRepository.save(turma);

    return new TurmaResponse(
      turma.getId(),
      turma.getNome(),
      turma.getProfessor().getId(),
      turma.getProfessor().getNome(),
      turma.getCurso().getId(),
      turma.getCurso().getNome());
  }


  @Override
  public void apagar(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    TurmaEntity turma = turmaRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Turma não encontrada"));

    try {
      turmaRepository.delete(turma);
    } catch (DataIntegrityViolationException e) {
      throw new ExclusaoNaoPermitidaException("Não é possível excluir esta turma, pois ela possui vínculos.");
    }
  }

}
