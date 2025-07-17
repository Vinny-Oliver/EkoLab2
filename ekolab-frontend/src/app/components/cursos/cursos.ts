import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Curso } from '../../models/curso.model';
import { CursoService } from '../../services/curso.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-cursos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cursos.html',
  styleUrls: ['./cursos.css']
})
export class CursosComponent implements OnInit {
  cursos: Curso[] = [];
  cursosFiltrados: Curso[] = [];
  isLoading = true;
  
  filtros = {
    busca: '',
    categoria: '',
    nivel: '',
    modalidade: ''
  };

  constructor(
    private cursoService: CursoService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.carregarCursos();
  }

  private carregarCursos() {
    this.cursoService.getCursosPublicos().subscribe({
      next: (cursos) => {
        this.cursos = cursos;
        this.cursosFiltrados = [...cursos];
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
    this.cursos = [
      {
        id: 1,
        nome: 'Programação Web Completa',
        descricao: 'Aprenda HTML, CSS, JavaScript e frameworks modernos para criar aplicações web incríveis do zero. Curso completo com projetos práticos.',
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
        descricao: 'Domine SQL, MySQL e conceitos fundamentais de modelagem de dados para aplicações robustas e escaláveis.',
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
        descricao: 'Comece sua jornada na programação com Python, uma das linguagens mais populares e versáteis do mercado.',
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
      },
      {
        id: 4,
        nome: 'React.js Avançado',
        descricao: 'Desenvolva aplicações React modernas com hooks, context API, Redux e as melhores práticas do mercado.',
        cargaHoraria: 45,
        nivel: 'AVANCADO',
        modalidade: 'ONLINE',
        categoria: 'Desenvolvimento Web',
        instrutor: 'Prof. Ana Costa',
        preco: 299.99,
        certificado: true,
        imagem: '',
        video: '',
        dataInicio: new Date('2024-03-15'),
        dataFim: new Date('2024-05-15'),
        vagasDisponiveis: 25,
        status: 'ATIVO',
        dataCriacao: new Date(),
        dataAtualizacao: new Date()
      },
      {
        id: 5,
        nome: 'Desenvolvimento Mobile com Flutter',
        descricao: 'Crie aplicativos móveis nativos para Android e iOS usando Flutter e Dart.',
        cargaHoraria: 50,
        nivel: 'INTERMEDIARIO',
        modalidade: 'HIBRIDO',
        categoria: 'Mobile',
        instrutor: 'Prof. Roberto Lima',
        preco: 399.99,
        certificado: true,
        imagem: '',
        video: '',
        dataInicio: new Date('2024-04-01'),
        dataFim: new Date('2024-06-01'),
        vagasDisponiveis: 20,
        status: 'ATIVO',
        dataCriacao: new Date(),
        dataAtualizacao: new Date()
      },
      {
        id: 6,
        nome: 'DevOps com Docker e Kubernetes',
        descricao: 'Aprenda containerização, orquestração e deploy automatizado de aplicações.',
        cargaHoraria: 60,
        nivel: 'AVANCADO',
        modalidade: 'ONLINE',
        categoria: 'DevOps',
        instrutor: 'Prof. Lucas Ferreira',
        preco: 499.99,
        certificado: true,
        imagem: '',
        video: '',
        dataInicio: new Date('2024-04-15'),
        dataFim: new Date('2024-07-15'),
        vagasDisponiveis: 15,
        status: 'ATIVO',
        dataCriacao: new Date(),
        dataAtualizacao: new Date()
      }
    ];

    this.cursosFiltrados = [...this.cursos];
  }

  aplicarFiltros() {
    this.cursosFiltrados = this.cursoService.filtrarCursos(this.cursos, this.filtros);
  }

  limparFiltros() {
    this.filtros = {
      busca: '',
      categoria: '',
      nivel: '',
      modalidade: ''
    };
    this.cursosFiltrados = [...this.cursos];
  }

  verDetalhes(curso: Curso) {
    // Implementar navegação para detalhes do curso
    console.log('Ver detalhes do curso:', curso.nome);
  }

  inscreverCurso(curso: Curso) {
    if (!this.authService.isLoggedIn()) {
      alert('Você precisa fazer login para se inscrever em um curso.');
      return;
    }

    if (curso.vagasDisponiveis && curso.vagasDisponiveis > 0) {
      // Simulação de inscrição - pode ser implementado posteriormente
      alert('Inscrição realizada com sucesso!');
      // Atualizar o número de vagas disponíveis
      if (curso.vagasDisponiveis) {
        curso.vagasDisponiveis--;
      }
    }
  }

  getNivelLabel(nivel: string): string {
    const labels: { [key: string]: string } = {
      'INICIANTE': 'Iniciante',
      'INTERMEDIARIO': 'Intermediário',
      'AVANCADO': 'Avançado'
    };
    return labels[nivel] || nivel;
  }

  getModalidadeLabel(modalidade: string): string {
    const labels: { [key: string]: string } = {
      'ONLINE': 'Online',
      'PRESENCIAL': 'Presencial',
      'HIBRIDO': 'Híbrido'
    };
    return labels[modalidade] || modalidade;
  }

  formatDate(date: Date): string {
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
  }
}

