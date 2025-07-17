package com.ekolab.service;

import com.ekolab.dto.CursoRequest;
import com.ekolab.model.Curso;
import com.ekolab.model.Usuario;
import com.ekolab.repository.CursoRepository;
import com.ekolab.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CursoService {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }
    
    public List<Curso> listarAtivos() {
        return cursoRepository.findByStatus(Curso.StatusCurso.ATIVO);
    }
    
    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }
    
    public List<Curso> buscarPorNome(String nome) {
        return cursoRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    public List<Curso> listarPorCategoria(String categoria) {
        return cursoRepository.findByCategoria(categoria);
    }
    
    public List<Curso> listarPorNivel(Curso.Nivel nivel) {
        return cursoRepository.findByNivel(nivel);
    }
    
    public List<Curso> listarPorModalidade(Curso.Modalidade modalidade) {
        return cursoRepository.findByModalidade(modalidade);
    }
    
    public List<Curso> listarPorInstrutor(String instrutor) {
        return cursoRepository.findByInstrutor(instrutor);
    }
    
    public List<Curso> listarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return cursoRepository.findByPrecoBetween(precoMin, precoMax);
    }
    
    public List<Curso> listarCursosRecentes() {
        return cursoRepository.findByStatusOrderByDataCriacaoDesc(Curso.StatusCurso.ATIVO);
    }
    
    public List<Curso> listarCursosPorPreco() {
        return cursoRepository.findByStatusOrderByPrecoAsc(Curso.StatusCurso.ATIVO);
    }
    
    public List<String> listarCategorias() {
        return cursoRepository.findDistinctCategoriasByStatus(Curso.StatusCurso.ATIVO);
    }
    
    public List<String> listarInstrutores() {
        return cursoRepository.findDistinctInstrutoresByStatus(Curso.StatusCurso.ATIVO);
    }
    
    public List<Curso> buscarComFiltros(String categoria, Curso.Nivel nivel, 
                                       Curso.Modalidade modalidade, Curso.StatusCurso status,
                                       BigDecimal precoMin, BigDecimal precoMax) {
        return cursoRepository.findCursosComFiltros(categoria, nivel, modalidade, status, precoMin, precoMax);
    }
    
    public Curso criarCurso(CursoRequest request) {
        Curso curso = new Curso();
        curso.setNome(request.getNome());
        curso.setDescricao(request.getDescricao());
        curso.setCargaHoraria(request.getCargaHoraria());
        curso.setNivel(request.getNivel());
        curso.setModalidade(request.getModalidade());
        curso.setStatus(request.getStatus());
        curso.setCategoria(request.getCategoria());
        curso.setInstrutor(request.getInstrutor());
        curso.setDataInicio(request.getDataInicio());
        curso.setPreco(request.getPreco());
        curso.setCertificado(request.getCertificado());
        curso.setImagemUrl(request.getImagemUrl());
        curso.setVideoApresentacaoUrl(request.getVideoApresentacaoUrl());
        curso.setRequisitos(request.getRequisitos());
        curso.setConteudoProgramatico(request.getConteudoProgramatico());
        
        return cursoRepository.save(curso);
    }
    
    public Curso atualizarCurso(Long id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
        
        curso.setNome(request.getNome());
        curso.setDescricao(request.getDescricao());
        curso.setCargaHoraria(request.getCargaHoraria());
        curso.setNivel(request.getNivel());
        curso.setModalidade(request.getModalidade());
        curso.setStatus(request.getStatus());
        curso.setCategoria(request.getCategoria());
        curso.setInstrutor(request.getInstrutor());
        curso.setDataInicio(request.getDataInicio());
        curso.setPreco(request.getPreco());
        curso.setCertificado(request.getCertificado());
        curso.setImagemUrl(request.getImagemUrl());
        curso.setVideoApresentacaoUrl(request.getVideoApresentacaoUrl());
        curso.setRequisitos(request.getRequisitos());
        curso.setConteudoProgramatico(request.getConteudoProgramatico());
        
        return cursoRepository.save(curso);
    }
    
    public void deletarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
        
        cursoRepository.delete(curso);
    }
    
    public void inativarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
        
        curso.setStatus(Curso.StatusCurso.INATIVO);
        cursoRepository.save(curso);
    }
    
    public void ativarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
        
        curso.setStatus(Curso.StatusCurso.ATIVO);
        cursoRepository.save(curso);
    }
    
    public void inscreverUsuario(Long cursoId, Long usuarioId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
        
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        
        curso.getUsuariosInscritos().add(usuario);
        cursoRepository.save(curso);
    }
    
    public void desinscreverUsuario(Long cursoId, Long usuarioId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
        
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        
        curso.getUsuariosInscritos().remove(usuario);
        cursoRepository.save(curso);
    }
    
    public long contarCursos() {
        return cursoRepository.count();
    }
    
    public long contarCursosAtivos() {
        return cursoRepository.countByStatus(Curso.StatusCurso.ATIVO);
    }
    
    public long contarCursosPorCategoria(String categoria) {
        return cursoRepository.countByCategoria(categoria);
    }
}

