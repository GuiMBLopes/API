package br.org.serratec.FinalAPI.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Usuario {
    
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column 
    @NotBlank(message= "O nome é obrigatorio")
    private String nome;

    @Column
    @NotBlank(message= "O sobrenome é obrigatorio")
    private String sobrenome;

    @Column
    @NotBlank(message= "O e-mail é obrigatorio")
    private String email;

    @Column
    @NotBlank(message= "A senha é obrigatoria")
    private String senha;

    @Column (name="data_nascimento")
    @NotNull(message= "A data de nascimento é obrigaria")
    private LocalDate dataNascimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
