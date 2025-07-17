import { Usuario } from './usuario.model';

export interface JwtResponse {
  token: string;
  usuario: Usuario;
}

