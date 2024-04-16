package br.com.senai.fullstack.educationprojetofinalmodulo01.service.impl;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.NotaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.NotaResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.*;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.*;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.*;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TokenService;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements NotaService {

  private final NotaRepository notaRepository;
  private final UsuarioRepository usuarioRepository;
  private final AlunoRepository alunoRepository;
  private final DocenteRepository docenteRepository;
  private final MateriaRepository materiaRepository;
  private final TokenService tokenService;

  @Override
  public List<NotaResponse> buscarNotasPorAluno(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PROFESSOR")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    List<NotaEntity> listaNotas = notaRepository.findAllNotasByAlunoId(id);

    if (alunoRepository.findById(id).isEmpty()) {
      throw new NotFoundException("Aluno não encontrado.");
    }

    if (listaNotas.isEmpty()) {
      throw new NotFoundException("Não há notas cadastradas.");
    }

    return listaNotas.stream()
      .map(nota -> new NotaResponse(
        nota.getId(),
        nota.getAluno().getNome(),
        nota.getProfessor().getNome(),
        nota.getMateria().getNome(),
        nota.getValor(),
        nota.getData()))
      .collect(Collectors.toList());
  }

  @Override
  public NotaResponse buscarPorId(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PROFESSOR")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    NotaEntity nota = notaRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Nota não encontrada"));

    return new NotaResponse(
      nota.getId(),
      nota.getAluno().getNome(),
      nota.getProfessor().getNome(),
      nota.getMateria().getNome(),
      nota.getValor(),
      nota.getData());
  }

  @Override
  public NotaResponse cadastrar(NotaRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PROFESSOR")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    Long usuarioId = Long.valueOf(tokenService.buscarCampo(token, "sub"));

    UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
      .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

    if (request.alunoId() == null) {
      throw new RequisicaoInvalidaException("Campo 'alunoId' é obrigatório.");
    }

    if (request.professorId() == null) {
      throw new RequisicaoInvalidaException("Campo 'professorId' é obrigatório.");
    }

    if (request.materiaId() == null) {
      throw new RequisicaoInvalidaException("Campo 'materiaId' é obrigatório.");
    }

    if (request.valor() == null) {
      throw new RequisicaoInvalidaException("Campo 'valor' é obrigatório.");
    }

    if (request.data() == null) {
      throw new RequisicaoInvalidaException("Campo 'data' é obrigatório.");
    }

    AlunoEntity aluno = alunoRepository.findById(request.alunoId())
      .orElseThrow(() -> new NotFoundException("Aluno não encontrado."));

    DocenteEntity professor = docenteRepository.findById(request.professorId())
      .orElseThrow(() -> new NotFoundException("Professor não encontrado."));

    MateriaEntity materia = materiaRepository.findById(request.materiaId())
      .orElseThrow(() -> new NotFoundException("Curso não encontrado."));

    if (!aluno.getUsuario().getPapel().getNome().equals("ALUNO")) {
      throw new CodigoInvalidoException("Código não é de um Aluno.");
    } else {
      NotaEntity nota = new NotaEntity();
      nota.setAluno(aluno);
      nota.setProfessor(professor);
      nota.setMateria(materia);
      nota.setValor(request.valor());
      nota.setData(request.data());

      notaRepository.save(nota);

      return new NotaResponse(
        nota.getId(),
        nota.getAluno().getNome(),
        nota.getProfessor().getNome(),
        nota.getMateria().getNome(),
        nota.getValor(),
        nota.getData());
    }
  }

  @Override
  public NotaResponse alterar(Long id, NotaRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PROFESSOR")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    NotaEntity nota = notaRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Nota não encontrada"));

    if (request.alunoId() != null && !nota.getAluno().getId().equals(request.alunoId())) {
      AlunoEntity aluno = alunoRepository.findById(request.alunoId())
        .orElseThrow(() -> new NotFoundException("Aluno não encontrado."));

      nota.setAluno(aluno);
    }

    if (request.professorId() != null && !nota.getProfessor().getId().equals(request.professorId())) {
      DocenteEntity professor = docenteRepository.findById(request.professorId())
        .orElseThrow(() -> new NotFoundException("Professor não encontrado."));

      nota.setProfessor(professor);
    }

    if (request.materiaId() != null && !nota.getMateria().getId().equals(request.materiaId())) {
      MateriaEntity materia = materiaRepository.findById(request.materiaId())
        .orElseThrow(() -> new NotFoundException("Curso não encontrado."));

      nota.setMateria(materia);
    }

    if (request.valor() != null && !nota.getValor().equals(request.valor())) {
      nota.setValor(request.valor());
    }

    if (request.data() != null && !nota.getData().equals(request.data())) {
      nota.setData(request.data());
    }

    nota.setId(id);
    notaRepository.save(nota);

    return new NotaResponse(
      nota.getId(),
      nota.getAluno().getNome(),
      nota.getProfessor().getNome(),
      nota.getMateria().getNome(),
      nota.getValor(),
      nota.getData());
  }

  @Override
  public void apagar(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    NotaEntity nota = notaRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Nota não encontrada"));

    try {
      notaRepository.delete(nota);
    } catch (DataIntegrityViolationException e) {
      throw new ExclusaoNaoPermitidaException("Não é possível excluir esta nota, pois ela possui vínculos.");
    }
  }

}
