package br.com.senai.fullstack.educationprojetofinalmodulo01.service.impl;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CursoRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.CursoResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.CursoEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.UsuarioEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.CursoRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.UsuarioRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.AcessoNaoAutorizadoException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.NotFoundException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.RequisicaoInvalidaException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.UsuarioInvalidoException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.CursoService;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

  private final CursoRepository cursoRepository;
  private final UsuarioRepository usuarioRepository;
  private final TokenService tokenService;

  @Override
  public List<CursoResponse> buscarTodos(String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    List<CursoEntity> listaCursos = cursoRepository.findAll();

    if (listaCursos.isEmpty()) {
      throw new NotFoundException("Não há cursos cadastrados.");
    }

    return listaCursos.stream()
      .map(curso -> new CursoResponse(
        curso.getId(),
        curso.getNome()))
      .collect(Collectors.toList());
  }

  @Override
  public CursoResponse buscarPorId(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    CursoEntity curso = cursoRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

    return new CursoResponse(
      curso.getId(),
      curso.getNome());
  }

  @Override
  public CursoResponse cadastrar(CursoRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    if (request.nome() == null) {
      throw new RequisicaoInvalidaException("Todos os campos são obrigatórios.");
    }

    CursoEntity curso = new CursoEntity();
    curso.setNome(request.nome());

    cursoRepository.save(curso);

    return new CursoResponse(curso.getId(), curso.getNome());
  }

  @Override
  public CursoResponse alterar(Long id, CursoRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    CursoEntity curso = cursoRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

    if (request.nome() != null && !curso.getNome().equals(request.nome())) {
      curso.setNome(request.nome());
    }

    curso.setId(id);

    cursoRepository.save(curso);

    return new CursoResponse(curso.getId(), curso.getNome());
  }


  @Override
  public void apagar(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    CursoEntity curso = cursoRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

    cursoRepository.delete(curso);
  }

}
