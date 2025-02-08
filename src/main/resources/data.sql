INSERT INTO tb_user (username, name, password, role) 
VALUES 
('user1', '홍길동', '$2a$10$xxxxxxxxxxx', 'ROLE_USER'),
('user2', '김철수', '$2a$10$xxxxxxxxxxx', 'ROLE_USER'),
('user3', '이영희', '$2a$10$xxxxxxxxxxx', 'ROLE_USER');

INSERT INTO tb_history (username, donation_date, amount) 
VALUES 
('user1', '2024-03-10', 10000),
('user2', '2024-03-09', 50000),
('user3', '2024-03-08', 20000),
('user1', '2024-03-07', 30000),
('user2', '2024-03-06', 40000); 