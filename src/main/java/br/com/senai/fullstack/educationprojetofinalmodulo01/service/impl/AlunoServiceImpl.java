package br.com.senai.fullstack.educationprojetofinalmodulo01.service.impl;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.AlterarAlunoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarAlunoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.AlunoResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.AlunoEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.TurmaEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.UsuarioEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.AlunoRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.TurmaRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.UsuarioRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.AcessoNaoAutorizadoException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.NotFoundException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.RequisicaoInvalidaException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.UsuarioInvalidoException;
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
  private final TokenService tokenService;

  @Override
  public List<AlunoResponse> buscarTodos(String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
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
    if (!papel.equals("ADM")){
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
  public AlunoResponse cadastrar(CadastrarAlunoRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    if (request.nome() == null ||
      request.dataNascimento() == null ||
      request.turmaId() == null ||
      request.usuario() == null) {
      throw new RequisicaoInvalidaException("Todos os campos são obrigatórios.");
    }

    UsuarioEntity usuario = usuarioRepository.findByLogin(request.usuario())
      .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

    TurmaEntity turma = turmaRepository.findById(request.turmaId())
      .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

    if (!usuario.getPapel().getNome().equals("ALUNO") ) {
      throw new UsuarioInvalidoException("Usuário precisar ser do tipo 'Aluno'.");
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
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    AlunoEntity aluno = alunoRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

    if (request.nome() != null && !aluno.getNome().equals(request.nome())) {
      aluno.setNome(request.nome());
    }

    if (request.dataNascimento() != null && !aluno.getDataNascimento().isEqual(request.dataNascimento())) {
      aluno.setDataNascimento(request.dataNascimento());
    }

    if (request.turmaId() != null && !aluno.getTurma().getId().equals(request.turmaId())) {
      aluno.setDataNascimento(request.dataNascimento());
    }

    aluno.setId(id);

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
  public void apagar(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    AlunoEntity aluno = alunoRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

    alunoRepository.delete(aluno);
  }

}
