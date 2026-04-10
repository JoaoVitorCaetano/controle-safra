package br.com.safra.servico;

import br.com.safra.modelo.Plantio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GerenciadorSafraTest {

    private GerenciadorSafra gerenciador;

    // Esse método roda antes de cada teste, garantindo um ambiente limpo
    @BeforeEach
    void setUp() {
        gerenciador = new GerenciadorSafra();
    }

    // 1. Cenário de uso correto ("caminho feliz")
    @Test
    void deveRegistrarPlantioComSucesso() {
        Plantio plantio = new Plantio("Milho", LocalDate.now(), 90);
        gerenciador.registrarPlantio(plantio);

        assertEquals(1, gerenciador.listarTodos().size());
        assertTrue(gerenciador.verificarSeExiste("Milho"));
    }

    // 2. Entrada inválida ou comportamento indevido
    @Test
    void naoDevePermitirPlantioSemCultura() {
        Plantio plantioInvalido = new Plantio("", LocalDate.now(), 60);

        // Espera-se que o sistema lance uma exceção ao tentar registrar
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.registrarPlantio(plantioInvalido);
        });

        assertEquals("Dados do plantio inválidos.", exception.getMessage());
    }

    // 3. Caso limite ou variação importante (Cálculo de datas)
    @Test
    void deveCalcularDataDeColheitaCorretamente() {
        // Se plantou em 10/04/2026 e leva 30 dias, a colheita deve ser 10/05/2026
        LocalDate dataPlantio = LocalDate.of(2026, 4, 10);
        Plantio alface = new Plantio("Alface", dataPlantio, 30);

        LocalDate dataEsperada = LocalDate.of(2026, 5, 10);

        assertEquals(dataEsperada, alface.calcularDataPrevistaColheita());
    }
}