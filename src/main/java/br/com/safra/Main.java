package br.com.safra;

import br.com.safra.modelo.Plantio;
import br.com.safra.servico.GerenciadorSafra;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorSafra gerenciador = new GerenciadorSafra();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("🌱 === Sistema de Controle de Safra Familiar === 🌱");

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Registrar novo plantio");
            System.out.println("2 - Listar plantios e previsões de colheita");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");

            String opcao = scanner.nextLine();

            if (opcao.equals("1")) {
                System.out.print("Qual é a cultura (Ex: Milho, Feijão, Alface)? ");
                String cultura = scanner.nextLine();

                System.out.print("Data do plantio (dd/MM/yyyy) [Deixe em branco para usar a data de hoje]: ");
                String dataInput = scanner.nextLine();
                LocalDate dataPlantio = dataInput.isBlank() ? LocalDate.now() : LocalDate.parse(dataInput, formatter);

                System.out.print("Quantos dias, em média, até a colheita? ");
                int dias = Integer.parseInt(scanner.nextLine());

                Plantio plantio = new Plantio(cultura, dataPlantio, dias);
                gerenciador.registrarPlantio(plantio);

                System.out.println("✅ Plantio registrado com sucesso!");

            } else if (opcao.equals("2")) {
                System.out.println("\n--- Plantios Registrados ---");
                if (gerenciador.listarTodos().isEmpty()) {
                    System.out.println("Nenhum plantio registrado ainda.");
                } else {
                    for (Plantio p : gerenciador.listarTodos()) {
                        System.out.println(p);
                    }
                }
            } else if (opcao.equals("3")) {
                System.out.println("Encerrando o sistema... Boa safra!");
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }
}