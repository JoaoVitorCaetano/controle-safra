package br.com.safra.modelo;

import java.time.LocalDate;

public class Plantio {
    private String cultura;
    private LocalDate dataPlantio;
    private int diasParaColheita;

    public Plantio(String cultura, LocalDate dataPlantio, int diasParaColheita) {
        this.cultura = cultura;
        this.dataPlantio = dataPlantio;
        this.diasParaColheita = diasParaColheita;
    }

    // Regra de negócio simples e testável:
    public LocalDate calcularDataPrevistaColheita() {
        return this.dataPlantio.plusDays(this.diasParaColheita);
    }

    public String getCultura() {
        return cultura;
    }

    public LocalDate getDataPlantio() {
        return dataPlantio;
    }

    @Override
    public String toString() {
        return String.format("Cultura: %s | Plantio: %s | Previsão de Colheita: %s",
                cultura, dataPlantio, calcularDataPrevistaColheita());
    }
}