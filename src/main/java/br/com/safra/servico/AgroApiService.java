package br.com.safra.servico;

import br.com.safra.dto.CicloColheitaDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

/**
 * Serviço responsável por consumir a API REST da AgroAPI (Embrapa)
 * para buscar o ciclo de colheita de uma cultura.
 *
 * <p>O token de acesso é lido via variável de ambiente AGROAPI_TOKEN.
 * Se o token não estiver configurado ou a API falhar, retorna Optional.empty()
 * para que o fluxo do CLI faça fallback para digitação manual.</p>
 */
public class AgroApiService {

  private static final String BASE_URL =
      "https://api.cnptia.embrapa.br/agritec/v1/culturas";
  private static final int TIMEOUT_SECONDS = 10;

  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;
  private final String token;

  /**
   * Construtor padrão para uso em produção.
   * Lê o token da variável de ambiente AGROAPI_TOKEN.
   */
  public AgroApiService() {
    this.httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
        .build();
    this.objectMapper = new ObjectMapper();
    this.token = System.getenv("AGROAPI_TOKEN");
  }

  /**
   * Construtor para injeção de dependências (usado nos testes).
   *
   * @param httpClient cliente HTTP customizado
   * @param token      token de autenticação
   */
  public AgroApiService(HttpClient httpClient, String token) {
    this.httpClient = httpClient;
    this.objectMapper = new ObjectMapper();
    this.token = token;
  }

  /**
   * Busca o ciclo de colheita (em dias) para a cultura informada.
   *
   * @param cultura nome da cultura (ex: "Milho", "Feijão")
   * @return Optional com o número de dias, ou empty se falhar
   */
  public Optional<Integer> buscarCicloColheita(String cultura) {
    if (token == null || token.isBlank()) {
      System.out.println("⚠️  Token da API não configurado "
          + "(defina a variável de ambiente AGROAPI_TOKEN).");
      return Optional.empty();
    }

    try {
      String culturaCodificada = URLEncoder.encode(
          cultura.toLowerCase().trim(), StandardCharsets.UTF_8);
      String url = BASE_URL + "?nome=" + culturaCodificada;

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .header("Authorization", "Bearer " + token)
          .header("Accept", "application/json")
          .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
          .GET()
          .build();

      HttpResponse<String> response = httpClient.send(
          request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        CicloColheitaDto dto = objectMapper.readValue(
            response.body(), CicloColheitaDto.class);
        return Optional.of(dto.diasParaColheita());
      } else {
        System.out.println("⚠️  API retornou status "
            + response.statusCode() + ". Usando fallback manual.");
        return Optional.empty();
      }

    } catch (Exception e) {
      System.out.println("⚠️  Erro ao consultar API: "
          + e.getMessage() + ". Usando fallback manual.");
      return Optional.empty();
    }
  }
}
