package com.ekolab.repository;

import com.ekolab.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    /**
     * Busca cursos ativos
     */
    List<Curso> findByStatus(Curso.StatusCurso status);

    /**
     * Busca cursos por categoria
     */
    List<Curso> findByCategoria(String categoria);

    /**
     * Busca cursos por nível
     */
    List<Curso> findByNivel(Curso.Nivel nivel);

    /**
     * Busca cursos por modalidade
     */
    List<Curso> findByModalidade(Curso.Modalidade modalidade);

    /**
     * Busca cursos por instrutor
     */
    List<Curso> findByInstrutor(String instrutor);

    /**
     * Busca cursos por nome (contendo)
     */
    @Query("SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Curso> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    /**
     * Busca cursos ativos por categoria
     */
    List<Curso> findByStatusAndCategoria(Curso.StatusCurso status, String categoria);

    /**
     * Busca cursos ativos por nível
     */
    List<Curso> findByStatusAndNivel(Curso.StatusCurso status, Curso.Nivel nivel);

    /**
     * Busca cursos por faixa de preço
     */
    List<Curso> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

    /**
     * Busca cursos que iniciam após uma data
     */
    List<Curso> findByDataInicioAfter(LocalDate data);

    /**
     * Busca cursos que iniciam antes de uma data
     */
    List<Curso> findByDataInicioBefore(LocalDate data);

    /**
     * Busca cursos ativos ordenados por data de criação (mais recentes primeiro)
     */
    List<Curso> findByStatusOrderByDataCriacaoDesc(Curso.StatusCurso status);

    /**
     * Busca cursos ativos ordenados por preço crescente
     */
    List<Curso> findByStatusOrderByPrecoAsc(Curso.StatusCurso status);

    /**
     * Busca cursos com certificado
     */
    List<Curso> findByCertificadoTrue();

    /**
     * Busca todas as categorias distintas
     */
    @Query("SELECT DISTINCT c.categoria FROM Curso c WHERE c.status = :status")
    List<String> findDistinctCategoriasByStatus(@Param("status") Curso.StatusCurso status);

    /**
     * Busca todos os instrutores distintos
     */
    @Query("SELECT DISTINCT c.instrutor FROM Curso c WHERE c.status = :status")
    List<String> findDistinctInstrutoresByStatus(@Param("status") Curso.StatusCurso status);

    /**
     * Conta cursos por status
     */
    long countByStatus(Curso.StatusCurso status);

    /**
     * Conta cursos por categoria
     */
    long countByCategoria(String categoria);

    /**
     * Busca cursos com filtros múltiplos
     */
    @Query("SELECT c FROM Curso c WHERE " +
           "(:categoria IS NULL OR c.categoria = :categoria) AND " +
           "(:nivel IS NULL OR c.nivel = :nivel) AND " +
           "(:modalidade IS NULL OR c.modalidade = :modalidade) AND " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:precoMin IS NULL OR c.preco >= :precoMin) AND " +
           "(:precoMax IS NULL OR c.preco <= :precoMax)")
    List<Curso> findCursosComFiltros(
        @Param("categoria") String categoria,
        @Param("nivel") Curso.Nivel nivel,
        @Param("modalidade") Curso.Modalidade modalidade,
        @Param("status") Curso.StatusCurso status,
        @Param("precoMin") BigDecimal precoMin,
        @Param("precoMax") BigDecimal precoMax
    );
}

