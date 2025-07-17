package com.ekolab.controller;

import com.ekolab.dto.LoginRequest;
import com.ekolab.dto.UsuarioRequest;
import com.ekolab.model.Usuario;
import com.ekolab.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(loginRequest.getEmail());
            
            if (usuarioOpt.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Credenciais inválidas!");
                return ResponseEntity.badRequest().body(error);
            }
            
            Usuario usuario = usuarioOpt.get();
            
            // Verificar senha
            if (!passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Credenciais inválidas!");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Verificar se usuário está ativo
            if (!usuario.getAtivo()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Usuário inativo!");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login realizado com sucesso!");
            response.put("usuario", Map.of(
                "id", usuario.getId(),
                "nomeCompleto", usuario.getNomeCompleto(),
                "email", usuario.getEmail(),
                "tipoUsuario", usuario.getTipoUsuario()
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erro interno do servidor!");
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/cadastro")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        try {
            // Verificar se email já existe
            if (usuarioService.existeEmail(usuarioRequest.getEmail())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Email já está em uso!");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Verificar se CPF já existe
            if (usuarioService.existeCpf(usuarioRequest.getCpf())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "CPF já está em uso!");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Criar usuário
            Usuario usuario = usuarioService.criarUsuario(usuarioRequest);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuário cadastrado com sucesso!");
            response.put("email", usuario.getEmail());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erro ao cadastrar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/verificar-email")
    public ResponseEntity<?> verificarEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean existe = usuarioService.existeEmail(email);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/verificar-cpf")
    public ResponseEntity<?> verificarCpf(@RequestBody Map<String, String> request) {
        String cpf = request.get("cpf");
        boolean existe = usuarioService.existeCpf(cpf);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        
        return ResponseEntity.ok(response);
    }
}

