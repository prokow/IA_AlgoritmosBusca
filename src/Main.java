import algoritmos.buscaAEstrela;
import algoritmos.buscaProfundidade;
import model.Grafo;
import util.ReadGrafo;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Projeto 2 - Algortimos de Busca ---");
        System.out.println("--- Aluno: Matheus Prokopowiski ---");

        // --- Carrega o Grafo a ser usado ---
        Grafo grafo = null;
        ReadGrafo leitor = new ReadGrafo();
        Scanner scanner = new Scanner(System.in);

        // --- Arquivo texto para o labirinto ---
        System.out.print("\nDigite o caminho do arquivo de teste para iniciar a execução (ex: teste.txt): ");
        String caminhoArquivo = scanner.next(); // Lê a string digitada pelo usuário

        try {
            grafo = leitor.readArquivo(caminhoArquivo);
            System.out.println("... Grafo do labirinto carregado com sucesso!\n");
        } catch (IOException e) {
            System.err.println("Erro: Não foi possível ler o arquivo do labirinto em '" + caminhoArquivo + "'.");
            System.err.println("Verifique se o arquivo existe e se o caminho está correto.");
            e.printStackTrace();
            scanner.close(); // Fecha o scanner antes de sair
            System.exit(1);
        }
        
        // --- Menu ---
        while (true) {
            System.out.println("==============================================");
            System.out.println("Escolha o algoritmo para Teseu usar:");
            System.out.println("1. Melhor Solução (Busca A*)");
            System.out.println("2. Pior Solução (Busca em Profundidade)");
            System.out.println("0. Sair do programa");
            System.out.print("Digite sua opção: ");

            int escolha;
            try {
                escolha = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); // Limpa o buffer do scanner
                continue;
            }

            System.out.println("==============================================");

            // --- Algoritmo a ser escolhido ---
            switch (escolha) {
                case 1:
                    buscaAEstrela aEstrela = new buscaAEstrela(grafo);
                    aEstrela.buscar();
                    break;
                case 2:
                    buscaProfundidade dfs = new buscaProfundidade(grafo);
                    dfs.busca();
                    break;
                case 0:
                    System.out.println("Teseu cansou de fazer a busca e esta indo embora..");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma das opções do menu.");
                    break;
            }
        }
    }

    /*public static void main(String[] args) {
        ReadGrafo leitor = new ReadGrafo();
        Grafo g = null;

        String nomeArquivo = "src/teste.txt";
        try{
            g = leitor.readArquivo(nomeArquivo);
            System.out.println("Grafo lido com sucesso!");
            System.out.println(g);

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: "+ e.getMessage());
        }
    }*/
}