package br.com.senai.fullstack.educationprojetofinalmodulo01.service.impl;

import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.request.LoginRequest;
import br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response.LoginResponse;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.entity.UsuarioEntity;
import br.com.senai.fullstack.educationprojetofinalmodulo01.datasource.repository.UsuarioRepository;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.NotFoundException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.infra.exception.customException.RequisicaoInvalidaException;
import br.com.senai.fullstack.educationprojetofinalmodulo01.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

  private final BCryptPasswordEncoder bCryptEncoder;
  private final JwtEncoder jwtEncoder;
  private final JwtDecoder jwtDecoder;
  private final UsuarioRepository usuarioRepository;

  private static long TEMPO_EXPIRACAO = 1296000L;

  public LoginResponse gerarToken(LoginRequest loginRequest) {

    if (loginRequest.login() == null) {
      throw new RequisicaoInvalidaException("Campo 'login' é obrigatório.");
    }

    if (loginRequest.senha() == null) {
      throw new RequisicaoInvalidaException("Campo 'senha' é obrigatório.");
    }

    UsuarioEntity usuarioEntity = usuarioRepository
      .findByLogin(loginRequest.login())
      .orElseThrow(() -> new NotFoundException("Usuário não existe.")
      );

    if (!usuarioEntity.validarSenha(loginRequest, bCryptEncoder)) {
      throw new RequisicaoInvalidaException("Senha incorreta.");
    }

    Instant now = Instant.now();

    String scope = usuarioEntity.getPapel().getNome();

    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuer("labPCP")
      .issuedAt(now)
      .expiresAt(now.plusSeconds(TEMPO_EXPIRACAO))
      .subject(usuarioEntity.getId().toString())
      .claim("scope", scope)
      .build();

    var tokenJWT = jwtEncoder
      .encode(JwtEncoderParameters.from(claims))
      .getTokenValue();

    return new LoginResponse(tokenJWT, TEMPO_EXPIRACAO);
  }

  public String buscarCampo(String token, String claim) {
    return jwtDecoder
      .decode(token)
      .getClaims()
      .get(claim)
      .toString();
  }

}

