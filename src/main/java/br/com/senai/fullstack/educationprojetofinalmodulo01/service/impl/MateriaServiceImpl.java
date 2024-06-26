package br.com.senai.fullstack.educationprojetofinalmodulo01.service.impl;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.MateriaRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.MateriaResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.CursoEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.MateriaEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.CursoRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.MateriaRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.AcessoNaoAutorizadoException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.ExclusaoNaoPermitidaException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.NotFoundException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.RequisicaoInvalidaException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TokenService;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements MateriaService {

  private final MateriaRepository materiaRepository;
  private final CursoRepository cursoRepository;
  private final TokenService tokenService;

  @Override
  public List<MateriaResponse> buscarMateriasPorCurso(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    List<MateriaEntity> listaMaterias = materiaRepository.findAllMateriasByCursoId(id);

    if (cursoRepository.findById(id).isEmpty()) {
      throw new NotFoundException("Curso não encontrado.");
    }

    if (listaMaterias.isEmpty()) {
      throw new NotFoundException("Não há matérias cadastradas.");
    }

    return listaMaterias.stream()
      .map(materia -> new MateriaResponse(
        materia.getId(),
        materia.getNome(),
        materia.getCurso().getId(),
        materia.getCurso().getNome()))
      .collect(Collectors.toList());
  }

  @Override
  public MateriaResponse buscarPorId(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    MateriaEntity materia = materiaRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Matéria não encontrada"));

    return new MateriaResponse(
      materia.getId(),
      materia.getNome(),
      materia.getCurso().getId(),
      materia.getCurso().getNome());
  }

  @Override
  public MateriaResponse cadastrar(MateriaRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    if (request.nome() == null) {
      throw new RequisicaoInvalidaException("Campo 'nome' é obrigatório.");
    }

    if (request.cursoId() == null) {
      throw new RequisicaoInvalidaException("Campo 'cursoId' é obrigatório.");
    }

    CursoEntity curso = cursoRepository.findById(request.cursoId())
      .orElseThrow(() -> new NotFoundException("Curso não encontrado."));

    MateriaEntity materia = new MateriaEntity();
    materia.setNome(request.nome());
    materia.setCurso(curso);

    materiaRepository.save(materia);

    return new MateriaResponse(
      materia.getId(),
      materia.getNome(),
      materia.getCurso().getId(),
      materia.getCurso().getNome());
  }

  @Override
  public MateriaResponse alterar(Long id, MateriaRequest request, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    MateriaEntity materia = materiaRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Matéria não encontrada"));

    if (request.nome() != null && !materia.getNome().equals(request.nome())) {
      materia.setNome(request.nome());
    }

    if (request.cursoId() != null && !materia.getCurso().getId().equals(request.cursoId())) {
      CursoEntity curso = cursoRepository.findById(request.cursoId())
        .orElseThrow(() -> new NotFoundException("Curso não encontrado."));

      materia.setCurso(curso);
    }

    materia.setId(id);
    materiaRepository.save(materia);

    return new MateriaResponse(
      materia.getId(),
      materia.getNome(),
      materia.getCurso().getId(),
      materia.getCurso().getNome());
  }

  @Override
  public void apagar(Long id, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    MateriaEntity materia = materiaRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Matéria não encontrada"));

    try {
      materiaRepository.delete(materia);
    } catch (DataIntegrityViolationException e) {
      throw new ExclusaoNaoPermitidaException("Não é possível excluir esta matéria, pois ela possui vínculos.");
    }
  }

}

