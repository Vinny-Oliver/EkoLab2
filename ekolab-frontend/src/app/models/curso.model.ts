export interface Curso {
  id?: number;
  nome: string;
  descricao: string;
  cargaHoraria: number;
  nivel: 'INICIANTE' | 'INTERMEDIARIO' | 'AVANCADO';
  modalidade: 'ONLINE' | 'PRESENCIAL' | 'HIBRIDO';
  status?: 'ATIVO' | 'INATIVO' | 'EM_BREVE' | 'FINALIZADO';
  categoria: string;
  instrutor: string;
  dataInicio: Date;
  dataFim?: Date;
  preco: number;
  certificado?: boolean;
  imagem?: string;
  video?: string;
  vagasDisponiveis?: number;
  requisitos?: string;
  conteudoProgramatico?: string;
  dataCriacao?: Date;
  dataAtualizacao?: Date;
}

