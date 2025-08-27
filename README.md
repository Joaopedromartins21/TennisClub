# TennisClub - Sistema de Agendamento de Quadras de Tênis

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![React](https://img.shields.io/badge/React-18-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## 📋 Sobre o Projeto

O **TennisClub** é um sistema completo de agendamento de quadras de tênis desenvolvido como projeto de portfólio por **João Pedro Aguiar**. O sistema demonstra expertise em desenvolvimento full-stack utilizando as melhores práticas e tecnologias modernas.

### 🎯 Objetivo

Este projeto foi desenvolvido para demonstrar conhecimentos em:
- Desenvolvimento de APIs REST com Java e Spring Boot
- Criação de interfaces modernas com React
- Integração frontend-backend
- Documentação de APIs com Swagger/OpenAPI
- Boas práticas de desenvolvimento e arquitetura de software

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 3.2.0** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **MySQL** - Banco de dados relacional
- **H2 Database** - Banco de dados para desenvolvimento
- **Swagger/OpenAPI** - Documentação da API
- **Maven** - Gerenciamento de dependências

### Frontend
- **React 18** - Biblioteca JavaScript
- **Tailwind CSS** - Framework CSS
- **Shadcn/UI** - Componentes de interface
- **Lucide Icons** - Ícones
- **Vite** - Build tool
- **React Router** - Roteamento

## 📁 Estrutura do Projeto

```
TennisClub/
├── backend/                    # Aplicação Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/joaopedroaguiar/tennisclub/
│   │   │   │   ├── controller/     # Controladores REST
│   │   │   │   ├── service/        # Lógica de negócios
│   │   │   │   ├── repository/     # Repositórios JPA
│   │   │   │   ├── model/          # Entidades JPA
│   │   │   │   ├── dto/            # Data Transfer Objects
│   │   │   │   └── config/         # Configurações
│   │   │   └── resources/
│   │   │       ├── application.yml # Configurações da aplicação
│   │   │       └── data.sql        # Dados iniciais
│   │   └── test/                   # Testes unitários
│   └── pom.xml                     # Dependências Maven
├── frontend/                   # Aplicação React
│   ├── src/
│   │   ├── components/         # Componentes React
│   │   ├── assets/            # Recursos estáticos
│   │   └── App.jsx            # Componente principal
│   └── package.json           # Dependências npm
└── README.md                  # Este arquivo
```

## 🏗️ Arquitetura

### Backend (API REST)
- **Arquitetura em Camadas**: Controller → Service → Repository → Model
- **Padrão DTO**: Para transferência de dados entre camadas
- **Validação**: Bean Validation (JSR-303)
- **Tratamento de Exceções**: Global exception handling
- **Documentação**: Swagger UI automático

### Frontend (SPA)
- **Componentes Funcionais**: Hooks do React
- **Design Responsivo**: Mobile-first com Tailwind CSS
- **Componentização**: Reutilização de componentes UI
- **Estado Local**: useState e useEffect

## 🎲 Modelo de Dados

### Entidades Principais

#### User (Usuário)
- `id` - Identificador único
- `name` - Nome completo
- `email` - Email (único)
- `password` - Senha criptografada
- `role` - Papel (ADMIN/CLIENT)
- `isActive` - Status ativo

#### Court (Quadra)
- `id` - Identificador único
- `name` - Nome da quadra
- `description` - Descrição
- `pricePerHour` - Preço por hora
- `isActive` - Status ativo

#### Booking (Agendamento)
- `id` - Identificador único
- `court` - Referência à quadra
- `user` - Referência ao usuário
- `bookingDate` - Data do agendamento
- `startTime` - Horário de início
- `endTime` - Horário de fim
- `status` - Status (PENDING/CONFIRMED/CANCELED)
- `totalPrice` - Preço total

## 🔗 API Endpoints

### Base URL
```
/api/joaopedroaguiar/tennisclub/v1
```

### Principais Endpoints

#### Usuários
- `GET /users` - Listar usuários
- `POST /users` - Criar usuário
- `GET /users/{id}` - Buscar usuário por ID
- `PUT /users/{id}` - Atualizar usuário
- `DELETE /users/{id}` - Excluir usuário

#### Quadras
- `GET /courts` - Listar quadras
- `POST /courts` - Criar quadra
- `GET /courts/active` - Listar quadras ativas
- `GET /courts/{id}` - Buscar quadra por ID

#### Agendamentos
- `GET /bookings` - Listar agendamentos
- `POST /bookings` - Criar agendamento
- `GET /bookings/available-times` - Verificar disponibilidade
- `PATCH /bookings/{id}/confirm` - Confirmar agendamento
- `PATCH /bookings/{id}/cancel` - Cancelar agendamento

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Node.js 18+
- MySQL 8.0+ (opcional, usa H2 por padrão)
- Maven 3.6+

### Backend

1. **Clone o repositório**
```bash
git clone https://github.com/Joaopedromartins21/TennisClub.git
cd TennisClub/backend
```

2. **Execute a aplicação**
```bash
mvn spring-boot:run
```

3. **Acesse a documentação da API**
- Swagger UI: http://localhost:8080/api/joaopedroaguiar/tennisclub/v1/swagger-ui.html
- H2 Console: http://localhost:8080/api/joaopedroaguiar/tennisclub/v1/h2-console

### Frontend

1. **Instale as dependências**
```bash
cd ../frontend
npm install
```

2. **Execute o servidor de desenvolvimento**
```bash
npm run dev
```

3. **Acesse a aplicação**
- Frontend: http://localhost:5173

## 🧪 Testes

### Backend
```bash
cd backend
mvn test
```

### Frontend
```bash
cd frontend
npm test
```

## 📊 Funcionalidades

### ✅ Implementadas
- [x] CRUD completo de usuários
- [x] CRUD completo de quadras
- [x] Sistema de agendamentos com validações
- [x] Verificação de disponibilidade de horários
- [x] Interface web responsiva
- [x] Documentação automática da API
- [x] Autenticação e autorização
- [x] Dados de exemplo para demonstração

### 🔄 Futuras Melhorias
- [ ] Sistema de pagamentos
- [ ] Notificações por email
- [ ] Relatórios e dashboards
- [ ] App mobile
- [ ] Sistema de avaliações

## 🤝 Contribuição

Este é um projeto de portfólio pessoal, mas sugestões e feedback são sempre bem-vindos!

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

**João Pedro Aguiar**
- GitHub: [@Joaopedromartins21](https://github.com/Joaopedromartins21)
- Email: jpmarttins.dev@gmail.com

---

⭐ **Se este projeto foi útil para você, considere dar uma estrela no repositório!**

---

*Desenvolvido com ❤️ por João Pedro Aguiar como projeto de portfólio para demonstrar habilidades em desenvolvimento full-stack.*

