-- Inserir usuário administrador padrão
INSERT INTO usuarios (nome_completo, email, senha, cpf, data_nascimento, tipo_usuario, ativo, data_criacao, data_atualizacao) 
VALUES ('Administrador EkoLab', 'admin@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdxIcnYbANhkPS9e', '12345678901', '1990-01-01', 'ADMIN', true, NOW(), NOW());

-- Inserir usuário comum de teste
INSERT INTO usuarios (nome_completo, email, senha, cpf, data_nascimento, tipo_usuario, ativo, data_criacao, data_atualizacao) 
VALUES ('João Silva', 'joao@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdxIcnYbANhkPS9e', '98765432100', '1995-05-15', 'USER', true, NOW(), NOW());

-- Inserir cursos de exemplo
INSERT INTO cursos (nome, descricao, carga_horaria, nivel, modalidade, categoria, instrutor, preco, certificado, data_inicio, status, data_criacao, data_atualizacao) 
VALUES ('Programação Web Completa', 'Aprenda HTML, CSS, JavaScript e frameworks modernos para criar aplicações web incríveis do zero.', 40, 'INICIANTE', 'ONLINE', 'Desenvolvimento Web', 'Prof. João Silva', 0.00, true, '2024-02-01', 'ATIVO', NOW(), NOW());

INSERT INTO cursos (nome, descricao, carga_horaria, nivel, modalidade, categoria, instrutor, preco, certificado, data_inicio, status, data_criacao, data_atualizacao) 
VALUES ('Banco de Dados MySQL', 'Domine SQL, MySQL e conceitos fundamentais de modelagem de dados para aplicações robustas.', 30, 'INTERMEDIARIO', 'ONLINE', 'Banco de Dados', 'Prof. Maria Santos', 0.00, true, '2024-02-15', 'ATIVO', NOW(), NOW());

INSERT INTO cursos (nome, descricao, carga_horaria, nivel, modalidade, categoria, instrutor, preco, certificado, data_inicio, status, data_criacao, data_atualizacao) 
VALUES ('Python para Iniciantes', 'Comece sua jornada na programação com Python, uma das linguagens mais populares e versáteis.', 35, 'INICIANTE', 'ONLINE', 'Programação', 'Prof. Carlos Oliveira', 0.00, true, '2024-03-01', 'ATIVO', NOW(), NOW());

INSERT INTO cursos (nome, descricao, carga_horaria, nivel, modalidade, categoria, instrutor, preco, certificado, data_inicio, status, data_criacao, data_atualizacao) 
VALUES ('React.js Avançado', 'Desenvolva aplicações React modernas com hooks, context API, Redux e as melhores práticas do mercado.', 45, 'AVANCADO', 'ONLINE', 'Desenvolvimento Web', 'Prof. Ana Costa', 299.99, true, '2024-03-15', 'ATIVO', NOW(), NOW());

INSERT INTO cursos (nome, descricao, carga_horaria, nivel, modalidade, categoria, instrutor, preco, certificado, data_inicio, status, data_criacao, data_atualizacao) 
VALUES ('Desenvolvimento Mobile com Flutter', 'Crie aplicativos móveis nativos para Android e iOS usando Flutter e Dart.', 50, 'INTERMEDIARIO', 'HIBRIDO', 'Mobile', 'Prof. Roberto Lima', 399.99, true, '2024-04-01', 'ATIVO', NOW(), NOW());

INSERT INTO cursos (nome, descricao, carga_horaria, nivel, modalidade, categoria, instrutor, preco, certificado, data_inicio, status, data_criacao, data_atualizacao) 
VALUES ('DevOps com Docker e Kubernetes', 'Aprenda containerização, orquestração e deploy automatizado de aplicações.', 60, 'AVANCADO', 'ONLINE', 'DevOps', 'Prof. Lucas Ferreira', 499.99, true, '2024-04-15', 'ATIVO', NOW(), NOW());
