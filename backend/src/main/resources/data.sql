-- Dados iniciais para o sistema TennisClub
-- Desenvolvido por: João Pedro Aguiar

-- Inserir usuários de teste
INSERT INTO users (name, email, password, role, is_active, created_at) VALUES
('João Pedro Aguiar', 'joao.pedro@tennisclub.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXYLFSZwZSfpR7.JM8GEg/7fykK', 'ADMIN', true, NOW()),
('Maria Silva', 'maria.silva@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXYLFSZwZSfpR7.JM8GEg/7fykK', 'CLIENT', true, NOW()),
('Carlos Santos', 'carlos.santos@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXYLFSZwZSfpR7.JM8GEg/7fykK', 'CLIENT', true, NOW()),
('Ana Costa', 'ana.costa@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXYLFSZwZSfpR7.JM8GEg/7fykK', 'CLIENT', true, NOW()),
('Pedro Oliveira', 'pedro.oliveira@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXYLFSZwZSfpR7.JM8GEg/7fykK', 'CLIENT', true, NOW());

-- Inserir quadras de teste
INSERT INTO courts (name, description, price_per_hour, is_active, created_at) VALUES
('Quadra Central', 'Quadra principal com arquibancada e iluminação profissional', 80.00, true, NOW()),
('Quadra Norte', 'Quadra coberta ideal para jogos em qualquer clima', 70.00, true, NOW()),
('Quadra Sul', 'Quadra ao ar livre com vista panorâmica', 60.00, true, NOW()),
('Quadra Leste', 'Quadra com piso sintético de alta qualidade', 75.00, true, NOW()),
('Quadra Oeste', 'Quadra econômica para treinos e aulas', 50.00, true, NOW());

-- Inserir alguns agendamentos de exemplo
INSERT INTO bookings (court_id, user_id, booking_date, start_time, end_time, status, total_price, created_at, notes) VALUES
(1, 2, CURRENT_DATE + 1, '08:00:00', '10:00:00', 'CONFIRMED', 160.00, NOW(), 'Jogo duplas - torneio interno'),
(2, 3, CURRENT_DATE + 1, '14:00:00', '15:00:00', 'PENDING', 70.00, NOW(), 'Aula particular'),
(3, 4, CURRENT_DATE + 2, '09:00:00', '11:00:00', 'CONFIRMED', 120.00, NOW(), 'Treino individual'),
(4, 5, CURRENT_DATE + 2, '16:00:00', '17:00:00', 'PENDING', 75.00, NOW(), 'Jogo recreativo'),
(1, 2, CURRENT_DATE + 3, '10:00:00', '12:00:00', 'CONFIRMED', 160.00, NOW(), 'Partida amistosa');

-- Comentários sobre os dados inseridos:
-- Senha padrão para todos os usuários: "password123" (hash BCrypt)
-- João Pedro Aguiar é o administrador do sistema
-- As quadras têm preços variados para demonstrar diferentes cenários
-- Os agendamentos são criados para datas futuras para demonstrar o funcionamento

