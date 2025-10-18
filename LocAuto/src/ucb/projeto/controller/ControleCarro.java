package ucb.projeto.controller;

import ucb.projeto.model.Carro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControleCarro {
    private ControleBancoDeDados cbd;

    public ControleCarro(String senha) throws SQLException {
        cbd = new ControleBancoDeDados(senha);
    }

    // CREATE
    public void adicionarCarro(Carro carro) {
        String sql = "INSERT INTO carro (id_carro, placa, quilometragem, cor, status, ano_fabricacao, nome) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = cbd.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, carro.getId_Carro());
            stmt.setString(2, carro.getPlaca());
            stmt.setFloat(3, carro.getQuilometragem());
            stmt.setString(4, carro.getCor());
            stmt.setString(5, carro.getStatus());
            stmt.setInt(6, carro.getAnoFabricacao());
            stmt.setString(7, carro.getNome());

            stmt.executeUpdate();
            System.out.println("Carro adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar carro: " + e.getMessage());
        }
    }

    // READ (listar todos)
    public List<Carro> listarCarros() {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT * FROM carro";
        try (ResultSet rs = cbd.executarCusultaBD(sql)) {
            while (rs.next()) {
                Carro c = new Carro(
                        rs.getInt("id_carro"),
                        rs.getString("placa"),
                        rs.getFloat("quilometragem"),
                        rs.getString("cor"),
                        rs.getString("status"),
                        rs.getInt("ano_fabricacao"),
                        rs.getString("nome")
                );
                carros.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar carros: " + e.getMessage());
        }
        return carros;
    }

    // READ (buscar por ID)
    public Carro buscarPorId(int id) {
        String sql = "SELECT * FROM carro WHERE id_carro = " + id;
        Carro carro = null;
        try (ResultSet rs = cbd.executarCusultaBD(sql)) {
            if (rs.next()) {
                carro = new Carro(
                        rs.getInt("id_carro"),
                        rs.getString("placa"),
                        rs.getFloat("quilometragem"),
                        rs.getString("cor"),
                        rs.getString("status"),
                        rs.getInt("ano_fabricacao"),
                        rs.getString("nome")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar carro: " + e.getMessage());
        }
        return carro;
    }

    // UPDATE
    public void atualizarCarro(Carro carro) {
        String sql = "UPDATE carro SET " +
                "placa='" + carro.getPlaca() + "', " +
                "quilometragem=" + carro.getQuilometragem() + ", " +
                "cor='" + carro.getCor() + "', " +
                "status='" + carro.getStatus() + "', " +
                "ano_fabricacao=" + carro.getAnoFabricacao() + ", " +
                "nome='" + carro.getNome() + "' " +
                "WHERE id_carro=" + carro.getId_Carro();
        try {
            int linhas = cbd.executarAtualizacaoBD(sql);
            if (linhas > 0) System.out.println("Carro atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar carro: " + e.getMessage());
        }
    }

    // DELETE
    public void excluirCarro(int id) {
        String sql = "DELETE FROM carro WHERE id_carro=" + id;
        try {
            int linhas = cbd.executarAtualizacaoBD(sql);
            if (linhas > 0) System.out.println("Carro excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir carro: " + e.getMessage());
        }
    }
}