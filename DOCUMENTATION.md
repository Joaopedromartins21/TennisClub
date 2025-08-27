# TennisClub - Documentação Técnica Completa

**Desenvolvido por:** João Pedro Aguiar  
**Data:** Agosto 2024  
**Versão:** 1.0.0  

---

## Sumário Executivo

O TennisClub representa um sistema completo de agendamento de quadras de tênis desenvolvido como projeto de portfólio, demonstrando expertise em desenvolvimento full-stack utilizando tecnologias modernas e boas práticas de engenharia de software. Este documento apresenta uma análise técnica abrangente do sistema, incluindo arquitetura, implementação, funcionalidades e considerações de design.

O projeto foi concebido para showcasing de habilidades técnicas em Java Spring Boot para o backend, React para o frontend, e integração completa entre as camadas, resultando em uma aplicação web robusta e escalável que simula um ambiente de produção real.

---

## 1. Visão Geral do Sistema

### 1.1 Propósito e Objetivos

O TennisClub foi desenvolvido com múltiplos objetivos estratégicos que vão além da simples funcionalidade de agendamento. Primariamente, o sistema serve como uma demonstração prática de competências em desenvolvimento full-stack, evidenciando a capacidade de criar soluções completas que integram backend robusto, frontend moderno e práticas de desenvolvimento profissional.

O sistema simula um ambiente real de negócios onde um clube de tênis precisa gerenciar suas quadras, usuários e agendamentos de forma eficiente. Esta simulação permite demonstrar não apenas habilidades técnicas, mas também compreensão de requisitos de negócio, modelagem de dados e experiência do usuário.

### 1.2 Escopo Funcional

O escopo do TennisClub abrange três domínios principais de funcionalidade. O primeiro domínio concentra-se no gerenciamento de usuários, incluindo cadastro, autenticação, autorização e manutenção de perfis. O sistema suporta dois tipos de usuários: administradores, que possuem acesso completo às funcionalidades de gestão, e clientes, que podem realizar agendamentos e gerenciar suas próprias reservas.

O segundo domínio aborda o gerenciamento de quadras, permitindo que administradores cadastrem, atualizem e controlem a disponibilidade das instalações. Cada quadra possui características específicas como nome, descrição, preço por hora e status de atividade, proporcionando flexibilidade na gestão do inventário de recursos.

O terceiro domínio implementa o sistema de agendamentos propriamente dito, com funcionalidades para criação, modificação, cancelamento e consulta de reservas. O sistema inclui validações sofisticadas para evitar conflitos de horário, verificação de disponibilidade em tempo real e cálculo automático de preços baseado na duração e tipo de quadra.

### 1.3 Arquitetura Geral

A arquitetura do TennisClub segue o padrão de aplicações web modernas com separação clara entre frontend e backend. O backend implementa uma API REST completa utilizando Java Spring Boot, seguindo princípios de arquitetura limpa e padrões de design estabelecidos. O frontend é uma Single Page Application (SPA) desenvolvida em React, consumindo a API através de requisições HTTP.

Esta separação arquitetural proporciona diversos benefícios, incluindo escalabilidade independente das camadas, facilidade de manutenção, possibilidade de desenvolvimento paralelo por equipes diferentes e flexibilidade para futuras expansões, como desenvolvimento de aplicações mobile que poderiam consumir a mesma API.

---

## 2. Arquitetura do Backend

### 2.1 Estrutura em Camadas

O backend do TennisClub implementa uma arquitetura em camadas bem definida, seguindo os princípios de separação de responsabilidades e baixo acoplamento. Esta estrutura facilita a manutenção, testabilidade e evolução do sistema.

A camada de apresentação (Controller Layer) é responsável por receber requisições HTTP, validar parâmetros de entrada, delegar processamento para a camada de serviços e formatar respostas. Os controladores são implementados como classes Spring REST Controllers, utilizando anotações para mapeamento de rotas e serialização automática de objetos.

A camada de serviços (Service Layer) contém a lógica de negócios da aplicação. Esta camada é onde residem as regras de validação complexas, cálculos de preços, verificações de disponibilidade e orquestração de operações que envolvem múltiplas entidades. Os serviços são implementados como Spring Services, beneficiando-se da injeção de dependências e gerenciamento transacional.

A camada de persistência (Repository Layer) abstrai o acesso aos dados, utilizando Spring Data JPA para operações CRUD básicas e queries customizadas. Esta camada isola a lógica de negócios dos detalhes de implementação do banco de dados, permitindo mudanças na estratégia de persistência sem impacto nas camadas superiores.

### 2.2 Modelo de Dados

O modelo de dados do TennisClub foi cuidadosamente projetado para representar as entidades do domínio e seus relacionamentos de forma eficiente e normalizada. O design segue princípios de modelagem relacional, evitando redundâncias e garantindo integridade referencial.

