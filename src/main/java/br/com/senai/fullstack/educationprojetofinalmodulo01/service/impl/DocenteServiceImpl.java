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
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
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
        docente.getUsuario().getLogin()))
      .collect(Collectors.toList());
  }

  @Override
  public DocenteResponse buscarPorId(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    DocenteEntity docente = docenteRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Docente não encontrado"));

    return new DocenteResponse(
      docente.getId(),
      docente.getNome(),
      docente.getDataEntrada(),
      docente.getUsuario().getLogin());
  }

  @Override
  public DocenteResponse cadastrar(CadastrarDocenteRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    if (request.nome() == null ||
      request.dataEntrada() == null ||
      request.usuario() == null) {
      throw new RequisicaoInvalidaException("Todos os campos são obrigatórios.");
    }

    UsuarioEntity usuario = usuarioRepository.findByLogin(request.usuario())
      .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

    DocenteEntity docente = new DocenteEntity();
    docente.setNome(request.nome());
    docente.setDataEntrada(request.dataEntrada());
    docente.setUsuario(usuario);

    try {
      docenteRepository.save(docente);
    } catch (DataIntegrityViolationException e) {
      throw new UsuarioInvalidoException("Usuário já está sendo utilizado.");
    }

    return new DocenteResponse(docente.getId(), docente.getNome(), docente.getDataEntrada(), docente.getUsuario().getLogin());
  }

  @Override
  public DocenteResponse alterar(Long id, AlterarDocenteRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
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

    if (request.usuario() != null && !docente.getUsuario().getLogin().equals(request.usuario())) {
      UsuarioEntity usuario = usuarioRepository.findByLogin(request.usuario())
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
      docente.setUsuario(usuario);
    }

    docente.setId(id);

    try {
      docenteRepository.save(docente);
    } catch (DataIntegrityViolationException e) {
      throw new UsuarioInvalidoException("Usuário já está sendo utilizado.");
    }

    return new DocenteResponse(docente.getId(), docente.getNome(), docente.getDataEntrada(), docente.getUsuario().getLogin());
  }


  @Override
  public void apagar(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    DocenteEntity docente = docenteRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Docente não encontrado"));

    docenteRepository.delete(docente);
  }

}
