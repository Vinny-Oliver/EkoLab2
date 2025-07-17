package com.ekolab.service;

import com.ekolab.dto.UsuarioRequest;
import com.ekolab.model.Usuario;
import com.ekolab.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    public List<Usuario> listarAtivos() {
        return usuarioRepository.findByAtivoTrue();
    }
    
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Optional<Usuario> buscarPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }
    
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeCompletoContainingIgnoreCase(nome);
    }
    
    public List<Usuario> listarPorTipo(Usuario.TipoUsuario tipo) {
        return usuarioRepository.findByTipoUsuario(tipo);
    }
    
    public Usuario criarUsuario(UsuarioRequest request) {
        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já está em uso!");
        }
        
        // Verificar se CPF já existe
        if (usuarioRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já está em uso!");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(request.getNomeCompleto());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setCpf(request.getCpf());
        usuario.setDataNascimento(request.getDataNascimento());
        usuario.setTipoUsuario(request.getTipoUsuario());
        usuario.setAtivo(true);
        
        return usuarioRepository.save(usuario);
    }
    
    public Usuario atualizarUsuario(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        
        // Verificar se email já existe (exceto para o próprio usuário)
        Optional<Usuario> usuarioComEmail = usuarioRepository.findByEmail(request.getEmail());
        if (usuarioComEmail.isPresent() && !usuarioComEmail.get().getId().equals(id)) {
            throw new RuntimeException("Email já está em uso!");
        }
        
        // Verificar se CPF já existe (exceto para o próprio usuário)
        Optional<Usuario> usuarioComCpf = usuarioRepository.findByCpf(request.getCpf());
        if (usuarioComCpf.isPresent() && !usuarioComCpf.get().getId().equals(id)) {
            throw new RuntimeException("CPF já está em uso!");
        }
        
        usuario.setNomeCompleto(request.getNomeCompleto());
        usuario.setEmail(request.getEmail());
        usuario.setCpf(request.getCpf());
        usuario.setDataNascimento(request.getDataNascimento());
        usuario.setTipoUsuario(request.getTipoUsuario());
        
        // Só atualiza a senha se foi fornecida
        if (request.getSenha() != null && !request.getSenha().trim().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        }
        
        return usuarioRepository.save(usuario);
    }
    
    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        
        usuarioRepository.delete(usuario);
    }
    
    public void desativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
    
    public void ativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
    }
    
    public long contarUsuarios() {
        return usuarioRepository.count();
    }
    
    public long contarUsuariosAtivos() {
        return usuarioRepository.countByAtivoTrue();
    }
    
    public long contarUsuariosPorTipo(Usuario.TipoUsuario tipo) {
        return usuarioRepository.countByTipoUsuario(tipo);
    }
    
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    public boolean existeCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }
}
