import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario.model';

const API_URL = 'http://localhost:8080/api/usuarios/';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }

  listarTodos(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(API_URL);
  }

  buscarPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(API_URL + id);
  }

  criarUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(API_URL, usuario);
  }

  atualizarUsuario(id: number, usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(API_URL + id, usuario);
  }

  deletarUsuario(id: number): Observable<any> {
    return this.http.delete(API_URL + id);
  }

  desativarUsuario(id: number): Observable<any> {
    return this.http.put(API_URL + id + '/desativar', {});
  }

  ativarUsuario(id: number): Observable<any> {
    return this.http.put(API_URL + id + '/ativar', {});
  }

  listarAtivos(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(API_URL + 'ativos');
  }

  buscarPorNome(nome: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(API_URL + 'buscar?nome=' + nome);
  }

  listarPorTipo(tipo: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(API_URL + 'tipo/' + tipo);
  }

  obterEstatisticas(): Observable<any> {
    return this.http.get<any>(API_URL + 'estatisticas');
  }
}

