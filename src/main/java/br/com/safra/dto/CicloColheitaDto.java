package br.com.safra.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO que mapeia a resposta da API de ciclo de colheita.
 * Utiliza record do Java 16+ para imutabilidade e concisão.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CicloColheitaDto(String cultura, int diasParaColheita) {
}
