package Tabela;

import java.util.List;

public class Tabela{

   private List<String> header;

   private List<String> body;

    public Tabela() {
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public List<String> getBody() {
        return body;
    }

    public void setBody(List<String> body) {
        this.body = body;
    }
}
