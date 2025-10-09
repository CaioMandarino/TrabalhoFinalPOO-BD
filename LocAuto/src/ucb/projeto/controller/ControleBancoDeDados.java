package ucb.projeto.controller;

import java.sql.*;

public class ControleBancoDeDados {
    private final String url = "jdbc:mysql://localhost:3306/locauto";
    private final String usuario = "root";

    private final Connection conexao;

    public ControleBancoDeDados(String senha) throws SQLException {
        conexao = DriverManager.getConnection(url, usuario, senha);
    }

    public Connection getConexao() {
        return conexao;
    }

    // Retorna o resultado da query do SQL
    public ResultSet executarCusultaBD(String sql) throws SQLException {
        PreparedStatement statement = conexao.prepareStatement(sql);
        return statement.executeQuery();
    }

    // Retorna o número de linhas afetadas
    public int executarAtualizacaoBD(String sql) throws SQLException {
        PreparedStatement statement = conexao.prepareStatement(sql);
        return statement.executeUpdate();
    }

}
