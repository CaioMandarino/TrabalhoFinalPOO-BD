package ucb.projeto.controller;

import ucb.projeto.model.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
        ResultSet resultado = controleBancoDeDados.executarConsultaBD(sql);

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


    public ArrayList<Cliente> listarClientes() throws SQLException {
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
        ResultSet resultado = controleBancoDeDados.executarConsultaBD(sql);

        ArrayList<Cliente> clientes = new ArrayList<Cliente>();

        if (!resultado.next()) {
            return clientes;
        }

        do {
            int idCliente = resultado.getInt("ID_Cliente");
            int id        = resultado.getInt("ID_Pessoa");
            String cpf    = resultado.getString("CPF");
            String nome   = resultado.getString("Nome");
            Date dataNascimento = resultado.getDate("Data_nasc");

            String municipio = resultado.getString("Municipio");
            String uf        = resultado.getString("UF");
            String cep       = resultado.getString("CEP");
            String compl     = resultado.getString("Complemento");
            String email     = resultado.getString("Email");

            String endereco = String.format("%s/%s, CEP %s%s", municipio, uf, cep, (compl != null && !compl.isBlank() ? " - " + compl : ""));

            Cliente cliente = new Cliente(
                    idCliente,
                    id,
                    cpf,
                    nome,
                    dataNascimento,
                    endereco,
                    email
            );

            clientes.add(cliente);
        } while (resultado.next());

        resultado.close();
        return clientes;
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

        return controleBancoDeDados.executarConsultaBD(sql);
    }
}
