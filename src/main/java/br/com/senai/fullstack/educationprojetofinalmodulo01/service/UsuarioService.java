package br.com.senai.fullstack.educationprojetofinalmodulo01.service;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.CadastrarUsuarioRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.UsuarioEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.PapelRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.UsuarioRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.RequisicaoInvalidaException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final BCryptPasswordEncoder bCryptEncoder;
  private final UsuarioRepository usuarioRepository;
  private final PapelRepository papelRepository;

  public void cadastrarUsuario(@RequestBody CadastrarUsuarioRequest cadastrarUsuarioRequest) {

    if (cadastrarUsuarioRequest.login() == null ||
        cadastrarUsuarioRequest.senha() == null ||
        cadastrarUsuarioRequest.papel() == null) {
      throw new RequisicaoInvalidaException("Erro: todos os campos são obrigatórios.");
    }

    if (usuarioRepository
      .findByLogin(cadastrarUsuarioRequest.login())
      .isPresent()) {
      throw new RequisicaoInvalidaException("Erro: usuário já existe.");
    }

    UsuarioEntity usuario = new UsuarioEntity();
    usuario.setLogin(cadastrarUsuarioRequest.login());
    usuario.setSenha(bCryptEncoder.encode(cadastrarUsuarioRequest.senha()));
    usuario.setPapel(
      papelRepository.findByNome(cadastrarUsuarioRequest.papel())
      .orElseThrow(() -> new RequisicaoInvalidaException("Erro: papel inválido ou inexistente."))
    );

    usuarioRepository.save(usuario);
  }
}
