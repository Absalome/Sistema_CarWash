
package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Método para conectar ao banco de dados
    public static Connection connect() {
        Connection conn = null;
        try {
            // URL de conexão JDBc
            String url = "jdbc:mysql://localhost:3306/testesdb"; 
            String user = "root"; // Nome de usuário do MySQL
            String password = ""; // Senha do MySQL (vazio se não houver)

            // Conexão ao banco de dados MySQL
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão bem-sucedida ao MySQL!");

        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
        return conn;
    }

    public static Object mycon() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