A entidade User representa os usuários do sistema, incluindo tanto administradores quanto clientes. Esta entidade contém informações básicas como nome, email, senha criptografada e role (papel no sistema). A senha é armazenada utilizando hash BCrypt, garantindo segurança mesmo em caso de comprometimento da base de dados.

A entidade Court modela as quadras disponíveis para agendamento. Cada quadra possui nome, descrição, preço por hora e status de atividade. O preço é armazenado como BigDecimal para garantir precisão em cálculos monetários, evitando problemas de arredondamento comuns com tipos de ponto flutuante.

A entidade Booking representa os agendamentos realizados pelos usuários. Esta entidade estabelece relacionamentos Many-to-One com User e Court, incluindo informações temporais (data, horário de início e fim), status do agendamento e preço total calculado. O design permite rastreamento completo do ciclo de vida de cada agendamento.

### 2.3 API REST Design

A API REST do TennisClub segue rigorosamente os princípios RESTful, utilizando métodos HTTP apropriados, códigos de status padronizados e estrutura de URLs consistente. A base URL `/api/joaopedroaguiar/tennisclub/v1` incorpora o nome do desenvolvedor conforme solicitado, demonstrando atenção aos requisitos específicos do projeto.

Os endpoints são organizados por recursos (users, courts, bookings), com operações CRUD mapeadas para métodos HTTP apropriados. GET para consultas, POST para criação, PUT para atualizações completas, PATCH para atualizações parciais e DELETE para remoção. Esta consistência facilita o consumo da API e segue convenções amplamente aceitas na indústria.

A API implementa validação robusta de entrada utilizando Bean Validation (JSR-303), com mensagens de erro descritivas e códigos de status HTTP apropriados. Respostas de erro seguem um formato padronizado, facilitando o tratamento no frontend e debugging durante desenvolvimento.

### 2.4 Segurança

A implementação de segurança utiliza Spring Security, configurado para permitir acesso público aos endpoints para fins de demonstração, mas mantendo a estrutura necessária para implementação de autenticação e autorização em ambiente de produção.

O sistema inclui criptografia de senhas utilizando BCrypt, um algoritmo de hash adaptativo que inclui salt automático e é resistente a ataques de força bruta. Esta implementação demonstra consciência sobre boas práticas de segurança em aplicações web.

A configuração CORS está habilitada para permitir requisições do frontend durante desenvolvimento, com configuração flexível que pode ser restringida em ambiente de produção para aceitar apenas origens autorizadas.

---

## 3. Arquitetura do Frontend

### 3.1 Estrutura da Aplicação React

O frontend do TennisClub é implementado como uma Single Page Application utilizando React 18, aproveitando as funcionalidades mais recentes da biblioteca, incluindo Concurrent Features e Automatic Batching. A estrutura da aplicação segue padrões modernos de desenvolvimento React, com componentes funcionais e hooks.

A aplicação é organizada em componentes reutilizáveis, cada um com responsabilidade bem definida. O componente principal App.jsx orquestra a renderização da página, incluindo header, seções de conteúdo e footer. Esta abordagem modular facilita manutenção e permite reutilização de componentes em diferentes contextos.

O gerenciamento de estado é implementado utilizando hooks nativos do React (useState, useEffect), adequados para a complexidade atual da aplicação. Esta escolha demonstra compreensão sobre quando utilizar soluções mais simples versus bibliotecas de gerenciamento de estado mais complexas como Redux.

### 3.2 Design System e UI/UX

O design do TennisClub utiliza Tailwind CSS como framework de estilização, combinado com componentes da biblioteca shadcn/ui para elementos de interface mais complexos. Esta combinação proporciona consistência visual, responsividade e produtividade no desenvolvimento.

A paleta de cores é centrada em tons de verde, evocando a temática esportiva e transmitindo sensação de energia e vitalidade. O design é limpo e moderno, com uso adequado de espaçamento, tipografia e hierarquia visual para guiar a atenção do usuário.

A interface é completamente responsiva, adaptando-se adequadamente a diferentes tamanhos de tela. O design mobile-first garante que a experiência seja otimizada para dispositivos móveis, com degradação elegante para telas maiores.

### 3.3 Componentização

A estratégia de componentização segue princípios de reutilização e separação de responsabilidades. Componentes como Header, HeroSection, FeaturesSection, CourtsSection e Footer são independentes e focados em funcionalidades específicas.

Cada componente encapsula sua própria lógica e estilização, facilitando manutenção e testes. A comunicação entre componentes é feita através de props, mantendo o fluxo de dados unidirecional característico do React.

