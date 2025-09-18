# 🎉 EventosTec Backend

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql)
![AWS](https://img.shields.io/badge/AWS-S3-orange?style=for-the-badge&logo=amazon-aws)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue?style=for-the-badge&logo=docker)

Uma API REST robusta para gerenciamento de eventos de tecnologia, construída com Spring Boot e arquitetura hexagonal.

</div>

## 📋 Sobre o Projeto

O **EventosTec Backend** é uma API completa para gerenciamento de eventos tecnológicos, oferecendo funcionalidades para criar, listar, filtrar e gerenciar eventos, além de um sistema de cupons integrado. O projeto utiliza as melhores práticas de desenvolvimento com Clean Architecture e está pronto para produção com Docker e AWS.

### ✨ Principais Funcionalidades

- 📅 **Gerenciamento Completo de Eventos**
  - Criação de eventos com upload de imagens
  - Listagem paginada de eventos
  - Filtros por localização e data
  - Busca por título
  - Exclusão com autenticação admin

- 🎫 **Sistema de Cupons**
  - Criação de cupons para eventos
  - Associação de cupons a eventos específicos

- 📍 **Gerenciamento de Localização**
  - Suporte a eventos presenciais e remotos
  - Filtros por cidade e estado

- 🖼️ **Upload de Imagens**
  - Integração com AWS S3
  - Upload seguro de imagens para eventos

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.3.4** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Validation** - Validação de dados
- **PostgreSQL** - Banco de dados principal
- **Flyway** - Migração de banco de dados

### Cloud & Storage
- **AWS S3** - Armazenamento de imagens
- **PostgreSQL RDS** - Banco de dados em produção

### DevOps & Tools
- **Docker** - Containerização
- **Maven** - Gerenciamento de dependências
- **Lombok** - Redução de boilerplate
- **MapStruct** - Mapeamento de objetos
- **JaCoCo** - Cobertura de testes

### Testes
- **JUnit 5** - Framework de testes
- **Mockito** - Mocking para testes
- **H2** - Banco de dados para testes

## 🏗️ Arquitetura

O projeto segue os princípios da **Clean Architecture** (Arquitetura Hexagonal):

```
src/main/java/com/eventostec/api/
├── adapters/
│   ├── inbound/controller/     # Controllers REST
│   └── outbound/
│       ├── entities/           # Entidades JPA
│       ├── repositories/       # Implementações de repositórios
│       └── storage/           # Adaptadores de armazenamento
├── application/
│   ├── service/               # Serviços de aplicação
│   └── usecases/             # Casos de uso
├── domain/                   # Regras de negócio
│   ├── event/               # Domínio de eventos
│   ├── coupon/              # Domínio de cupons
│   └── address/             # Domínio de endereços
├── infrastructure/          # Configurações
└── utils/                   # Utilitários
```

## 🚀 Como Executar

### Pré-requisitos
- Java 21
- Maven 3.9+
- Docker & Docker Compose
- PostgreSQL (para desenvolvimento local)



### 💻 Executando Localmente

1. **Configure o banco PostgreSQL**
```bash
# Execute o PostgreSQL via Docker
docker run --name postgres-eventostec -e POSTGRES_DB=eventostec -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres
```

2. **Configure as variáveis de ambiente**
```bash
export DB_URL=jdbc:postgresql://localhost:5432/eventostec
export DB_USER=admin
export DB_PASSWORD=admin
export ADMIN_KEY=sua_chave_admin
export AWS_REGION=us-east-1
export AWS_BUCKET_NAME=seu_bucket_s3
```

3. **Execute a aplicação**
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8090`

## 📚 Documentação da API

### Eventos

#### Criar Evento
```http
POST /api/event
Content-Type: multipart/form-data

{
  "title": "Nome do Evento",
  "description": "Descrição do evento",
  "date": "2024-12-31T23:59:59",
  "city": "São Paulo",
  "state": "SP",
  "remote": false,
  "eventUrl": "https://evento.com",
  "image": [arquivo]
}
```

#### Listar Eventos
```http
GET /api/event?page=0&size=10
```

#### Buscar Eventos
```http
GET /api/event/search?title=spring
```

#### Filtrar Eventos
```http
GET /api/event/filter?city=São Paulo&uf=SP&startDate=2024-01-01&endDate=2024-12-31&page=0&size=10
```

#### Detalhes do Evento
```http
GET /api/event/{eventId}
```

#### Deletar Evento
```http
DELETE /api/event/{eventId}
Content-Type: application/json

"sua_admin_key"
```

### Cupons

#### Criar Cupom
```http
POST /api/coupon/event/{eventId}
Content-Type: application/json

{
  "code": "DESCONTO10",
  "discount": 10,
  "valid": 100
}
```

## 🚀 Deploy em Produção

### 1. Build da Imagem Docker
```bash
docker build --platform linux/amd64 -t backend-eventostec:3.0 .
```

### 2. Push para Docker Hub
```bash
docker tag backend-eventostec:3.0 kipperdev/backend-eventostec:3.0
docker push kipperdev/backend-eventostec:3.0
```

### 3. Deploy na AWS EC2
```bash
# Conecte à instância EC2
ssh ec2-user@44.212.51.2

# Baixe e execute a nova versão
docker pull kipperdev/backend-eventostec:3.0
docker run -d -p 80:80 kipperdev/backend-eventostec:3.0
```

> ⚠️ **Importante**: Certifique-se de mapear para a porta 80, pois é a porta que o Load Balancer está acessando.

## 🧪 Executando Testes

### Testes Unitários
```bash
# Executar todos os testes
./mvnw test

# Executar testes com relatório de cobertura
./mvnw verify

# Ver relatório de cobertura
open target/site/jacoco/index.html
```

### Testando a API Manualmente

#### 1. Iniciar a Aplicação
```bash
# Compilar e executar
./mvnw spring-boot:run
```

#### 2. Testar Endpoints com cURL

**Criar um evento:**
```bash
curl -X POST http://localhost:8090/api/event \
  -F "title=Workshop Spring Boot" \
  -F "description=Aprenda Spring Boot na prática" \
  -F "date=2024-12-31T10:00:00" \
  -F "city=São Paulo" \
  -F "state=SP" \
  -F "remote=false" \
  -F "eventUrl=https://workshop.com" \
  -F "image=@caminho/para/imagem.jpg"
```

**Listar eventos:**
```bash
curl http://localhost:8090/api/event?page=0&size=10
```

**Buscar eventos por título:**
```bash
curl "http://localhost:8090/api/event/search?title=Spring"
```

**Obter detalhes de um evento:**
```bash
curl http://localhost:8090/api/event/{EVENT_ID}
```

**Criar cupom para evento:**
```bash
curl -X POST http://localhost:8090/api/coupon/event/{EVENT_ID} \
  -H "Content-Type: application/json" \
  -d '{
    "code": "DESCONTO20",
    "discount": 20,
    "valid": 30
  }'
