package util;

import model.Grafo;
import model.Vertice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadGrafo {

    //Lê um arquivo linha por linha e coordena o processo de parsing.
    //Pré-condição: 'nomeArq' é um caminho válido para um arquivo existente.
    //Pós-condição: Retorna um objeto 'Grafo' populado com os dados do arquivo.
    //Entrada: String 'nomeArq' - caminho do arquivo a ser lido.
    //Saída: Um objeto Grafo.
    public Grafo readArquivo(String nomeArq) throws IOException {
        Grafo g = new Grafo();
        List<String> linhas = Files.readAllLines(Paths.get(nomeArq));

        for(String linha : linhas){
            processaLinha(linha, g);
        }
        return g;
    }


    //Processa uma única linha do arquivo, identifica seu tipo e atualiza o grafo.
    //Pré-condição: 'g' é um objeto Grafo válido.
    //Pós-condição: O objeto 'g' é modificado com a informação contida na 'linha'.
    //Entrada: Linha de texto a ser processada pela 'linha', e o grafo 'g' a ser modificado.
    //Saída: Nenhuma.
    private void processaLinha(String linha, Grafo g) {
        linha = linha.trim();

        if(linha.isEmpty() || linha.startsWith("%")){
            return;
        }

        if(linha.endsWith(".")){
            linha = linha.substring(0, linha.length()-1);
        }

        try{
            if(linha.startsWith("ponto_inicial")){
                String nomeVertice = extrairConteudo(linha);
                g.setPontoInicial(nomeVertice);
            } else if (linha.startsWith("ponto_final")){
                String nomeVertice = extrairConteudo(linha);
                g.setPontoFinal(nomeVertice);
            } else if (linha.startsWith("orientado")){
                String valor = extrairConteudo(linha);
                g.setOrientado(valor.equalsIgnoreCase("s"));
            } else if (linha.startsWith("pode_ir")){
                String conteudo = extrairConteudo(linha);
                String[] partes = conteudo.split(",");
                String origem = partes[0].trim();
                String destino = partes[1].trim();
                int custo = Integer.parseInt(partes[2].trim());
                g.addAresta(origem, destino, custo);
            } else if(linha.startsWith("h")){
                String conteudo = extrairConteudo(linha);
                String[] partes = conteudo.split(",");
                String nomeVertice = partes[0].trim();
                int valorHeuristica = Integer.parseInt(partes[2].trim());

                Vertice v = g.getVertice(nomeVertice);
                if (v == null){
                    v = g.addVertice(nomeVertice);
                }v.setHeuristica(valorHeuristica);
            }
        } catch (Exception e){
            System.err.println("Nao foi possivel processar a linha: '" + linha + "' Erro: " + e.getMessage());
        }
    }


    //Função auxiliar que extrai o texto de dentro de parênteses.
    //Pré-condição: A linha contém os caracteres '(' e ')'.
    //Pós-condição: Retorna a substring entre os parênteses.
    //Entrada: String 'linha' - A linha completa.
    //Saída: A string extraída.
    private String extrairConteudo(String linha){
        int inicio = linha.indexOf('(');
        int fim = linha.lastIndexOf(')');
        return linha.substring(inicio+1, fim);
    }
}
