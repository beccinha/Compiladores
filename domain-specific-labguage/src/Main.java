import Linguagem.SimpleSQL;

public class Main {

     public static final String QUERY = "SELECT id nome FROM usuario";

//    public static final String QUERY = "INSERT usuario VALUES 4 Heloisa (81)996354295";

    public static void main(String[] args) {
        SimpleSQL.run(QUERY);
    }
}