A utilização de componentes da biblioteca shadcn/ui (Button, Card, Badge) demonstra capacidade de integração com bibliotecas externas e aproveitamento de soluções existentes para acelerar desenvolvimento.

---

## 4. Funcionalidades Implementadas

### 4.1 Gerenciamento de Usuários

O sistema de gerenciamento de usuários implementa um CRUD completo com funcionalidades avançadas de busca e filtragem. A criação de usuários inclui validação de email único, criptografia de senha e atribuição de roles apropriadas.

As operações de consulta suportam diferentes critérios de busca, incluindo busca por nome (com correspondência parcial case-insensitive), filtro por role e status ativo. Estas funcionalidades demonstram compreensão sobre requisitos típicos de sistemas de gestão de usuários.

A atualização de usuários permite modificação de informações básicas mantendo integridade de dados, com validações para evitar duplicação de emails e inconsistências de estado. O sistema também suporta ativação/desativação de usuários sem exclusão física dos dados.

### 4.2 Gerenciamento de Quadras

O módulo de quadras implementa funcionalidades completas para cadastro e manutenção das instalações disponíveis. Cada quadra pode ser configurada com nome, descrição detalhada, preço por hora e status de disponibilidade.

O sistema suporta diferentes tipos de consulta, incluindo listagem de todas as quadras, filtro por quadras ativas, ordenação por preço e busca por nome. A funcionalidade de busca da quadra mais barata demonstra implementação de queries otimizadas.

A flexibilidade no modelo de preços permite que diferentes quadras tenham valores distintos, refletindo variações em qualidade, localização ou recursos disponíveis. Esta abordagem simula cenários reais onde instalações premium comandam preços mais altos.

### 4.3 Sistema de Agendamentos

O sistema de agendamentos representa a funcionalidade mais complexa da aplicação, implementando lógica sofisticada para gerenciamento de reservas com validações rigorosas para evitar conflitos.

A criação de agendamentos inclui verificação de disponibilidade em tempo real, validação de horários de funcionamento (6h às 22h), duração mínima de 1 hora e cálculo automático de preços baseado na duração e valor da quadra. Estas validações garantem integridade dos dados e experiência consistente para os usuários.

O sistema suporta diferentes status de agendamento (PENDING, CONFIRMED, CANCELED, COMPLETED), permitindo workflow completo de gestão de reservas. Administradores podem confirmar ou cancelar agendamentos, enquanto clientes podem visualizar e cancelar suas próprias reservas.

A funcionalidade de verificação de horários disponíveis retorna slots de tempo livres para uma quadra específica em uma data determinada, facilitando a seleção de horários pelos usuários. Esta implementação demonstra capacidade de criar algoritmos eficientes para problemas de scheduling.

---

## 5. Tecnologias e Ferramentas

### 5.1 Stack Tecnológico Backend

O backend utiliza Java 17, aproveitando as funcionalidades mais recentes da linguagem, incluindo records, pattern matching e text blocks. Esta escolha demonstra atualização com versões LTS mais recentes e compreensão sobre evolução da plataforma Java.

Spring Boot 3.2.0 serve como framework principal, proporcionando configuração automática, injeção de dependências e integração com ecossistema Spring. A utilização de Spring Data JPA simplifica operações de persistência, enquanto Spring Security fornece infraestrutura de segurança.

O gerenciamento de dependências utiliza Maven, com configuração clara de dependências e plugins necessários. O arquivo pom.xml está bem estruturado, incluindo dependências para testes, documentação (Swagger) e segurança.

### 5.2 Stack Tecnológico Frontend

O frontend utiliza React 18 com Vite como build tool, proporcionando desenvolvimento rápido com hot module replacement e builds otimizados para produção. Esta combinação representa o estado da arte em desenvolvimento React.

Tailwind CSS fornece sistema de design utilitário, permitindo estilização rápida e consistente. A integração com shadcn/ui adiciona componentes pré-construídos de alta qualidade, acelerando desenvolvimento sem comprometer customização.

Lucide Icons fornece iconografia consistente e moderna, enquanto a configuração do projeto inclui ESLint para qualidade de código e ferramentas de desenvolvimento otimizadas.

### 5.3 Banco de Dados

O sistema suporta tanto MySQL para produção quanto H2 para desenvolvimento, demonstrando flexibilidade na configuração de ambientes. O uso de H2 facilita desenvolvimento e testes, eliminando dependências externas durante desenvolvimento local.

A configuração utiliza Spring Profiles para alternar entre ambientes, com configurações específicas para cada cenário. Esta abordagem segue boas práticas de configuração de aplicações Spring Boot.

O modelo de dados utiliza JPA/Hibernate para mapeamento objeto-relacional, com anotações apropriadas para definição de relacionamentos, validações e estratégias de geração de chaves primárias.

