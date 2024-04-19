package br.com.senai.fullstack.educationprojetofinalmodulo01.service.impl;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.AlterarAlunoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarAlunoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.AlunoResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.PontuacaoResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.*;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.*;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.*;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.AlunoService;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlunoServiceImpl implements AlunoService {

  private final AlunoRepository alunoRepository;
  private final UsuarioRepository usuarioRepository;
  private final TurmaRepository turmaRepository;
  private final MateriaRepository materiaRepository;
  private final NotaRepository notaRepository;
  private final TokenService tokenService;

  @Override
  public List<AlunoResponse> buscarTodos(String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    List<AlunoEntity> listaAlunos = alunoRepository.findAll();

    if (listaAlunos.isEmpty()) {
      throw new NotFoundException("Não há alunos cadastrados.");
    }

    return listaAlunos.stream()
      .map(aluno -> new AlunoResponse(
        aluno.getId(),
        aluno.getNome(),
        aluno.getDataNascimento(),
        aluno.getTurma().getId(),
        aluno.getUsuario().getLogin()))
      .collect(Collectors.toList());
  }

  @Override
  public AlunoResponse buscarPorId(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    AlunoEntity aluno = alunoRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

    return new AlunoResponse(
      aluno.getId(),
      aluno.getNome(),
      aluno.getDataNascimento(),
      aluno.getTurma().getId(),
      aluno.getUsuario().getLogin());
  }

  @Override
  public PontuacaoResponse buscarPontuacao(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("ALUNO")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    List<NotaEntity> notas = notaRepository.findAllNotasByAlunoId(id);
    double soma = 0.0;
    double pontuacao = 0.0;
    double materias = materiaRepository.findAllMateriasByCursoByAlunoId(id).size();

    for (NotaEntity nota : notas) {
      soma += nota.getValor();
    }

    pontuacao = (soma / materias) * 10;

    if (Double.isNaN(pontuacao)) {
      throw new NotFoundException("Não existem notas para este aluno.");
    }

    return new PontuacaoResponse(pontuacao);
  }

  @Override
  public AlunoResponse cadastrar(CadastrarAlunoRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    if (request.nome() == null) {
      throw new RequisicaoInvalidaException("Campo 'nome' é obrigatório.");
    }

    if (request.dataNascimento() == null) {
      throw new RequisicaoInvalidaException("Campo 'dataNascimento' é obrigatório.");
    }

    if (request.turmaId() == null) {
      throw new RequisicaoInvalidaException("Campo 'turmaId' é obrigatório.");
    }

    if (request.usuario() == null) {
      throw new RequisicaoInvalidaException("Campo 'usuario' é obrigatório.");
    }

    UsuarioEntity usuario = usuarioRepository.findByLogin(request.usuario())
      .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

    TurmaEntity turma = turmaRepository.findById(request.turmaId())
      .orElseThrow(() -> new NotFoundException("Turma não encontrada."));

    if (!usuario.getPapel().getNome().equals("ALUNO") ) {
      throw new UsuarioInvalidoException("Usuário deve ter papel 'ALUNO'.");
    }

    AlunoEntity aluno = new AlunoEntity();
    aluno.setNome(request.nome());
    aluno.setDataNascimento(request.dataNascimento());
    aluno.setTurma(turma);
    aluno.setUsuario(usuario);

    try {
      alunoRepository.save(aluno);
    } catch (DataIntegrityViolationException e) {
      throw new UsuarioInvalidoException("Usuário já está sendo utilizado.");
    }

    return new AlunoResponse(
      aluno.getId(),
      aluno.getNome(),
      aluno.getDataNascimento(),
      aluno.getTurma().getId(),
      aluno.getUsuario().getLogin());
  }

  @Override
  public AlunoResponse alterar(Long id, AlterarAlunoRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    AlunoEntity aluno = alunoRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

    if (request.nome() != null && !aluno.getNome().equals(request.nome())) {
      aluno.setNome(request.nome());
    }

    if (request.dataNascimento() != null && !aluno.getDataNascimento().equals(request.dataNascimento())) {
      aluno.setDataNascimento(request.dataNascimento());
    }

    if (request.turmaId() != null && !aluno.getTurma().getId().equals(request.turmaId())) {
      TurmaEntity turma = turmaRepository.findById(request.turmaId())
        .orElseThrow(() -> new NotFoundException("Turma não encontrada."));

      aluno.setTurma(turma);
    }

    if (request.usuario() != null) {
      throw new RequisicaoInvalidaException("Usuário não pode ser alterado.");
    }

    aluno.setId(id);
    alunoRepository.save(aluno);

    return new AlunoResponse(
      aluno.getId(),
      aluno.getNome(),
      aluno.getDataNascimento(),
      aluno.getTurma().getId(),
      aluno.getUsuario().getLogin());
  }

  @Override
  public void apagar(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    AlunoEntity aluno = alunoRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

    try {
      alunoRepository.delete(aluno);
    } catch (DataIntegrityViolationException e) {
      throw new ExclusaoNaoPermitidaException("Não é possível excluir este aluno, pois ele possui vínculos.");
    }
  }

}

