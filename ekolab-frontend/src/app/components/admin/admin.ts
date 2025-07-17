import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CursoService } from '../../services/curso.service';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './admin.html',
  styleUrls: ['./admin.css']
})
export class AdminComponent implements OnInit {
  activeTab = 'dashboard';
  currentUser: any;
  isLoading = false;

  // Dados
  cursos: any[] = [];
  usuarios: any[] = [];
  cursosFiltrados: any[] = [];
  usuariosFiltrados: any[] = [];
  estatisticas: any = {};
  estatisticasUsuarios: any = {};

  // Filtros
  filtrosCurso = {
    busca: '',
    categoria: '',
    status: ''
  };

  filtrosUsuario = {
    busca: '',
    tipo: '',
    ativo: ''
  };

  // Modais
  showModalCurso = false;
  showModalUsuario = false;
  cursoEditando: any = null;
  usuarioEditando: any = null;

  // Forms
  cursoForm!: FormGroup;
  usuarioForm!: FormGroup;

  constructor(
    private authService: AuthService,
    private cursoService: CursoService,
    private usuarioService: UsuarioService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit() {
    this.currentUser = this.authService.getUser();
    
    if (!this.currentUser || !this.authService.isAdmin()) {
      this.router.navigate(['/login']);
      return;
    }

    this.initializeForms();
    this.loadData();
  }

  initializeForms() {
    this.cursoForm = this.formBuilder.group({
      nome: ['', [Validators.required]],
      descricao: ['', [Validators.required]],
      categoria: ['', [Validators.required]],
      instrutor: ['', [Validators.required]],
      cargaHoraria: ['', [Validators.required, Validators.min(1)]],
      nivel: ['', [Validators.required]],
      modalidade: ['', [Validators.required]],
      preco: ['', [Validators.required, Validators.min(0)]],
      dataInicio: ['', [Validators.required]],
      status: ['ATIVO', [Validators.required]],
      certificado: [true]
    });

    this.usuarioForm = this.formBuilder.group({
      nomeCompleto: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      cpf: ['', [Validators.required]],
      dataNascimento: ['', [Validators.required]],
      tipoUsuario: ['USER', [Validators.required]],
      senha: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  loadData() {
    this.loadCursos();
    this.loadUsuarios();
    this.loadEstatisticas();
  }

  loadCursos() {
    this.cursoService.getAllCursos().subscribe({
      next: (cursos) => {
        this.cursos = cursos;
        this.filtrarCursos();
      },
      error: (error) => {
        console.error('Erro ao carregar cursos:', error);
      }
    });
  }

  loadUsuarios() {
    this.usuarioService.listarTodos().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
        this.filtrarUsuarios();
      },
      error: (error) => {
        console.error('Erro ao carregar usuários:', error);
      }
    });
  }

  loadEstatisticas() {
    this.cursoService.getEstatisticas().subscribe({
      next: (stats) => {
        this.estatisticas = stats;
      },
      error: (error) => {
        console.error('Erro ao carregar estatísticas de cursos:', error);
      }
    });

    this.usuarioService.obterEstatisticas().subscribe({
      next: (stats) => {
        this.estatisticasUsuarios = stats;
      },
      error: (error) => {
        console.error('Erro ao carregar estatísticas de usuários:', error);
      }
    });
  }

  setActiveTab(tab: string) {
    this.activeTab = tab;
  }

  // Métodos de filtro
  filtrarCursos() {
    this.cursosFiltrados = this.cursos.filter(curso => {
      const matchBusca = !this.filtrosCurso.busca || 
        curso.nome.toLowerCase().includes(this.filtrosCurso.busca.toLowerCase()) ||
        curso.descricao.toLowerCase().includes(this.filtrosCurso.busca.toLowerCase());

      const matchCategoria = !this.filtrosCurso.categoria || 
        curso.categoria === this.filtrosCurso.categoria;

      const matchStatus = !this.filtrosCurso.status || 
        curso.status === this.filtrosCurso.status;

      return matchBusca && matchCategoria && matchStatus;
    });
  }

  filtrarUsuarios() {
    this.usuariosFiltrados = this.usuarios.filter(usuario => {
      const matchBusca = !this.filtrosUsuario.busca || 
        usuario.nomeCompleto.toLowerCase().includes(this.filtrosUsuario.busca.toLowerCase()) ||
        usuario.email.toLowerCase().includes(this.filtrosUsuario.busca.toLowerCase());

      const matchTipo = !this.filtrosUsuario.tipo || 
        usuario.tipoUsuario === this.filtrosUsuario.tipo;

      const matchAtivo = this.filtrosUsuario.ativo === '' || 
        usuario.ativo.toString() === this.filtrosUsuario.ativo;

      return matchBusca && matchTipo && matchAtivo;
    });
  }

  // Métodos de modal
  abrirModalCurso() {
    this.cursoEditando = null;
    this.cursoForm.reset();
    this.cursoForm.patchValue({
      status: 'ATIVO',
      certificado: true
    });
    this.showModalCurso = true;
  }

  fecharModalCurso() {
    this.showModalCurso = false;
    this.cursoEditando = null;
  }

  abrirModalUsuario() {
    this.usuarioEditando = null;
    this.usuarioForm.reset();
    this.usuarioForm.patchValue({
      tipoUsuario: 'USER'
    });
    this.showModalUsuario = true;
  }

  fecharModalUsuario() {
    this.showModalUsuario = false;
    this.usuarioEditando = null;
  }

  // Métodos de CRUD para cursos
  editarCurso(curso: any) {
    this.cursoEditando = curso;
    this.cursoForm.patchValue({
      nome: curso.nome,
      descricao: curso.descricao,
      categoria: curso.categoria,
      instrutor: curso.instrutor,
      cargaHoraria: curso.cargaHoraria,
      nivel: curso.nivel,
      modalidade: curso.modalidade,
      preco: curso.preco,
      dataInicio: curso.dataInicio,
      status: curso.status,
      certificado: curso.certificado
    });
    this.showModalCurso = true;
  }

  salvarCurso() {
    if (this.cursoForm.valid) {
      this.isLoading = true;
      const cursoData = this.cursoForm.value;

      const request = this.cursoEditando 
        ? this.cursoService.atualizarCurso(this.cursoEditando.id, cursoData)
        : this.cursoService.criarCurso(cursoData);

      request.subscribe({
        next: () => {
          this.isLoading = false;
          this.fecharModalCurso();
          this.loadCursos();
        },
        error: (error) => {
          this.isLoading = false;
          console.error('Erro ao salvar curso:', error);
        }
      });
    }
  }

  toggleStatusCurso(curso: any) {
    const request = curso.status === 'ATIVO' 
      ? this.cursoService.inativarCurso(curso.id)
      : this.cursoService.ativarCurso(curso.id);

    request.subscribe({
      next: () => {
        this.loadCursos();
      },
      error: (error) => {
        console.error('Erro ao alterar status do curso:', error);
      }
    });
  }

  deletarCurso(curso: any) {
    if (confirm(`Tem certeza que deseja excluir o curso "${curso.nome}"?`)) {
      this.cursoService.deletarCurso(curso.id).subscribe({
        next: () => {
          this.loadCursos();
        },
        error: (error) => {
          console.error('Erro ao deletar curso:', error);
        }
      });
    }
  }

  // Métodos de CRUD para usuários
  editarUsuario(usuario: any) {
    this.usuarioEditando = usuario;
    this.usuarioForm.patchValue({
      nomeCompleto: usuario.nomeCompleto,
      email: usuario.email,
      cpf: usuario.cpf,
      dataNascimento: usuario.dataNascimento,
      tipoUsuario: usuario.tipoUsuario
    });
    
    // Remove validação de senha para edição
    this.usuarioForm.get('senha')?.clearValidators();
    this.usuarioForm.get('senha')?.updateValueAndValidity();
    
    this.showModalUsuario = true;
  }

  salvarUsuario() {
    if (this.usuarioForm.valid) {
      this.isLoading = true;
      const usuarioData = this.usuarioForm.value;

      const request = this.usuarioEditando 
        ? this.usuarioService.atualizarUsuario(this.usuarioEditando.id, usuarioData)
        : this.usuarioService.criarUsuario(usuarioData);

      request.subscribe({
        next: () => {
          this.isLoading = false;
          this.fecharModalUsuario();
          this.loadUsuarios();
        },
        error: (error) => {
          this.isLoading = false;
          console.error('Erro ao salvar usuário:', error);
        }
      });
    }
  }

  toggleStatusUsuario(usuario: any) {
    const request = usuario.ativo 
      ? this.usuarioService.desativarUsuario(usuario.id)
      : this.usuarioService.ativarUsuario(usuario.id);

    request.subscribe({
      next: () => {
        this.loadUsuarios();
      },
      error: (error) => {
        console.error('Erro ao alterar status do usuário:', error);
      }
    });
  }

  deletarUsuario(usuario: any) {
    if (confirm(`Tem certeza que deseja excluir o usuário "${usuario.nomeCompleto}"?`)) {
      this.usuarioService.deletarUsuario(usuario.id).subscribe({
        next: () => {
          this.loadUsuarios();
        },
        error: (error) => {
          console.error('Erro ao deletar usuário:', error);
        }
      });
    }
  }

  // Métodos utilitários
  isFieldInvalid(fieldName: string): boolean {
    const field = this.cursoForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  isFieldInvalidUsuario(fieldName: string): boolean {
    const field = this.usuarioForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  getNivelLabel(nivel: string): string {
    const labels: any = {
      'INICIANTE': 'Iniciante',
      'INTERMEDIARIO': 'Intermediário',
      'AVANCADO': 'Avançado'
    };
    return labels[nivel] || nivel;
  }

  getModalidadeLabel(modalidade: string): string {
    const labels: any = {
      'ONLINE': 'Online',
      'PRESENCIAL': 'Presencial',
      'HIBRIDO': 'Híbrido'
    };
    return labels[modalidade] || modalidade;
  }

  getStatusLabel(status: string): string {
    const labels: any = {
      'ATIVO': 'Ativo',
      'INATIVO': 'Inativo',
      'EM_BREVE': 'Em Breve',
      'FINALIZADO': 'Finalizado'
    };
    return labels[status] || status;
  }

  formatCpf(cpf: string): string {
    if (!cpf) return '';
    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
  }

  formatDate(date: string): string {
    if (!date) return '';
    return new Date(date).toLocaleDateString('pt-BR');
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

