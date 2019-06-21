import Linguagem.SimpleSQL;


public class Main {

    public static final String QUERY = "SELECT nome id FROM usuario";

    public String teste = "Teste";

    public static void main(String[] args) {
        SimpleSQL.run(QUERY);
    }
}
