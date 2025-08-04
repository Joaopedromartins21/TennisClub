# 🎾 TennisClub

Projeto Full Stack de um sistema para gerenciamento de clubes de tênis, com **backend em Java Spring Boot** e **frontend em React**. O objetivo é permitir o agendamento de treinos, cadastro de jogadores, controle de partidas e visualização de estatísticas.

---

## 🧠 Funcionalidades

- ✅ Cadastro e gerenciamento de jogadores
- ✅ Agendamento de treinos e partidas
- ✅ Registro e visualização de estatísticas dos jogadores
- ✅ Integração com banco de dados PostgreSQL
- ✅ Frontend com React integrado ao backend
- 🚧 (Planejado) Sistema de login e autenticação

---

## 🛠️ Tecnologias Utilizadas

### Backend:
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

### Frontend:
- React.js
- Axios (para requisições HTTP)
- React Router
- Tailwind CSS (ou outro framework, se aplicável)

---

## 🗂️ Estrutura do Projeto

```bash
TennisClub/
├── backend/              # Java Spring Boot
│   ├── src/
│   ├── pom.xml
│   └── ...
└── frontend/             # React
    ├── public/
    ├── src/
    │   ├── components/
    │   ├── pages/
    │   ├── App.jsx
    │   └── ...
    ├── package.json
    └── ...

🚀 Como Executar o Projeto
📦 Backend
Clone o repositório:

bash
Copy
Edit
git clone https://github.com/Joaopedromartins21/TennisClub.git
Configure o PostgreSQL no application.properties:

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://localhost:5432/tennisclub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
Execute o backend com:

bash
Copy
Edit
./mvnw spring-boot:run
💻 Frontend
Acesse a pasta do frontend:

bash
Copy
Edit
cd frontend
Instale as dependências:

bash
Copy
Edit
npm install
Execute o frontend:

bash
Copy
Edit
npm start
O projeto estará disponível em http://localhost:3000.

📌 Endpoints Exemplo
GET /jogadores — lista todos os jogadores

POST /jogadores — cadastra um novo jogador

PUT /jogadores/{id} — atualiza um jogador

DELETE /jogadores/{id} — remove um jogador

👨‍💻 Autor
João Pedro Aguiar Martins
📧 jpmarttins.dev@gmail.com
💼 Projeto desenvolvido com fins de aprendizado e portfólio.