---

## 6. Documentação da API

### 6.1 Swagger/OpenAPI Integration

A documentação da API é gerada automaticamente utilizando Springdoc OpenAPI, proporcionando interface interativa para exploração e teste dos endpoints. A configuração inclui metadados detalhados sobre a API, incluindo informações de contato e licenciamento.

Cada endpoint é documentado com descrições detalhadas, parâmetros de entrada, códigos de resposta possíveis e exemplos de payload. Esta documentação serve tanto para desenvolvimento quanto para consumidores da API.

A interface Swagger UI está disponível em `/swagger-ui.html`, proporcionando ambiente interativo para teste da API sem necessidade de ferramentas externas. Esta funcionalidade acelera desenvolvimento e facilita debugging.

### 6.2 Estrutura de Endpoints

Os endpoints seguem convenções RESTful consistentes, com URLs descritivas e métodos HTTP apropriados. A estrutura hierárquica facilita navegação e compreensão da API.

Endpoints de usuários (`/users`) incluem operações CRUD completas, busca por critérios específicos e operações de ativação/desativação. A implementação demonstra compreensão sobre design de APIs para gerenciamento de recursos.

Endpoints de quadras (`/courts`) proporcionam funcionalidades similares, com adições específicas como busca por faixa de preço e identificação da quadra mais barata. Estas funcionalidades mostram capacidade de implementar requisitos de negócio específicos.

Endpoints de agendamentos (`/bookings`) implementam lógica mais complexa, incluindo verificação de disponibilidade, operações de confirmação/cancelamento e consultas por diferentes critérios. A complexidade destes endpoints demonstra capacidade de implementar lógica de negócio sofisticada.

---

## 7. Considerações de Qualidade

### 7.1 Tratamento de Erros

O sistema implementa tratamento robusto de erros com mensagens descritivas e códigos de status HTTP apropriados. Exceções são capturadas e transformadas em respostas estruturadas que facilitam debugging e tratamento no frontend.

Validações de entrada utilizam Bean Validation com mensagens customizadas, proporcionando feedback claro sobre problemas nos dados fornecidos. Esta abordagem melhora experiência do usuário e facilita integração.

Erros de negócio (como conflitos de agendamento) são tratados com exceções específicas e mensagens explicativas, permitindo que o frontend apresente informações úteis aos usuários.

### 7.2 Performance e Escalabilidade

O design da aplicação considera aspectos de performance através de queries otimizadas, uso apropriado de relacionamentos JPA e estrutura de dados eficiente. A separação entre frontend e backend permite escalabilidade independente das camadas.

A utilização de Spring Boot proporcionará facilidade de deployment em diferentes ambientes, incluindo containerização com Docker e deployment em plataformas cloud. Esta flexibilidade é importante para escalabilidade futura.

O modelo de dados normalizado evita redundâncias e garante consistência, enquanto índices apropriados (implícitos através de chaves primárias e estrangeiras) otimizam consultas frequentes.

### 7.3 Manutenibilidade

A estrutura modular do código facilita manutenção e evolução. A separação clara de responsabilidades entre camadas permite modificações isoladas sem impacto em outras partes do sistema.

O uso de padrões estabelecidos (Spring Boot, React) facilita onboarding de novos desenvolvedores e reduz curva de aprendizado. A documentação abrangente complementa a manutenibilidade do código.

Convenções de nomenclatura consistentes e estrutura de projeto padronizada facilitam navegação no código e compreensão da arquitetura por desenvolvedores externos.

---

## 8. Conclusão

O TennisClub representa uma implementação completa e profissional de um sistema de agendamento, demonstrando competências técnicas abrangentes em desenvolvimento full-stack. O projeto showcases não apenas habilidades de programação, mas também compreensão de arquitetura de software, design de APIs, experiência do usuário e boas práticas de desenvolvimento.

A escolha tecnológica reflete conhecimento atual do mercado, utilizando ferramentas e frameworks modernos que são amplamente adotados na indústria. A implementação demonstra capacidade de criar soluções completas que atendem requisitos funcionais e não-funcionais.

O sistema está preparado para evolução futura, com arquitetura flexível que suporta adição de novas funcionalidades, integração com sistemas externos e deployment em diferentes ambientes. Esta preparação para crescimento demonstra visão estratégica e compreensão sobre ciclo de vida de software.

O projeto TennisClub serve efetivamente como demonstração de capacidades técnicas, proporcionando evidência concreta de habilidades em desenvolvimento de software e compreensão sobre requisitos de sistemas empresariais modernos.

---

**Desenvolvido por João Pedro Aguiar**  
**GitHub:** https://github.com/Joaopedromartins21  
**Projeto de Portfólio - 2024**

