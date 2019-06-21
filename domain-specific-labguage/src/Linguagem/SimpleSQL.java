package Linguagem;

import Tabela.Tabela;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SimpleSQL {

//    public static final String PATH = "C:\\Users\\Rebec\\Documents\\Intellij\\domain-specific-labguage\\src\\Banco\\";
    public static final String PATH = "C:\\Users\\arthu\\Documents\\GitHub\\domain-pecifc-language\\domain-specific-labguage\\src\\Banco\\";

    public static String type;

    public static void run(String query){

        /** valida a query **/
        String msg = isValid(query);

        int index = 1;
        Map<String, String> mapAtributo = new TreeMap<>();
        if(query != null && msg == null){
            String[] map = query.split(" ");
            type = map[0];
            while (!map[index].equals("FROM")){
                mapAtributo.put( map[index].toUpperCase(), map[index]);
                index++;
            }
            switch (type){
                case "SELECT":
                    select(map[index + 1], mapAtributo);
                case "INSERT":

                case "UPDATE":

                case "DELETE":
            }
        }
        if (msg == null) {
            System.out.println("======================== FIM ========================");
        } else {
            System.out.println(msg);
        }
    }

    public static void select(String input, Map<String, String>  mapAtributo){
        String database = PATH + input + ".txt";
        System.out.println("=================== TABELA: " +input + " ===================");
        try{

            /** instancia as listas das tabelas **/
            Tabela dataTable = new Tabela();
            dataTable.setBody(new ArrayList<>());
            dataTable.setHeader(new ArrayList<>());

            /** Lê o arquivo (tabela) **/
            BufferedReader file = new BufferedReader(new FileReader(database));

            /** Pega a quantidade de linhas e colunas do arquivo (tabela) **/
            String tabela[][] = getTable(file, getDimensions(new BufferedReader(new FileReader(database))));

            /** percorre as linhas e colunas e cria uma TABELA **/
            for(int linha = 0; linha < tabela.length; linha++){
                for(int coluna = 0; coluna < tabela.length; coluna++){
                    if(coluna < tabela[0].length && mapAtributo.get(tabela[0][coluna]) != null){
                        /** header **/
                        if(linha == 0){
                            dataTable.getHeader().add(tabela[linha][coluna]);
                        }
                        /** body **/
                        else{
                            dataTable.getBody().add(tabela[linha][coluna]);
                        }
                    }
                }
            }

            /** Printa a TABELA **/
            printaTabela(dataTable);

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * @apiNote recebe um arquivo e monta uma matriz com todos os campos
     * @param file
     * @param matriz
     * @return tabela
    **/
    public static String[][] getTable(BufferedReader file, String[][] matriz) throws IOException {
        int cont = 0;
        while(file.ready()){
            String[] linha = file.readLine().split("-");
            for(int index = 0; index < linha.length; index++){
                matriz[cont][index] = linha[index];
            }
            cont++;
        }
        file.close();
        return matriz;
    }

    /**
     * @apiNote recebe um arquivo e calcula as dimensões da tabela
     * @param file
     * @return dimensoes
    **/
    public static String[][] getDimensions(BufferedReader file) throws IOException {
        int row = 0;
        int column = 0;
        String linha;
        while((linha = file.readLine()) != null){
            row++;
            column = linha.split("-").length;
        }
        String dimensoes[][] = new String[row][column];
        file.close();
        return dimensoes;
    }

    /**
     * @apiNote verifica se a query é válida
     * @param query
     * @return o erro em formato de string
    **/
    public static String isValid(String query){
        int index = 0;
        if(query == null) return "ERRO 1: query inválida";
        String[] map = query.split(" ");
        if(!map[0].equals("SELECT")) return "ERRO 2: comando inválido";
        return null;
    }

    /**
     * @apiNote Recebe uma tabela e printa no console
     * @param tabela
     * @return printa tabela
     **/
    public static void printaTabela(Tabela tabela){
        String header = "";
        String body = "";
        String skip = "\n";
        int count = 0;
        for(int index = 0; index < tabela.getHeader().size(); index++){
            if(header.equals("")) header = tabela.getHeader().get(index);
            else  header = header + "                           " + tabela.getHeader().get(index);
        }
        for(int index = 0; index < tabela.getBody().size(); index++){
            if(body.equals("")) body = tabela.getBody().get(index);
            else {
                if(count == tabela.getHeader().size() - 1) {
                    body = body + "                            " + tabela.getBody().get(index) + skip;
                    count = -1;
                }
                else{
                    body = body + tabela.getBody().get(index);
                }
            }
            count++;
        }

        System.out.println(header);
        System.out.println(body);
    }
}
