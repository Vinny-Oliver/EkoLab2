package com.ekolab.dto;

import com.ekolab.model.Curso;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CursoRequest {
    
    @NotBlank(message = "Nome do curso é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;
    
    @NotNull(message = "Carga horária é obrigatória")
    @Min(value = 1, message = "Carga horária deve ser pelo menos 1 hora")
    private Integer cargaHoraria;
    
    @NotNull(message = "Nível é obrigatório")
    private Curso.Nivel nivel;
    
    @NotNull(message = "Modalidade é obrigatória")
    private Curso.Modalidade modalidade;
    
    private Curso.StatusCurso status = Curso.StatusCurso.ATIVO;
    
    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 50, message = "Categoria deve ter no máximo 50 caracteres")
    private String categoria;
    
    @NotBlank(message = "Instrutor é obrigatório")
    @Size(max = 100, message = "Nome do instrutor deve ter no máximo 100 caracteres")
    private String instrutor;
    
    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    private BigDecimal preco;
    
    private Boolean certificado = true;
    
    private String imagemUrl;
    
    private String videoApresentacaoUrl;
    
    @Size(max = 500, message = "Requisitos devem ter no máximo 500 caracteres")
    private String requisitos;
    
    @Size(max = 2000, message = "Conteúdo programático deve ter no máximo 2000 caracteres")
    private String conteudoProgramatico;
    
    // Construtores
    public CursoRequest() {}
    
    // Getters e Setters
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
    
    public Curso.Nivel getNivel() {
        return nivel;
    }
    
    public void setNivel(Curso.Nivel nivel) {
        this.nivel = nivel;
    }
    
    public Curso.Modalidade getModalidade() {
        return modalidade;
    }
    
    public void setModalidade(Curso.Modalidade modalidade) {
        this.modalidade = modalidade;
    }
    
    public Curso.StatusCurso getStatus() {
        return status;
    }
    
    public void setStatus(Curso.StatusCurso status) {
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
}

