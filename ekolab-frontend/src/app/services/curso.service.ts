import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Curso } from '../models/curso.model';
import { AuthService } from './auth.service';

const CURSO_API = 'http://localhost:8080/api/cursos/';

@Injectable({
  providedIn: 'root'
})
export class CursoService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  // Métodos públicos (não requerem autenticação)
  getCursosPublicos(): Observable<Curso[]> {
    return this.http.get<Curso[]>(CURSO_API + 'publicos');
  }

  getCursoPublico(id: number): Observable<Curso> {
    return this.http.get<Curso>(`${CURSO_API}publicos/${id}`);
  }

  buscarCursosPublicos(filtros: any): Observable<Curso[]> {
    let params = new HttpParams();
    
    if (filtros.nome) {
      params = params.set('nome', filtros.nome);
    }
    if (filtros.categoria) {
      params = params.set('categoria', filtros.categoria);
    }
    if (filtros.nivel) {
      params = params.set('nivel', filtros.nivel);
    }
    if (filtros.modalidade) {
      params = params.set('modalidade', filtros.modalidade);
    }
    
    return this.http.get<Curso[]>(CURSO_API + 'publicos/buscar', { params });
  }

  getCursosPorCategoria(categoria: string): Observable<Curso[]> {
    return this.http.get<Curso[]>(`${CURSO_API}publicos/categoria/${categoria}`);
  }

  getCursosRecentes(): Observable<Curso[]> {
    return this.http.get<Curso[]>(CURSO_API + 'publicos/recentes');
  }

  getCategorias(): Observable<string[]> {
    return this.http.get<string[]>(CURSO_API + 'publicos/categorias');
  }

  getInstrutores(): Observable<string[]> {
    return this.http.get<string[]>(CURSO_API + 'publicos/instrutores');
  }

  // Métodos administrativos (agora sem autenticação)
  getAllCursos(): Observable<Curso[]> {
    return this.http.get<Curso[]>(CURSO_API);
  }

  getCurso(id: number): Observable<Curso> {
    return this.http.get<Curso>(`${CURSO_API}${id}`);
  }

  criarCurso(curso: any): Observable<Curso> {
    const headers = this.authService.getHeaders();
    return this.http.post<Curso>(CURSO_API, curso, { headers });
  }

  atualizarCurso(id: number, curso: any): Observable<Curso> {
    const headers = this.authService.getHeaders();
    return this.http.put<Curso>(`${CURSO_API}${id}`, curso, { headers });
  }

  deletarCurso(id: number): Observable<any> {
    const headers = this.authService.getHeaders();
    return this.http.delete(`${CURSO_API}${id}`, { headers });
  }

  ativarCurso(id: number): Observable<any> {
    const headers = this.authService.getHeaders();
    return this.http.put(`${CURSO_API}${id}/ativar`, {}, { headers });
  }

  inativarCurso(id: number): Observable<any> {
    const headers = this.authService.getHeaders();
    return this.http.put(`${CURSO_API}${id}/inativar`, {}, { headers });
  }

  inscreverUsuario(cursoId: number, usuarioId: number): Observable<any> {
    const headers = this.authService.getHeaders();
    return this.http.post(`${CURSO_API}${cursoId}/inscrever/${usuarioId}`, {}, { headers });
  }

  desinscreverUsuario(cursoId: number, usuarioId: number): Observable<any> {
    const headers = this.authService.getHeaders();
    return this.http.delete(`${CURSO_API}${cursoId}/desinscrever/${usuarioId}`, { headers });
  }

  getEstatisticas(): Observable<any> {
    return this.http.get(`${CURSO_API}estatisticas`);
  }

  // Método para filtrar cursos localmente
  filtrarCursos(cursos: Curso[], filtros: any): Curso[] {
    return cursos.filter(curso => {
      const matchBusca = !filtros.busca || 
        curso.nome.toLowerCase().includes(filtros.busca.toLowerCase()) ||
        curso.descricao.toLowerCase().includes(filtros.busca.toLowerCase());

      const matchCategoria = !filtros.categoria || 
        curso.categoria === filtros.categoria;

      const matchNivel = !filtros.nivel || 
        curso.nivel === filtros.nivel;

      const matchModalidade = !filtros.modalidade || 
        curso.modalidade === filtros.modalidade;

      return matchBusca && matchCategoria && matchNivel && matchModalidade;
    });
  }
}

