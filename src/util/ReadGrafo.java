package util;

import model.Grafo;
import model.Vertice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadGrafo {
    public Grafo readArquivo(String nomeArq) throws IOException {
        Grafo g = new Grafo();
        List<String> linhas = Files.readAllLines(Paths.get(nomeArq));

        for(String linha : linhas){
            processaLinha(linha, g);
        }
        return g;
    }

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

    private String extrairConteudo(String linha){
        int inicio = linha.indexOf('(');
        int fim = linha.lastIndexOf(')');
        return linha.substring(inicio+1, fim);
    }
}
