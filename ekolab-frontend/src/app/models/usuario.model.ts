export interface Usuario {
  id?: number;
  nome: string;
  email: string;
  cpf: string;
  dataNascimento: Date;
  tipo?: 'USER' | 'ADMIN';
  status?: 'ATIVO' | 'INATIVO';
  dataCriacao?: Date;
  dataAtualizacao?: Date;
}

export interface UsuarioRequest {
  nomeCompleto: string;
  email: string;
  senha: string;
  cpf: string;
  dataNascimento: string;
  tipo: 'USER' | 'ADMIN';
}

