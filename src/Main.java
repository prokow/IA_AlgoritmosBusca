import algoritmos.buscaAEstrela;
import algoritmos.buscaProfundidade;
import model.Grafo;
import util.ReadGrafo;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    //Descrição: O método principal que inicia o programa.
    //Pré-condição: Nenhuma.
    //Pós-condição: O programa é executado, interagindo com o usuário até que a opção de sair seja escolhida.
    //Entrada: String[] args - Argumentos de linha de comando (não utilizados).
    //Saída: Nenhuma.
    public static void main(String[] args) {
        System.out.println("-*-*-*-*-*-*-*-*-*- Projeto 2 - Algortimos de Busca -*-*-*-*-*-*-*-*-*-");
        System.out.println("- Alunos: Anny Karoline Dellani & Matheus Prokopowiski dos Santos -");

        // --- Carrega o Grafo a ser usado ---
        Grafo grafo = null;
        ReadGrafo leitor = new ReadGrafo();
        Scanner scanner = new Scanner(System.in);

        // Pede ao usuário para fornecer o caminho do arquivo de teste.
        System.out.print("\nDigite o caminho do arquivo de teste para iniciar a execução (ex: teste.txt): ");
        String caminhoArquivo = scanner.next(); // Lê a string digitada pelo usuário

        // Tenta ler e construir o grafo. Se falhar, encerra o programa com uma mensagem de erro.
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
        // O laço while(true) mantém o menu ativo até que o usuário escolha sair.
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

            // O switch direciona o programa com base na escolha do usuário.
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
                    System.out.println("Opção inválida! Escolha uma das opções do menu..");
                    break;
            }
        }
    }
}