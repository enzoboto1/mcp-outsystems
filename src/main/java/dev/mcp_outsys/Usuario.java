package dev.mcp_outsys;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {

    @JsonProperty("Id")
    private Long IdUsuario;

    @JsonProperty("Nome")
    private String Nome;

    @JsonProperty("CPF")
    private String CPF;

    public Usuario() {
    }

    public Usuario(Long idUsuario, String nome, String cpf) {
        this.IdUsuario = idUsuario;
        this.Nome = nome;
        this.CPF = cpf;
    }

    public Usuario(String nome, String cpf) {
        this.Nome = nome;
        this.CPF = cpf;
    }

    public Long getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

}