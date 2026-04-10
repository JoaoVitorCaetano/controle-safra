# 🌱 Sistema de Controle de Safra Familiar

![CI Pipeline](https://github.com/JoaoVitorCaetano/controle-safra/actions/workflows/ci.yml/badge.svg)

## 🎯 Descrição do Problema Real
Pequenos agricultores familiares e produtores rurais enfrentam dificuldades constantes para gerir o calendário de produção. O controle das datas de plantio e a previsão exata de colheita costumam ser feitos de forma informal ou manual, o que gera desperdícios e falhas no planejamento logístico da colheita.

## 💡 Proposta da Solução
A aplicação oferece uma interface de linha de comando (CLI) simples e eficiente para o registro de plantios. O sistema calcula automaticamente a data prevista de colheita com base no ciclo de cada cultura, permitindo que o produtor tenha uma visão clara do seu cronograma produtivo diretamente no terminal.

### 👥 Público-alvo
* Pequenos produtores agrícolas.
* Hortas comunitárias ou urbanas.
* Estudantes de agronomia e técnicos agrícolas.

## 🚀 Funcionalidades Principais
* **Registro de Plantio:** Inserção da cultura, data de início e tempo estimado de ciclo.
* **Cálculo Automático:** Determinação da data de colheita baseada na lógica de negócio.
* **Listagem de Safra:** Visualização organizada de todos os plantios ativos e suas previsões.

## 🛠️ Tecnologias Utilizadas
* **Java 21**: Linguagem principal.
* **Maven**: Gestão de dependências e build (`pom.xml`).
* **JUnit 5**: Framework de testes automatizados.
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
O projeto inclui testes de unidade para validar a lógica de cálculo e o comportamento do gerenciador de safra:
```bash
mvn test
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