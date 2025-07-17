import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { LoginRequest } from '../models/login-request.model';
import { UsuarioRequest } from '../models/usuario.model';

const AUTH_API = 'http://localhost:8080/api/auth/';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<any>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    // Verificar se há usuário logado no localStorage
    const user = this.getUser();
    if (user) {
      this.currentUserSubject.next(user);
    }
  }

  login(credentials: LoginRequest): Observable<any> {
    return this.http.post<any>(AUTH_API + 'login', credentials)
      .pipe(
        tap(response => {
          if (response.usuario) {
            this.saveUser(response.usuario);
            this.currentUserSubject.next(response.usuario);
          }
        })
      );
  }

  cadastrar(userData: UsuarioRequest): Observable<any> {
    return this.http.post(AUTH_API + 'cadastro', userData);
  }

  logout(): void {
    localStorage.removeItem('usuario');
    this.currentUserSubject.next(null);
  }

  // Métodos para gerenciar dados do usuário no localStorage
  saveUser(user: any): void {
    localStorage.setItem('usuario', JSON.stringify(user));
  }

  getUser(): any {
    const user = localStorage.getItem('usuario');
    return user ? JSON.parse(user) : null;
  }

  isLoggedIn(): boolean {
    return this.getUser() !== null;
  }

  isAdmin(): boolean {
    const user = this.getUser();
    return user && user.tipoUsuario === 'ADMIN';
  }

  // Método para obter headers básicos
  getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json'
    });
  }

  // Método para verificar se o email já existe
  verificarEmail(email: string): Observable<any> {
    return this.http.post(AUTH_API + 'verificar-email', { email });
  }

  // Método para verificar se o CPF já existe
  verificarCpf(cpf: string): Observable<any> {
    return this.http.post(AUTH_API + 'verificar-cpf', { cpf });
  }
}

