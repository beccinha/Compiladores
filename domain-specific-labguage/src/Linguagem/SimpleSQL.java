package Linguagem;

import Tabela.Tabela;

import java.io.*;
import java.util.*;

public class SimpleSQL {

//    public static final String PATH = "C:\\Users\\Rebec\\Documents\\Intellij\\domain-specific-labguage\\src\\Banco\\";
    public static final String PATH = "C:\\Users\\arthu\\Documents\\GitHub\\domain-pecifc-language\\domain-specific-labguage\\src\\Banco\\";

    public static String type;

    public static void run(String query){
        System.out.println("-> INICIO\n");
        System.out.println("-> " + query + "\n");
        /** valida a query **/
        String msg = isValid(query);

        int index = 1;
        String condition = null;
        Map<String, String> mapAtributo = new TreeMap<>();
        Map<String, String> mapCondicao = new TreeMap<>();
        if(query != null && msg == null){
            String[] map = query.split(" ");
            type = map[0];
            switch (type){
                case "SELECT":
                    while (!map[index].equals("FROM")){
                        mapAtributo.put(map[index].toUpperCase(), map[index]);
                        index++;
                    }
                    if((index + 5) == map.length && map[index + 2].equals("WHERE")){
                        mapCondicao.put(map[index + 3], map[index + 4]);
                    }
                    select(map[index + 1], mapAtributo, condition);
                    break;
                case "INSERT":
                    index = 3;
                    String row = "";
                    while (index < map.length){
                        if(row.equals("")) row = map[index];
                        else row = row + "-" +map[index];
                        index++;
                    }
                    insert(map[1], row);
                    break;
                case "UPDATE":

                case "DELETE":
            }
        }
        if (msg == null) {
            System.out.println("\n-> FIM");
        } else {
            System.out.println(msg);
        }
    }

    /**
     * @apiNote seleciona um registro de uma tabela
     * @param input
     * @param mapAtributo
     * @return tabela
    **/
    public static void select(String input, Map<String, String>  mapAtributo, String condition){
        String database = PATH + input + ".txt";
        try{

            /** instancia as listas das tabelas **/
            Tabela dataTable = new Tabela();
            dataTable.setBody(new ArrayList<>());
            dataTable.setHeader(new ArrayList<>());

            /** Lê o arquivo (tabela) **/
            BufferedReader file = new BufferedReader(new FileReader(database));

            /** Pega a quantidade de linhas e colunas do arquivo (tabela) **/
            String tabela[][] = getTable(file, getDimensions(new BufferedReader(new FileReader(database))));

            /** Caso nenhum campo seja especificado, retorna todos **/
            if(mapAtributo == null || mapAtributo.get("*") != null || mapAtributo.isEmpty()){
                for(int index = 0; index < tabela[0].length; index++){
                    mapAtributo.put(tabela[0][index].toUpperCase(), tabela[0][index]);
                }
                mapAtributo.remove("*");
            }

            /** percorre as linhas e colunas e cria uma TABELA **/
            for(int linha = 0; linha < tabela.length; linha++){

                /** Adiciona a cláusula WHERE **/
                if(linha > 0 && condition != null && !tabela[linha][0].equals(condition)) continue;

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
     * @apiNote insere um registro em uma tabela
     * @param input
     * @param row
     * @return tabela
    **/
    public static void insert(String input, String  row){
        String database = PATH + input + ".txt";
        File file = new File(database);
        try {
            /** Reescreve arquivo com dados antigos **/
            FileReader reader = new FileReader(file);
            BufferedReader lerArq = new BufferedReader(reader);
            String linha = lerArq.readLine();
            FileWriter writer = new FileWriter(file);
            while (linha != null) {
                writer.write(linha);
                writer.write("\n");
                linha = lerArq.readLine();
            }
            reader.close();

            /** Insere novo dado **/
            writer.write(row);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n-> Usuário inserido com sucesso!\n");

        /** Instancia um map de atributos default **/
        Map<String, String> defaultMap = new TreeMap<>();
        defaultMap.put("*", "*");
        select(input, defaultMap, null);
    }

    /**
     * @apiNote deleta um registro de uma tabela
     * @param input
     * @param mapAtributo
     * @return tabela
    **/
    public static void delete(String input, Map<String, String>  mapAtributo){
        String database = PATH + input + ".txt";
        System.out.println("=================== TABELA: " +input + " ===================");
    }

    /**
     * @apiNote atualiza um registro de uma tabela
     * @param input
     * @param mapAtributo
     * @return tabela
    **/
    public static void update(String input, Map<String, String>  mapAtributo){
        String database = PATH + input + ".txt";
        System.out.println("=================== TABELA: " +input + " ===================");
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
        if(!map[0].equals("SELECT") && !map[0].equals("INSERT") && !map[0].equals("UPDATE") && !map[0].equals("DELETE")) return "ERRO 2: comando inválido";
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
                if(count < tabela.getHeader().size()) {
                    body = body + "                            " + tabela.getBody().get(index);
                }
                else{
                    body = body + skip + tabela.getBody().get(index);
                    count = 0;
                }
            }
            count++;
        }
        System.out.println(header + "\n");
        System.out.println(body.replace("\n\n", "\n"));
    }

}
