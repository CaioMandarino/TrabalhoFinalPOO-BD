package ucb.projeto.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControleClientes extends ControlePessoa {

    public ControleClientes(ControleBancoDeDados controleBancoDeDados) {
        super(controleBancoDeDados);
    }

    public void inserirCliente (
        String cpf,
        String nome,
        String data_nasc,
        String cep,
        String municipio,
        String uf,
        String complemento,
        String email,
        String telefone1,
        String telefone2
    ) throws SQLException  {
        inserirPessoa(cpf, nome, data_nasc, cep, municipio, uf, complemento, email, telefone1, telefone2);
        String sql = String.format("SELECT * FROM tb_Pessoa WHERE CPF = '%s';", cpf);
        ResultSet resultado = controleBancoDeDados.executarCusultaBD(sql);

        if (!resultado.next()) {
            System.out.println("Error ao associar pessoa a cliente");
            return;
        }
        int id = resultado.getInt("id_pessoa");

        String inserirSQL = String.format("INSERT INTO tb_Cliente (Fk_ID_Pessoa) VALUES (%d)", id);

        controleBancoDeDados.executarAtualizacaoBD(inserirSQL);
    }

    public void atualizarCliente(
            String cpf,
            String nome,
            String data_nasc,
            String cep,
            String municipio,
            String uf,
            String complemento,
            String email,
            String telefone1,
            String telefone2
    ) throws SQLException {
        String sql = String.format(
                "UPDATE tb_Pessoa SET " +
                        "Nome=%s, Data_nasc=%s, CEP=%s, Municipio=%s, UF=%s, Complemento=%s, Email=%s, Telefone1=%s, Telefone2=%s " +
                        "WHERE CPF=%s;",
                sqlValorValido(nome), sqlDataValida(data_nasc), sqlValorValido(cep), sqlValorValido(municipio), sqlValorValido(uf),
                sqlValorValido(complemento), sqlValorValido(email), sqlValorValido(telefone1), sqlValorValido(telefone2),
                sqlValorValido(cpf)
        );
        controleBancoDeDados.executarAtualizacaoBD(sql);
    }

    public void deletarCliente(String cpf) throws SQLException {
        String deletarClienteSQL = String.format(
                "DELETE c FROM tb_Cliente c " +
                        "JOIN tb_Pessoa p ON p.ID_Pessoa = c.Fk_ID_Pessoa " +
                        "WHERE p.CPF = %s;",
                sqlValorValido(cpf)
        );
        String deletarPessoaSQL = String.format(
                "DELETE FROM tb_Pessoa WHERE CPF = %s;",
                sqlValorValido(cpf)
        );

        controleBancoDeDados.executarAtualizacaoBD(deletarClienteSQL);
        controleBancoDeDados.executarAtualizacaoBD(deletarPessoaSQL);
    }


    public ResultSet listarClientes() throws SQLException {
        String sql = """
            SELECT
                c.Fk_ID_Pessoa,
                c.ID_Cliente,
                p.ID_Pessoa,
                p.CPF,
                p.Nome,
                p.Data_nasc,
                p.CEP,
                p.Municipio,
                p.UF,
                p.Complemento,
                p.Email,
                p.Telefone1,
                p.Telefone2
            FROM tb_Cliente c
            JOIN tb_Pessoa p ON p.ID_Pessoa = c.Fk_ID_Pessoa;
            """;
        return controleBancoDeDados.executarCusultaBD(sql);
    }

    public ResultSet buscarPorCPF(String cpf) throws SQLException {
        String sql = String.format("""
        SELECT
            c.Fk_ID_Pessoa,
            c.ID_Cliente,
            p.ID_Pessoa,
            p.CPF,
            p.Nome,
            p.Data_nasc,
            p.CEP,
            p.Municipio,
            p.UF,
            p.Complemento,
            p.Email,
            p.Telefone1,
            p.Telefone2
        FROM tb_Cliente c
        JOIN tb_Pessoa p ON p.ID_Pessoa = c.Fk_ID_Pessoa
        WHERE p.CPF = %s;
        """, sqlValorValido(cpf));

        return controleBancoDeDados.executarCusultaBD(sql);
    }
}
