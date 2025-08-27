# TennisClub API - Guia de Uso

**Base URL:** `/api/joaopedroaguiar/tennisclub/v1`  
**Desenvolvido por:** João Pedro Aguiar  
**Versão:** 1.0.0

---

## 📋 Índice

1. [Visão Geral](#visão-geral)
2. [Autenticação](#autenticação)
3. [Endpoints de Usuários](#endpoints-de-usuários)
4. [Endpoints de Quadras](#endpoints-de-quadras)
5. [Endpoints de Agendamentos](#endpoints-de-agendamentos)
6. [Códigos de Status](#códigos-de-status)
7. [Exemplos de Uso](#exemplos-de-uso)
8. [Swagger UI](#swagger-ui)

---

## 🌟 Visão Geral

A API REST do TennisClub fornece endpoints completos para gerenciamento de um sistema de agendamento de quadras de tênis. A API segue princípios RESTful e retorna dados em formato JSON.

### Características Principais
- ✅ API RESTful completa
- ✅ Documentação automática com Swagger/OpenAPI
- ✅ Validação robusta de dados
- ✅ Tratamento de erros padronizado
- ✅ CORS habilitado para desenvolvimento
- ✅ Suporte a operações CRUD completas

---

## 🔐 Autenticação

**Nota:** Para fins de demonstração do portfólio, a API está configurada com acesso público. Em produção, seria implementada autenticação JWT ou OAuth2.

```http
# Headers recomendados para todas as requisições
Content-Type: application/json
Accept: application/json
```

---

## 👥 Endpoints de Usuários

### Listar Todos os Usuários
```http
GET /users
```

**Resposta:**
```json
[
  {
    "id": 1,
    "name": "João Pedro Aguiar",
    "email": "joao.pedro@tennisclub.com",
    "role": "ADMIN",
    "isActive": true,
    "createdAt": "2024-08-27T10:00:00",
    "updatedAt": null
  }
]
```

### Criar Novo Usuário
```http
POST /users
```

**Body:**
```json
{
  "name": "Maria Silva",
  "email": "maria.silva@email.com",
  "password": "password123",
  "role": "CLIENT"
}
```

**Resposta (201 Created):**
```json
{
  "id": 2,
  "name": "Maria Silva",
  "email": "maria.silva@email.com",
  "role": "CLIENT",
  "isActive": true,
  "createdAt": "2024-08-27T14:30:00",
  "updatedAt": null
}
```

### Buscar Usuário por ID
```http
GET /users/{id}
```

### Buscar Usuário por Email
```http
GET /users/email/{email}
```

### Buscar Usuários por Nome
```http
GET /users/search?name=João
```

### Buscar Usuários por Role
```http
GET /users/role/CLIENT
```

### Listar Usuários Ativos
```http
GET /users/active
```

### Atualizar Usuário
```http
PUT /users/{id}
```

**Body:**
```json
{
  "name": "João Pedro Aguiar Santos",
  "email": "joao.pedro@tennisclub.com",
  "role": "ADMIN",
  "isActive": true
}
```

### Ativar/Desativar Usuário
```http
PATCH /users/{id}/status
```

**Body:**
```json
{
  "isActive": false
}
```

### Excluir Usuário
```http
DELETE /users/{id}
```

### Contar Usuários Ativos
```http
GET /users/count/active
```

**Resposta:**
```json
{
  "count": 15
}
```

### Verificar Disponibilidade de Email
```http
GET /users/email-available?email=novo@email.com
```

**Resposta:**
```json
{
  "available": true
}
```

---

## 🏟️ Endpoints de Quadras

### Listar Todas as Quadras
```http
GET /courts
```

**Resposta:**
```json
[
  {
    "id": 1,
    "name": "Quadra Central",
    "description": "Quadra principal com arquibancada e iluminação profissional",
    "pricePerHour": 80.00,
    "isActive": true,
    "createdAt": "2024-08-27T08:00:00",
    "updatedAt": null
  }
]
```

### Criar Nova Quadra
```http
POST /courts
```

**Body:**
```json
{
  "name": "Quadra Premium",
  "description": "Quadra VIP com todas as comodidades",
  "pricePerHour": 120.00
}
```

### Listar Quadras Ativas
```http
GET /courts/active
```

### Listar Quadras por Preço (Crescente)
```http
GET /courts/active/by-price
```

### Listar Quadras por Nome (Alfabética)
```http
GET /courts/active/by-name
```

### Buscar Quadra por ID
```http
GET /courts/{id}
```

### Buscar Quadras por Nome
```http
GET /courts/search?name=Central
```

### Buscar Quadras por Faixa de Preço
```http
GET /courts/price-range?minPrice=50.00&maxPrice=100.00
```

### Buscar Quadras por Preço Máximo
```http
GET /courts/max-price?maxPrice=80.00
```

### Buscar Quadra Mais Barata
```http
GET /courts/cheapest
```

### Atualizar Quadra
```http
PUT /courts/{id}
```

### Ativar/Desativar Quadra
```http
PATCH /courts/{id}/status
```

**Body:**
```json
{
  "isActive": false
}
```

### Excluir Quadra
```http
DELETE /courts/{id}
```

### Contar Quadras Ativas
```http
GET /courts/count/active
```

---

## 📅 Endpoints de Agendamentos

### Listar Todos os Agendamentos
```http
GET /bookings
```

**Resposta:**
```json
[
  {
    "id": 1,
    "courtId": 1,
    "courtName": "Quadra Central",
    "userId": 2,
    "userName": "Maria Silva",
    "bookingDate": "2024-08-28",
    "startTime": "08:00:00",
    "endTime": "10:00:00",
    "status": "CONFIRMED",
    "totalPrice": 160.00,
    "notes": "Jogo duplas - torneio interno",
    "createdAt": "2024-08-27T15:00:00",
    "updatedAt": null
  }
]
```

### Criar Novo Agendamento
```http
POST /bookings
```

**Body:**
```json
{
  "courtId": 1,
  "userId": 2,
  "bookingDate": "2024-08-29",
  "startTime": "14:00:00",
  "endTime": "16:00:00",
  "notes": "Aula particular"
}
```

**Resposta (201 Created):**
```json
{
  "id": 6,
  "courtId": 1,
  "courtName": "Quadra Central",
  "userId": 2,
  "userName": "Maria Silva",
  "bookingDate": "2024-08-29",
  "startTime": "14:00:00",
  "endTime": "16:00:00",
  "status": "PENDING",
  "totalPrice": 160.00,
  "notes": "Aula particular",
  "createdAt": "2024-08-27T16:00:00",
  "updatedAt": null
}
```

### Buscar Agendamento por ID
```http
GET /bookings/{id}
```

### Buscar Agendamentos por Usuário
```http
GET /bookings/user/{userId}
```

### Buscar Agendamentos por Quadra
```http
GET /bookings/court/{courtId}
```

### Buscar Agendamentos por Data
```http
GET /bookings/date/2024-08-28
```

### Buscar Agendamentos por Status
```http
GET /bookings/status/CONFIRMED
```

### Buscar Agendamentos Futuros por Usuário
```http
GET /bookings/user/{userId}/future
```

### Buscar Agendamentos de Hoje
```http
GET /bookings/today
```

### Verificar Horários Disponíveis
```http
GET /bookings/available-times?courtId=1&date=2024-08-29
```

**Resposta:**
```json
[
  {
    "startTime": "06:00:00",
    "endTime": "07:00:00",
    "available": true
  },
  {
    "startTime": "07:00:00",
    "endTime": "08:00:00",
    "available": true
  },
  {
    "startTime": "08:00:00",
    "endTime": "09:00:00",
    "available": false
  }
]
```

### Atualizar Agendamento
```http
PUT /bookings/{id}
```

### Atualizar Status do Agendamento
```http
PATCH /bookings/{id}/status
```

**Body:**
```json
{
  "status": "CONFIRMED"
}
```

### Cancelar Agendamento
```http
PATCH /bookings/{id}/cancel
```

### Confirmar Agendamento
```http
PATCH /bookings/{id}/confirm
```

### Excluir Agendamento
```http
DELETE /bookings/{id}
```

### Contar Agendamentos por Status
```http
GET /bookings/count/status/CONFIRMED
```

---

## 📊 Códigos de Status

| Código | Descrição | Uso |
|--------|-----------|-----|
| 200 | OK | Operação bem-sucedida |
| 201 | Created | Recurso criado com sucesso |
| 204 | No Content | Operação bem-sucedida sem conteúdo |
| 400 | Bad Request | Dados inválidos ou malformados |
| 404 | Not Found | Recurso não encontrado |
| 409 | Conflict | Conflito (ex: email já existe, horário ocupado) |
| 500 | Internal Server Error | Erro interno do servidor |

---

## 💡 Exemplos de Uso

### Fluxo Completo de Agendamento

1. **Verificar quadras disponíveis:**
```http
GET /courts/active
```

2. **Verificar horários disponíveis:**
```http
GET /bookings/available-times?courtId=1&date=2024-08-29
```

3. **Criar agendamento:**
```http
POST /bookings
{
  "courtId": 1,
  "userId": 2,
  "bookingDate": "2024-08-29",
  "startTime": "14:00:00",
  "endTime": "16:00:00"
}
```

4. **Confirmar agendamento:**
```http
PATCH /bookings/6/confirm
```

### Busca Avançada de Quadras

```http
# Quadras até R$ 80/hora, ordenadas por preço
GET /courts/max-price?maxPrice=80.00

# Quadras em faixa específica
GET /courts/price-range?minPrice=60.00&maxPrice=100.00

# Quadra mais barata disponível
GET /courts/cheapest
```

### Relatórios e Estatísticas

```http
# Total de usuários ativos
GET /users/count/active

# Total de quadras ativas
GET /courts/count/active

# Agendamentos confirmados
GET /bookings/count/status/CONFIRMED

# Agendamentos de hoje
GET /bookings/today
```

---

## 📚 Swagger UI

A documentação interativa da API está disponível em:

```
http://localhost:8080/api/joaopedroaguiar/tennisclub/v1/swagger-ui.html
```

### Recursos do Swagger UI:
- 📖 Documentação completa de todos os endpoints
- 🧪 Interface para testar requisições diretamente
- 📝 Exemplos de request/response
- 🔍 Exploração interativa da API
- 📋 Schemas de dados detalhados

### Console H2 (Desenvolvimento):
```
http://localhost:8080/api/joaopedroaguiar/tennisclub/v1/h2-console
```

**Configurações de conexão:**
- JDBC URL: `jdbc:h2:mem:tennisclub`
- Username: `sa`
- Password: `password`

---

## 🚀 Dados de Exemplo

A API inclui dados de exemplo para demonstração:

### Usuários Padrão:
- **Admin:** joao.pedro@tennisclub.com (senha: password123)
- **Clientes:** maria.silva@email.com, carlos.santos@email.com, etc.

### Quadras Disponíveis:
- Quadra Central (R$ 80/hora)
- Quadra Norte (R$ 70/hora)
- Quadra Sul (R$ 60/hora)
- Quadra Leste (R$ 75/hora)
- Quadra Oeste (R$ 50/hora)

### Agendamentos de Exemplo:
- Diversos agendamentos para demonstrar diferentes cenários
- Status variados (PENDING, CONFIRMED, CANCELED)
- Horários futuros para testes

---

## 🔧 Configuração para Desenvolvimento

### Executar Backend:
```bash
cd backend
mvn spring-boot:run
```

### Executar Frontend:
```bash
cd frontend
npm run dev
```

### URLs de Desenvolvimento:
- **API:** http://localhost:8080/api/joaopedroaguiar/tennisclub/v1
- **Swagger:** http://localhost:8080/api/joaopedroaguiar/tennisclub/v1/swagger-ui.html
- **Frontend:** http://localhost:5173

---

**Desenvolvido por João Pedro Aguiar**  
**GitHub:** https://github.com/Joaopedromartins21  
**Projeto de Portfólio - 2024**

