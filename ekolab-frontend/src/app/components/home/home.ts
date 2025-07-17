import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Curso } from '../../models/curso.model';
import { CursoService } from '../../services/curso.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent implements OnInit {
  featuredCourses: Curso[] = [];
  isLoading = true;

  constructor(private cursoService: CursoService) {}

  ngOnInit() {
    this.loadFeaturedCourses();
  }

  private loadFeaturedCourses() {
    this.cursoService.getCursosPublicos().subscribe({
      next: (cursos) => {
        // Pegar apenas os 3 primeiros cursos como destaque
        this.featuredCourses = cursos.slice(0, 3);
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar cursos:', error);
        this.isLoading = false;
        // Usar dados mockados em caso de erro
        this.loadMockCourses();
      }
    });
  }

  private loadMockCourses() {
    this.featuredCourses = [
      {
        id: 1,
        nome: 'Programação Web Completa',
        descricao: 'Aprenda HTML, CSS, JavaScript e frameworks modernos para criar aplicações web incríveis do zero.',
        cargaHoraria: 40,
        nivel: 'INICIANTE',
        modalidade: 'ONLINE',
        categoria: 'Desenvolvimento Web',
        instrutor: 'Prof. João Silva',
        preco: 0,
        certificado: true,
        imagem: '',
        video: '',
        dataInicio: new Date('2024-02-01'),
        dataFim: new Date('2024-04-01'),
        vagasDisponiveis: 50,
        status: 'ATIVO',
        dataCriacao: new Date(),
        dataAtualizacao: new Date()
      },
      {
        id: 2,
        nome: 'Banco de Dados MySQL',
        descricao: 'Domine SQL, MySQL e conceitos fundamentais de modelagem de dados para aplicações robustas.',
        cargaHoraria: 30,
        nivel: 'INTERMEDIARIO',
        modalidade: 'ONLINE',
        categoria: 'Banco de Dados',
        instrutor: 'Prof. Maria Santos',
        preco: 0,
        certificado: true,
        imagem: '',
        video: '',
        dataInicio: new Date('2024-02-15'),
        dataFim: new Date('2024-03-15'),
        vagasDisponiveis: 30,
        status: 'ATIVO',
        dataCriacao: new Date(),
        dataAtualizacao: new Date()
      },
      {
        id: 3,
        nome: 'Python para Iniciantes',
        descricao: 'Comece sua jornada na programação com Python, uma das linguagens mais populares e versáteis.',
        cargaHoraria: 35,
        nivel: 'INICIANTE',
        modalidade: 'ONLINE',
        categoria: 'Programação',
        instrutor: 'Prof. Carlos Oliveira',
        preco: 0,
        certificado: true,
        imagem: '',
        video: '',
        dataInicio: new Date('2024-03-01'),
        dataFim: new Date('2024-04-15'),
        vagasDisponiveis: 40,
        status: 'ATIVO',
        dataCriacao: new Date(),
        dataAtualizacao: new Date()
      }
    ];
  }
}

