package br.com.safra.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Testes de integração para o AgroApiService.
 * Utiliza Mockito para simular as respostas HTTP, evitando
 * requisições reais à API (não consome cota nem quebra CI/CD).
 */
@ExtendWith(MockitoExtension.class)
class AgroApiServiceTest {

  @Mock
  private HttpClient httpClient;

  @Mock
  private HttpResponse<String> httpResponse;

  private AgroApiService apiService;

  @BeforeEach
  void setUp() {
    apiService = new AgroApiService(httpClient, "token-de-teste");
  }

  @Test
  @DisplayName("Deve retornar o ciclo de colheita quando a API retorna HTTP 200")
  @SuppressWarnings("unchecked")
  void deveRetornarCicloQuandoApiRetorna200() throws Exception {
    // Arrange - simula resposta JSON da API
    String jsonResposta = """
        {
          "cultura": "milho",
          "diasParaColheita": 90,
          "outrosCamposIgnorados": "valor"
        }
        """;

    when(httpResponse.statusCode()).thenReturn(200);
    when(httpResponse.body()).thenReturn(jsonResposta);
    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenReturn(httpResponse);

    // Act
    Optional<Integer> resultado = apiService.buscarCicloColheita("Milho");

    // Assert
    assertTrue(resultado.isPresent(), "Deveria retornar um valor presente");
    assertEquals(90, resultado.get(), "O ciclo do milho deve ser 90 dias");
  }

  @Test
  @DisplayName("Deve retornar empty quando a API retorna erro HTTP 500")
  @SuppressWarnings("unchecked")
  void deveRetornarEmptyQuandoApiFalha() throws Exception {
    // Arrange - simula erro do servidor
    when(httpResponse.statusCode()).thenReturn(500);
    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenReturn(httpResponse);

    // Act
    Optional<Integer> resultado = apiService.buscarCicloColheita("Milho");

    // Assert
    assertTrue(resultado.isEmpty(),
        "Deveria retornar vazio quando a API falha");
  }

  @Test
  @DisplayName("Deve retornar empty quando ocorre exceção na comunicação")
  @SuppressWarnings("unchecked")
  void deveRetornarEmptyQuandoOcorreExcecao() throws Exception {
    // Arrange - simula falha de rede
    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(new RuntimeException("Connection timeout"));

    // Act
    Optional<Integer> resultado = apiService.buscarCicloColheita("Milho");

    // Assert
    assertTrue(resultado.isEmpty(),
        "Deveria retornar vazio quando há exceção de rede");
  }

  @Test
  @DisplayName("Deve retornar empty quando o token não está configurado")
  void deveRetornarEmptyQuandoTokenNulo() {
    // Arrange - serviço sem token
    AgroApiService serviceSemToken = new AgroApiService(httpClient, null);

    // Act
    Optional<Integer> resultado = serviceSemToken.buscarCicloColheita("Milho");

    // Assert
    assertTrue(resultado.isEmpty(),
        "Deveria retornar vazio quando o token é nulo");
  }

  @Test
  @DisplayName("Deve retornar empty quando o token está em branco")
  void deveRetornarEmptyQuandoTokenEmBranco() {
    // Arrange - serviço com token vazio
    AgroApiService serviceTokenVazio = new AgroApiService(httpClient, "  ");

    // Act
    Optional<Integer> resultado = serviceTokenVazio.buscarCicloColheita("Feijão");

    // Assert
    assertTrue(resultado.isEmpty(),
        "Deveria retornar vazio quando o token está em branco");
  }

  @Test
  @DisplayName("Deve retornar ciclo correto para cultura com acentos")
  @SuppressWarnings("unchecked")
  void deveRetornarCicloParaCulturaComAcentos() throws Exception {
    // Arrange
    String jsonResposta = """
        {
          "cultura": "feijão",
          "diasParaColheita": 70
        }
        """;

    when(httpResponse.statusCode()).thenReturn(200);
    when(httpResponse.body()).thenReturn(jsonResposta);
    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenReturn(httpResponse);

    // Act
    Optional<Integer> resultado = apiService.buscarCicloColheita("Feijão");

    // Assert
    assertTrue(resultado.isPresent());
    assertEquals(70, resultado.get(),
        "O ciclo do feijão deve ser 70 dias");
  }
}
