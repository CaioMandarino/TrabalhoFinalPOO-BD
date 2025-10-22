package ucb.projeto.controller;

import ucb.projeto.model.Carro;
import ucb.projeto.model.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class ControleContratos {
    private ControleBancoDeDados controleBancoDeDados;

    public ControleContratos(ControleBancoDeDados controleBancoDeDados) {
        this.controleBancoDeDados = controleBancoDeDados;
    }

    public void criarContrato(Cliente cliente, Carro carro, String data_inicio, String data_fim, Float valor) throws SQLException {
        String valorDecimalSQL = (valor == null)
                ? "NULL"
                : String.format(Locale.US, "%.2f", valor);

        String inserirSQL = String.format(
                "INSERT INTO tb_contrato " +
                        "(data_inicio, data_fim, valor_total, fk_id_cliente, fk_id_carro) " +
                        "VALUES (%s, %s, %s, %d, %d);",
                sqlValorValido(data_inicio),
                sqlValorValido(data_fim),
                sqlValorValido(valorDecimalSQL),
                cliente.getId(),
                carro.getId_Carro()
        );

        controleBancoDeDados.executarAtualizacaoBD(inserirSQL);
    }

    public ResultSet listarContratos() throws SQLException {
        String sql = """
        SELECT
            t.id_contrato,
            t.data_inicio,
            t.data_fim,
            t.valor_total,
            t.fk_id_cliente,
            t.fk_id_carro,
            c.id_cliente,
            p.ID_Pessoa,
            p.CPF,
            p.Nome,
            k.placa,
            k.nome AS nome_carro
        FROM tb_contrato t
        JOIN tb_cliente  c ON c.id_cliente  = t.fk_id_cliente
        JOIN tb_pessoa   p ON p.ID_Pessoa   = c.Fk_ID_Pessoa
        JOIN tb_carro    k ON k.id_carro    = t.fk_id_carro;
        """;
        return controleBancoDeDados.executarConsultaBD(sql);
    }

    public ResultSet consultarContratosPorCPF(String cpf) throws SQLException {
        String sql = String.format("""
        SELECT
            t.id_contrato,
            t.data_inicio,
            t.data_fim,
            t.valor_total,
            t.fk_id_cliente,
            t.fk_id_carro,
            c.id_cliente,
            p.ID_Pessoa,
            p.CPF,
            p.Nome,
            k.placa,
            k.nome AS nome_carro
        FROM tb_contrato t
        JOIN tb_cliente  c ON c.id_cliente  = t.fk_id_cliente
        JOIN tb_pessoa   p ON p.ID_Pessoa   = c.Fk_ID_Pessoa
        JOIN tb_carro    k ON k.id_carro    = t.fk_id_carro
        WHERE p.CPF = %s;
        """, sqlValorValido(cpf));

        return controleBancoDeDados.executarConsultaBD(sql);
    }

    // UPDATE completo do contrato
    public void atualizarContrato(int idContrato,
                                  Cliente cliente,
                                  Carro carro,
                                  String data_inicio,
                                  String data_fim,
                                  Float valor) throws SQLException {
        String valorSQL = (valor == null) ? "NULL" : String.format(java.util.Locale.US, "%.2f", valor);
        String sql = String.format(
                "UPDATE tb_contrato SET " +
                        "data_inicio=%s, data_fim=%s, valor_total=%s, fk_id_cliente=%d, fk_id_carro=%d " +
                        "WHERE id_contrato=%d;",
                sqlValorValido(data_inicio),
                sqlValorValido(data_fim),
                valorSQL,
                cliente.getId(),
                carro.getId_Carro(),
                idContrato
        );

        controleBancoDeDados.executarAtualizacaoBD(sql);
    }

    public void deletarContrato(int idContrato) throws SQLException {
        String sql = String.format("DELETE FROM tb_contrato WHERE id_contrato=%d;", idContrato);
        controleBancoDeDados.executarAtualizacaoBD(sql);
    }

    private static String sqlValorValido(String valor) {
        return valor == null ? "NULL" : "'" + valor.replace("'", "''") + "'";
    }

    private static String sqlDataValida(String data) {
        return (data == null || data.isBlank()) ? "NULL" : "'" + data + "'";
    }

}
