package com.ekolab.dto;

import com.ekolab.model.Usuario;

public class JwtResponse {
    
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String nomeCompleto;
    private Usuario.TipoUsuario tipoUsuario;
    
    // Construtores
    public JwtResponse() {}
    
    public JwtResponse(String token, Long id, String email, String nomeCompleto, Usuario.TipoUsuario tipoUsuario) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.tipoUsuario = tipoUsuario;
    }
    
    // Getters e Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNomeCompleto() {
        return nomeCompleto;
    }
    
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
    
    public Usuario.TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(Usuario.TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}