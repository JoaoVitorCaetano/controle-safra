# 🌱 Sistema de Controle de Safra Familiar

![CI Pipeline](https://github.com/JoaoVitorCaetano/controle-safra/actions/workflows/ci.yml/badge.svg)

## 🚀 Deploy e Execução

### Executar via Fat Jar (Recomendado)
Baixe o arquivo `.jar` na seção [Releases](https://github.com/JoaoVitorCaetano/controle-safra/releases) ou gere localmente:
```bash
mvn clean package
java -jar target/controle-safra-1.0.0.jar
```

### Variável de Ambiente (Opcional)
Para habilitar a busca automática do ciclo de colheita via API da Embrapa, configure o token:
```bash
# Linux/Mac
export AGROAPI_TOKEN="seu-token-aqui"

# Windows (PowerShell)
$env:AGROAPI_TOKEN="seu-token-aqui"
```
> **Nota:** Se o token não estiver configurado, o sistema funcionará normalmente com digitação manual do ciclo de colheita.

---

## 🎯 Descrição do Problema Real
Pequenos agricultores familiares e produtores rurais enfrentam dificuldades constantes para gerir o calendário de produção. O controle das datas de plantio e a previsão exata de colheita costumam ser feitos de forma informal ou manual, o que gera desperdícios e falhas no planejamento logístico da colheita.

## 💡 Proposta da Solução
A aplicação oferece uma interface de linha de comando (CLI) simples e eficiente para o registro de plantios. O sistema calcula automaticamente a data prevista de colheita com base no ciclo de cada cultura, permitindo que o produtor tenha uma visão clara do seu cronograma produtivo diretamente no terminal.

### 🌐 Integração com API
O sistema consulta automaticamente a **AgroAPI (Embrapa)** para obter o ciclo de colheita de cada cultura. Caso a API não esteja disponível ou o token não esteja configurado, o sistema faz **fallback** para a digitação manual, garantindo que a aplicação funcione em qualquer cenário.

### 👥 Público-alvo
* Pequenos produtores agrícolas.
* Hortas comunitárias ou urbanas.
* Estudantes de agronomia e técnicos agrícolas.

## 🚀 Funcionalidades Principais
* **Registro de Plantio:** Inserção da cultura, data de início e tempo estimado de ciclo.
* **Busca Automática via API:** Consulta à AgroAPI da Embrapa para obter o ciclo de colheita automaticamente.
* **Cálculo Automático:** Determinação da data de colheita baseada na lógica de negócio.
* **Listagem de Safra:** Visualização organizada de todos os plantios ativos e suas previsões.

## 🛠️ Tecnologias Utilizadas
* **Java 21**: Linguagem principal.
* **Maven**: Gestão de dependências e build (`pom.xml`).
* **Jackson Databind**: Parser JSON para integração com API REST.
* **JUnit 5**: Framework de testes automatizados.
* **Mockito**: Mocking para testes de integração.
* **Checkstyle**: Análise estática de código (Linting).
* **GitHub Actions**: Integração Contínua (CI).

## 📦 Instalação e Execução

### Pré-requisitos
* Java JDK 21 instalado.
* Maven instalado e configurado no PATH.

### Instruções
1. Clone o repositório:
   ```bash
   git clone https://github.com/JoaoVitorCaetano/controle-safra.git
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd controle-safra
   ```
3. Compile e execute a aplicação:
   ```bash
   mvn compile exec:java -Dexec.mainClass="br.com.safra.Main"
   ```

## ✅ Testes e Qualidade

### Executar Testes Automatizados
O projeto inclui testes de unidade e de integração (com mocks) para validar a lógica de cálculo e o serviço de API:
```bash
mvn test
```

### Executar Verificação Completa (Testes + Checkstyle)
```bash
mvn clean verify
```

### Executar Linting (Análise Estática)
Para verificar a padronização do código conforme as boas práticas:
```bash
mvn checkstyle:check
```

## 🏷️ Versão Atual
**1.0.0** (SemVer)

## 👤 Autor
João Vitor