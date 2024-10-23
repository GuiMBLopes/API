package br.org.serratec.FinalAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import model.UserJava;

@RestController
@RequestMapping("/api")
public class UserController {

    @Operation(summary = "Obter um usuário pelo ID")
    @GetMapping("/users/{id}")
    public UserJava getUserById(@Parameter(description = "ID do usuário") @PathVariable Long id) {
       
        return new UserJava(id, "Nome Exemplo", "email@example.com");
    }

    @Operation(summary = "Criar um novo usuário")
    @PostMapping("/users")
    public UserJava createUser(@RequestBody UserJava user) {
     
        return user;
    }
}