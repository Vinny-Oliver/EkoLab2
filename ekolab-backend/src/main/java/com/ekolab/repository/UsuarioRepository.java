package com.ekolab.repository;

import com.ekolab.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuário por email
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Busca usuário por CPF
     */
    Optional<Usuario> findByCpf(String cpf);

    /**
     * Verifica se existe usuário com o email
     */
    boolean existsByEmail(String email);

    /**
     * Verifica se existe usuário com o CPF
     */
    boolean existsByCpf(String cpf);

    /**
     * Busca usuários ativos
     */
    List<Usuario> findByAtivoTrue();

    /**
     * Busca usuários por tipo
     */
    List<Usuario> findByTipoUsuario(Usuario.TipoUsuario tipoUsuario);

    /**
     * Busca usuários por nome (contendo)
     */
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nomeCompleto) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNomeCompletoContainingIgnoreCase(@Param("nome") String nome);

    /**
     * Busca usuários ativos por tipo
     */
    List<Usuario> findByAtivoTrueAndTipoUsuario(Usuario.TipoUsuario tipoUsuario);

    /**
     * Conta total de usuários ativos
     */
    long countByAtivoTrue();

    /**
     * Conta usuários por tipo
     */
    long countByTipoUsuario(Usuario.TipoUsuario tipoUsuario);
}