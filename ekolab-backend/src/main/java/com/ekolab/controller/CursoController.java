package com.ekolab.controller;

import com.ekolab.dto.CursoRequest;
import com.ekolab.model.Curso;
import com.ekolab.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/cursos")
public class CursoController {
    
    @Autowired
    private CursoService cursoService;
    
    // Endpoints públicos
    @GetMapping("/publicos")
    public ResponseEntity<List<Curso>> listarCursosPublicos() {
        List<Curso> cursos = cursoService.listarAtivos();
        return ResponseEntity.ok(cursos);
    }
    
    @GetMapping("/publicos/{id}")
    public ResponseEntity<?> buscarCursoPublico(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.buscarPorId(id);
        if (curso.isPresent() && curso.get().getStatus() == Curso.StatusCurso.ATIVO) {
            return ResponseEntity.ok(curso.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Curso não encontrado ou não está ativo!");
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/publicos/categoria/{categoria}")
    public ResponseEntity<List<Curso>> listarPorCategoriaPublico(@PathVariable String categoria) {
        List<Curso> cursos = cursoService.buscarComFiltros(categoria, null, null, Curso.StatusCurso.ATIVO, null, null);
        return ResponseEntity.ok(cursos);
    }
    
    @GetMapping("/publicos/recentes")
    public ResponseEntity<List<Curso>> listarCursosRecentes() {
        List<Curso> cursos = cursoService.listarCursosRecentes();
        return ResponseEntity.ok(cursos);
    }
    
    @GetMapping("/publicos/categorias")
    public ResponseEntity<List<String>> listarCategorias() {
        List<String> categorias = cursoService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }
    
    @GetMapping("/publicos/instrutores")
    public ResponseEntity<List<String>> listarInstrutores() {
        List<String> instrutores = cursoService.listarInstrutores();
        return ResponseEntity.ok(instrutores);
    }
    
    @GetMapping("/publicos/buscar")
    public ResponseEntity<List<Curso>> buscarCursosPublicos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Curso.Nivel nivel,
            @RequestParam(required = false) Curso.Modalidade modalidade,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax) {
        
        List<Curso> cursos;
        if (nome != null && !nome.trim().isEmpty()) {
            cursos = cursoService.buscarPorNome(nome);
        } else {
            cursos = cursoService.buscarComFiltros(categoria, nivel, modalidade, Curso.StatusCurso.ATIVO, precoMin, precoMax);
        }
        
        return ResponseEntity.ok(cursos);
    }
    
    // Endpoints administrativos (agora sem autenticação)
    @GetMapping
    public ResponseEntity<List<Curso>> listarTodos() {
        List<Curso> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.buscarPorId(id);
        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Curso não encontrado!");
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> criarCurso(@Valid @RequestBody CursoRequest cursoRequest) {
        try {
            Curso curso = cursoService.criarCurso(cursoRequest);
            return ResponseEntity.ok(curso);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCurso(@PathVariable Long id, @Valid @RequestBody CursoRequest cursoRequest) {
        try {
            Curso curso = cursoService.atualizarCurso(id, cursoRequest);
            return ResponseEntity.ok(curso);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCurso(@PathVariable Long id) {
        try {
            cursoService.deletarCurso(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Curso deletado com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}/inativar")
    public ResponseEntity<?> inativarCurso(@PathVariable Long id) {
        try {
            cursoService.inativarCurso(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Curso inativado com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}/ativar")
    public ResponseEntity<?> ativarCurso(@PathVariable Long id) {
        try {
            cursoService.ativarCurso(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Curso ativado com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/{cursoId}/inscrever/{usuarioId}")
    public ResponseEntity<?> inscreverUsuario(@PathVariable Long cursoId, @PathVariable Long usuarioId) {
        try {
            cursoService.inscreverUsuario(cursoId, usuarioId);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Inscrição realizada com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @DeleteMapping("/{cursoId}/desinscrever/{usuarioId}")
    public ResponseEntity<?> desinscreverUsuario(@PathVariable Long cursoId, @PathVariable Long usuarioId) {
        try {
            cursoService.desinscreverUsuario(cursoId, usuarioId);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Desinscrição realizada com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/estatisticas")
    public ResponseEntity<?> obterEstatisticas() {
        Map<String, Object> estatisticas = new HashMap<>();
        estatisticas.put("totalCursos", cursoService.contarCursos());
        estatisticas.put("cursosAtivos", cursoService.contarCursosAtivos());
        
        return ResponseEntity.ok(estatisticas);
    }
}