```

#### 3. Testar com Swagger UI
Acesse: `http://localhost:8090/swagger-ui.html`

#### 4. Verificar Saúde da Aplicação
```bash
# Verificar se a aplicação está rodando
curl http://localhost:8090/actuator/health
```

## 🔧 Configuração

### Correções Recentes

#### Problema de Tipos UUID Resolvido
Corrigimos incompatibilidades de tipos entre `UUID` e `Long` nos repositórios:
- `EventRepositoryImpl`: Removida conversão desnecessária de UUID para Long
- `CouponService`: Corrigido uso direto de UUID nos métodos de busca

#### Implementação do ImageUploaderPort
Criamos a implementação `S3ImageUploader` para resolver o erro de inicialização:
- Integração completa com AWS S3
- Upload seguro de imagens com nomes únicos
- Configuração via properties do Spring

### Variáveis de Ambiente Necessárias

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `DB_URL` | URL do banco PostgreSQL | `jdbc:postgresql://localhost:5432/eventostec` |
| `DB_USER` | Usuário do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `sua_senha` |
| `ADMIN_KEY` | Chave para operações administrativas | `sua_chave_secreta` |
| `AWS_BUCKET_NAME` | Nome do bucket S3 | `eventostec-imagens` |

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Autores

- **Seu Nome** - *Desenvolvimento inicial* - [seu-github](https://github.com/seu-usuario)

## 🙏 Agradecimentos

- Spring Boot team pela excelente documentação
- Comunidade Java pelo suporte
- AWS pela infraestrutura confiável

---

<div align="center">
  Feito com ❤️ e ☕ por <a href="https://github.com/seu-usuario">Seu Nome</a>
</div>