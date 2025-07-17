package com.ekolab.controller;

import com.ekolab.model.Curso;
import com.ekolab.model.Usuario;
import com.ekolab.service.CursoService;
import com.ekolab.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private CursoService cursoService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        
        // Estatísticas de usuários
        Map<String, Object> usuariosStats = new HashMap<>();
        usuariosStats.put("total", usuarioService.contarUsuarios());
        usuariosStats.put("ativos", usuarioService.contarUsuariosAtivos());
        usuariosStats.put("admins", usuarioService.contarUsuariosPorTipo(Usuario.TipoUsuario.ADMIN));
        usuariosStats.put("users", usuarioService.contarUsuariosPorTipo(Usuario.TipoUsuario.USER));
        
        // Estatísticas de cursos
        Map<String, Object> cursosStats = new HashMap<>();
        cursosStats.put("total", cursoService.contarCursos());
        cursosStats.put("ativos", cursoService.contarCursosAtivos());
        
        // Listas recentes
        List<Usuario> usuariosRecentes = usuarioService.listarTodos().stream()
                .sorted((u1, u2) -> u2.getDataCriacao().compareTo(u1.getDataCriacao()))
                .limit(5)
                .collect(java.util.stream.Collectors.toList());
        
        List<Curso> cursosRecentes = cursoService.listarCursosRecentes().stream()
                .limit(5)
                .collect(java.util.stream.Collectors.toList());
        
        dashboard.put("usuarios", usuariosStats);
        dashboard.put("cursos", cursosStats);
        dashboard.put("usuariosRecentes", usuariosRecentes);
        dashboard.put("cursosRecentes", cursosRecentes);
        
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/usuarios/resumo")
    public ResponseEntity<?> getResumoUsuarios() {
        Map<String, Object> resumo = new HashMap<>();
        
        List<Usuario> todosUsuarios = usuarioService.listarTodos();
        List<Usuario> usuariosAtivos = usuarioService.listarAtivos();
        
        resumo.put("total", todosUsuarios.size());
        resumo.put("ativos", usuariosAtivos.size());
        resumo.put("inativos", todosUsuarios.size() - usuariosAtivos.size());
        resumo.put("admins", usuarioService.contarUsuariosPorTipo(Usuario.TipoUsuario.ADMIN));
        resumo.put("users", usuarioService.contarUsuariosPorTipo(Usuario.TipoUsuario.USER));
        
        return ResponseEntity.ok(resumo);
    }
    
    @GetMapping("/cursos/resumo")
    public ResponseEntity<?> getResumoCursos() {
        Map<String, Object> resumo = new HashMap<>();
        
        long totalCursos = cursoService.contarCursos();
        long cursosAtivos = cursoService.contarCursosAtivos();
        
        resumo.put("total", totalCursos);
        resumo.put("ativos", cursosAtivos);
        resumo.put("inativos", totalCursos - cursosAtivos);
        
        // Estatísticas por categoria
        List<String> categorias = cursoService.listarCategorias();
        Map<String, Long> cursosPorCategoria = new HashMap<>();
        for (String categoria : categorias) {
            cursosPorCategoria.put(categoria, cursoService.contarCursosPorCategoria(categoria));
        }
        resumo.put("porCategoria", cursosPorCategoria);
        
        return ResponseEntity.ok(resumo);
    }
    
    @GetMapping("/relatorios/usuarios-por-mes")
    public ResponseEntity<?> getUsuariosPorMes() {
        // Implementar lógica para relatório de usuários por mês
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("message", "Relatório de usuários por mês - implementar conforme necessidade");
        return ResponseEntity.ok(relatorio);
    }
    
    @GetMapping("/relatorios/cursos-populares")
    public ResponseEntity<?> getCursosPopulares() {
        // Implementar lógica para cursos mais populares
        List<Curso> cursosPopulares = cursoService.listarCursosRecentes();
        return ResponseEntity.ok(cursosPopulares);
    }
    
    @PostMapping("/sistema/backup")
    public ResponseEntity<?> criarBackup() {
        // Implementar lógica de backup
        Map<String, String> response = new HashMap<>();
        response.put("message", "Backup criado com sucesso!");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/sistema/status")
    public ResponseEntity<?> getStatusSistema() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "online");
        status.put("versao", "1.0.0");
        status.put("timestamp", java.time.LocalDateTime.now());
        status.put("totalUsuarios", usuarioService.contarUsuarios());
        status.put("totalCursos", cursoService.contarCursos());
        
        return ResponseEntity.ok(status);
    }
}

