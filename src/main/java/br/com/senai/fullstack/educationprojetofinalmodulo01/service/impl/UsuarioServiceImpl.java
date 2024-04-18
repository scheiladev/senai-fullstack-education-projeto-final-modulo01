package br.com.senai.fullstack.educationprojetofinalmodulo01.service.impl;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarUsuarioRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.UsuarioResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.UsuarioEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.PapelRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.UsuarioRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.AcessoNaoAutorizadoException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.ConflitoDeDadosException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.RequisicaoInvalidaException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TokenService;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

  private final BCryptPasswordEncoder bCryptEncoder;
  private final UsuarioRepository usuarioRepository;
  private final PapelRepository papelRepository;
  private final TokenService tokenService;

  public UsuarioResponse cadastrarUsuario(CadastrarUsuarioRequest cadastrarUsuarioRequest, String token) {

    String papel =  tokenService.buscarCampo(token, "scope");
    if (!papel.equals("ADM")){
      throw new AcessoNaoAutorizadoException("Acesso não autorizado.");
    }

    if (cadastrarUsuarioRequest.login() == null) {
      throw new RequisicaoInvalidaException("Campo 'login' é obrigatório.");
    }

    if (cadastrarUsuarioRequest.senha() == null) {
      throw new RequisicaoInvalidaException("Campo 'senha' é obrigatório.");
    }

    if (cadastrarUsuarioRequest.papel() == null) {
      throw new RequisicaoInvalidaException("Campo 'papel' é obrigatório.");
    }

    if (usuarioRepository.findByLogin(cadastrarUsuarioRequest.login()).isPresent()) {
      throw new ConflitoDeDadosException("Usuário já existe.");
    }

    UsuarioEntity usuario = new UsuarioEntity();
    usuario.setLogin(cadastrarUsuarioRequest.login());
    usuario.setSenha(bCryptEncoder.encode(cadastrarUsuarioRequest.senha()));
    usuario.setPapel(papelRepository.findByNome(cadastrarUsuarioRequest.papel())
      .orElseThrow(() -> new RequisicaoInvalidaException("Erro: papel inválido ou inexistente."))
    );

    usuarioRepository.save(usuario);

    return new UsuarioResponse(usuario.getId(), usuario.getLogin(), usuario.getPapel().getNome());
  }

}
