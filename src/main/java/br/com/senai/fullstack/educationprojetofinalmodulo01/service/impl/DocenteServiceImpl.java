package br.com.senai.fullstack.educationprojetofinalmodulo01.service.impl;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.AlterarDocenteRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarDocenteRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.DocenteResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.DocenteEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.UsuarioEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.DocenteRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.UsuarioRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.AcessoNaoAutorizadoException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.NotFoundException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.RequisicaoInvalidaException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.UsuarioInvalidoException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.ExclusaoNaoPermitidaException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.DocenteService;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocenteServiceImpl implements DocenteService {

  private final DocenteRepository docenteRepository;
  private final UsuarioRepository usuarioRepository;
  private final TokenService tokenService;

  @Override
  public List<DocenteResponse> buscarTodos(String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO") && !papel.equals("RECRUITER")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    if (!papel.equals("ADM")) {
      List<DocenteEntity> listaProfessores = docenteRepository.findAllDocentesWithPapelProfessor();

      if (listaProfessores.isEmpty()) {
        throw new NotFoundException("Não há professores cadastrados.");
      }

      return listaProfessores.stream()
        .map(docente -> new DocenteResponse(
          docente.getId(),
          docente.getNome(),
          docente.getDataEntrada(),
          docente.getUsuario().getLogin(),
          docente.getUsuario().getPapel().getNome()))
        .collect(Collectors.toList());
    }

    List<DocenteEntity> listaDocentes = docenteRepository.findAll();

    if (listaDocentes.isEmpty()) {
      throw new NotFoundException("Não há docentes cadastrados.");
    }

    return listaDocentes.stream()
      .map(docente -> new DocenteResponse(
        docente.getId(),
        docente.getNome(),
        docente.getDataEntrada(),
        docente.getUsuario().getLogin(),
        docente.getUsuario().getPapel().getNome()))
      .collect(Collectors.toList());
  }

  @Override
  public DocenteResponse buscarPorId(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO") && !papel.equals("RECRUITER")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    if (!papel.equals("ADM")) {
      DocenteEntity professor = docenteRepository.findByIdWithPapelProfessor(id);

      if (professor == null) {
        throw new NotFoundException("Professor não encontrado ou id não é de Professor.");
      }

      return new DocenteResponse(
        professor.getId(),
        professor.getNome(),
        professor.getDataEntrada(),
        professor.getUsuario().getLogin(),
        professor.getUsuario().getPapel().getNome());
    }

    DocenteEntity docente = docenteRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Docente não encontrado."));

    return new DocenteResponse(
      docente.getId(),
      docente.getNome(),
      docente.getDataEntrada(),
      docente.getUsuario().getLogin(),
      docente.getUsuario().getPapel().getNome());
  }

  @Override
  public DocenteResponse cadastrar(CadastrarDocenteRequest request, String token) {

    String papel = tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO") && !papel.equals("RECRUITER")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    if (request.nome() == null) {
      throw new RequisicaoInvalidaException("Campo 'nome' é obrigatório.");
    }

    if (request.usuario() == null) {
      throw new RequisicaoInvalidaException("Campo 'usuario' é obrigatório.");
    }

    UsuarioEntity usuario = usuarioRepository.findByLogin(request.usuario())
      .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

    if (!papel.equals("ADM") && !usuario.getPapel().getNome().equals("PROFESSOR")) {
      throw new UsuarioInvalidoException("Usuário deve ter papel de 'PROFESSOR'.");
    }

    if (usuario.getPapel().getNome().equals("ALUNO")) {
      throw new UsuarioInvalidoException("Usuário deve ter papel diferente de 'ALUNO'.");
    }

    DocenteEntity docente = new DocenteEntity();
    docente.setNome(request.nome());
    docente.setDataEntrada(request.dataEntrada());
    docente.setUsuario(usuario);

    try {
      docenteRepository.save(docente);
    } catch (DataIntegrityViolationException e) {
      throw new UsuarioInvalidoException("Usuário já está sendo utilizado.");
    }

    return new DocenteResponse(
      docente.getId(),
      docente.getNome(),
      docente.getDataEntrada(),
      docente.getUsuario().getLogin(),
      docente.getUsuario().getPapel().getNome());
  }

  @Override
  public DocenteResponse alterar(Long id, AlterarDocenteRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM") && !papel.equals("PEDAGOGICO") && !papel.equals("RECRUITER")) {
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    DocenteEntity docente = docenteRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Docente não encontrado"));

    if (request.nome() != null && !docente.getNome().equals(request.nome())) {
      docente.setNome(request.nome());
    }

    if (request.dataEntrada() != null && !docente.getDataEntrada().isEqual(request.dataEntrada())) {
      docente.setDataEntrada(request.dataEntrada());
    }

    if (request.usuario() != null) {
      throw new RequisicaoInvalidaException("Usuário não pode ser alterado.");
    }

    if (!papel.equals("ADM") && !docente.getUsuario().getPapel().getNome().equals("PROFESSOR")) {
        throw new UsuarioInvalidoException("Não é possível alterar este cadastro. O usuário é diferente de 'PROFESSOR'.");
    }

    docente.setId(id);
    docenteRepository.save(docente);

    return new DocenteResponse(
      docente.getId(),
      docente.getNome(),
      docente.getDataEntrada(),
      docente.getUsuario().getLogin(),
      docente.getUsuario().getPapel().getNome());
  }

  @Override
  public void apagar(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    DocenteEntity docente = docenteRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Docente não encontrado"));

    try {
      docenteRepository.delete(docente);
    } catch (DataIntegrityViolationException e) {
      throw new ExclusaoNaoPermitidaException("Não é possível excluir este cadastro, pois ele possui vínculos.");
    }
  }

}
