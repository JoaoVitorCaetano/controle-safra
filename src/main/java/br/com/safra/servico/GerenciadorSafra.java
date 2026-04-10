package br.com.safra.servico;

import br.com.safra.modelo.Plantio;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorSafra {
    private List<Plantio> plantios;

    public GerenciadorSafra() {
        this.plantios = new ArrayList<>();
    }

    public void registrarPlantio(Plantio plantio) {
        if (plantio == null || plantio.getCultura().isBlank()) {
            throw new IllegalArgumentException("Dados do plantio inválidos.");
        }
        plantios.add(plantio);
    }

    public List<Plantio> listarTodos() {
        return plantios;
    }

    // Método que será excelente para cobrir com testes automatizados depois
    public boolean verificarSeExiste(String cultura) {
        return plantios.stream()
                .anyMatch(p -> p.getCultura().equalsIgnoreCase(cultura));
    }
}