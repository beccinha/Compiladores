import Linguagem.SimpleSQL;


public class Main {

    public static final String QUERY = "SELECT nome FROM turma";

    public static void main(String[] args) {
        SimpleSQL.run(QUERY);
    }
}
