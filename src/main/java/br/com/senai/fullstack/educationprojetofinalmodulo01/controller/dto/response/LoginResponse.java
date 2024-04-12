package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto.response;

public record LoginResponse(String tokenJWT, long tempoExpiracao) {
}
