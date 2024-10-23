
package connect;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Tentando conectar ao banco de dados MySQL
        Connection conn = Conexao.connect();
        
        // Verifica se a conexão foi bem-sucedida
        if (conn != null) {
            System.out.println("Conexão ao banco de dados realizada com sucesso!");
        } else {
            System.out.println("Falha ao conectar ao banco de dados.");
        }
    }
}
