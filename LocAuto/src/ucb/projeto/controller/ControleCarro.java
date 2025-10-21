package ucb.projeto.controller;

import ucb.projeto.model.Carro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControleCarro {
    private ControleBancoDeDados controleBanco;

    public ControleCarro(String senha) throws SQLException {
        controleBanco = new ControleBancoDeDados(senha);
    }

    // CREATE
    public void adicionarCarro(Carro carro) {
        String sql = "INSERT INTO carro (id_carro, placa, quilometragem, cor, status, ano_fabricacao, nome) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement state = controleBanco.getConexao().prepareStatement(sql)) {
            state.setInt(1, carro.getId_Carro());
            state.setString(2, carro.getPlaca());
            state.setFloat(3, carro.getQuilometragem());
            state.setString(4, carro.getCor());
            state.setString(5, carro.getStatus());
            state.setInt(6, carro.getAnoFabricacao());
            state.setString(7, carro.getNome());

            state.executeUpdate();
            System.out.println("Carro adicionado com sucesso!");
        } catch (SQLException error) {
            System.out.println("Erro ao adicionar carro: " + error.getMessage());
        }
    }

    // READ (listar todos)
    public List<Carro> listarCarros() {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT * FROM carro";
        try (ResultSet result = controleBanco.executarConsultaBD(sql)) {
            while (result.next()) {
                Carro carro = new Carro(
                        result.getInt("id_carro"),
                        result.getString("placa"),
                        result.getFloat("quilometragem"),
                        result.getString("cor"),
                        result.getString("status"),
                        result.getInt("ano_fabricacao"),
                        result.getString("nome")
                );
                carros.add(carro);
            }
        } catch (SQLException error) {
            System.out.println("Erro ao listar carros: " + error.getMessage());
        }
        return carros;
    }

    // READ (buscar por ID)
    public Carro buscarPorId(int id) {
        String sql = "SELECT * FROM carro WHERE id_carro = " + id;
        Carro carro = null;
        try (ResultSet result = controleBanco.executarConsultaBD(sql)) {
            if (result.next()) {
                carro = new Carro(
                        result.getInt("id_carro"),
                        result.getString("placa"),
                        result.getFloat("quilometragem"),
                        result.getString("cor"),
                        result.getString("status"),
                        result.getInt("ano_fabricacao"),
                        result.getString("nome")
                );
            }
        } catch (SQLException error) {
            System.out.println("Erro ao buscar carro: " + error.getMessage());
        }
        return carro;
    }

    // READ (buscar por placa)
    public Carro buscarPorPlaca(String placa) {
        String sql = "SELECT * FROM carro WHERE placa = ?";
        Carro carro = null;
        try (PreparedStatement ps = controleBanco.getConexao().prepareStatement(sql)) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException error) {
            System.out.println("Erro ao buscar carro por placa: " + error.getMessage());
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
            int linhas = controleBanco.executarAtualizacaoBD(sql);
            if (linhas > 0) System.out.println("Carro atualizado com sucesso!");
        } catch (SQLException error) {
            System.out.println("Erro ao atualizar carro: " + error.getMessage());
        }
    }

    // DELETE
    public void excluirCarro(int id) {
        String sql = "DELETE FROM carro WHERE id_carro=" + id;
        try {
            int linhas = controleBanco.executarAtualizacaoBD(sql);
            if (linhas > 0) System.out.println("Carro excluído com sucesso!");
        } catch (SQLException error) {
            System.out.println("Erro ao excluir carro: " + error.getMessage());
        }
    }
}