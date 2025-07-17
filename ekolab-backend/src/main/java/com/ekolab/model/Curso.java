package com.ekolab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do curso é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    @Column(name = "descricao", nullable = false, length = 1000)
    private String descricao;

    @NotNull(message = "Carga horária é obrigatória")
    @Min(value = 1, message = "Carga horária deve ser pelo menos 1 hora")
    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel", nullable = false)
    private Nivel nivel;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade", nullable = false)
    private Modalidade modalidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusCurso status = StatusCurso.ATIVO;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 50, message = "Categoria deve ter no máximo 50 caracteres")
    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;

    @NotBlank(message = "Instrutor é obrigatório")
    @Size(max = 100, message = "Nome do instrutor deve ter no máximo 100 caracteres")
    @Column(name = "instrutor", nullable = false, length = 100)
    private String instrutor;

    @NotNull(message = "Data de início é obrigatória")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull(message = "Preço é obrigatório")
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "certificado", nullable = false)
    private Boolean certificado = true;

    @Column(name = "imagem_url", length = 500)
    private String imagemUrl;

    @Column(name = "video_apresentacao_url", length = 500)
    private String videoApresentacaoUrl;

    @Size(max = 500, message = "Requisitos devem ter no máximo 500 caracteres")
    @Column(name = "requisitos", length = 500)
    private String requisitos;

    @Size(max = 2000, message = "Conteúdo programático deve ter no máximo 2000 caracteres")
    @Column(name = "conteudo_programatico", length = 2000)
    private String conteudoProgramatico;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "usuario_curso",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @JsonIgnore
    private Set<Usuario> usuariosInscritos = new HashSet<>();

    // Construtores
    public Curso() {}

    public Curso(String nome, String descricao, Integer cargaHoraria, Nivel nivel, 
                 Modalidade modalidade, String categoria, String instrutor, 
                 LocalDate dataInicio, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.nivel = nivel;
        this.modalidade = modalidade;
        this.categoria = categoria;
        this.instrutor = instrutor;
        this.dataInicio = dataInicio;
        this.preco = preco;
    }

    // Getters e Setters
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public StatusCurso getStatus() {
        return status;
    }

    public void setStatus(StatusCurso status) {
        this.status = status;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(String instrutor) {
        this.instrutor = instrutor;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getCertificado() {
        return certificado;
    }

    public void setCertificado(Boolean certificado) {
        this.certificado = certificado;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getVideoApresentacaoUrl() {
        return videoApresentacaoUrl;
    }

    public void setVideoApresentacaoUrl(String videoApresentacaoUrl) {
        this.videoApresentacaoUrl = videoApresentacaoUrl;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getConteudoProgramatico() {
        return conteudoProgramatico;
    }

    public void setConteudoProgramatico(String conteudoProgramatico) {
        this.conteudoProgramatico = conteudoProgramatico;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Set<Usuario> getUsuariosInscritos() {
        return usuariosInscritos;
    }

    public void setUsuariosInscritos(Set<Usuario> usuariosInscritos) {
        this.usuariosInscritos = usuariosInscritos;
    }

    // Enums
    public enum Nivel {
        INICIANTE, INTERMEDIARIO, AVANCADO
    }

    public enum Modalidade {
        ONLINE, PRESENCIAL, HIBRIDO
    }

    public enum StatusCurso {
        ATIVO, INATIVO, EM_BREVE, FINALIZADO
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", instrutor='" + instrutor + '\'' +
                ", nivel=" + nivel +
                ", modalidade=" + modalidade +
                ", status=" + status +
                ", dataInicio=" + dataInicio +
                ", preco=" + preco +
                '}';
    }
}