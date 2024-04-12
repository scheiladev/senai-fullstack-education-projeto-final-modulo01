package br.com.senai.fullstack.educationprojetofinalmodulo01.controller.dto;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorld {

  @Secured("true")
  @GetMapping
  public String hello(){
    return "Hello";
  }
}

