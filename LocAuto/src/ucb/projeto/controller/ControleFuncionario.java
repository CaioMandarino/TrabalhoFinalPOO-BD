package ucb.projeto.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class ControleFuncionario extends ControlePessoa {

    public ControleFuncionario(ControleBancoDeDados controleBancoDeDados) {
        super(controleBancoDeDados);
    }

    public void inserirFuncionario(
            String cpf,
            String nome,
            String data_nasc,
            String cep,
            String municipio,
            String uf,
            String complemento,
            String email,
            String telefone1,
            String telefone2,
            String data_admissao,
            String cargo,
            Double salario,
            Integer fk_id_contrato
    ) throws SQLException {

        if (cargo == null || cargo.isBlank() || data_admissao == null || data_admissao.isBlank() || salario == null) {
            System.out.println("data_admissao, cargo e salario são obrigatórios.");
            return;
        }

        inserirPessoa(cpf, nome, data_nasc, cep, municipio, uf, complemento, email, telefone1, telefone2);

        String sqlBusca = String.format("SELECT ID_Pessoa FROM tb_pessoa WHERE CPF = %s;", sqlValorValido(cpf));
        ResultSet resultado = controleBancoDeDados.executarCusultaBD(sqlBusca);

        if (!resultado.next()) {
            System.out.println("Falha ao localizar ID_Pessoa para o CPF " + cpf);
            return;
        }
        int idPessoa = resultado.getInt("ID_Pessoa");

        String salarioSQL = String.format(Locale.US, "%.2f", salario); // ponto decimal
        String fkContratoSQL = (fk_id_contrato == null) ? "NULL" : Integer.toString(fk_id_contrato);

        String inserirSQL = String.format(
                "INSERT INTO tb_funcionario " +
                        "(data_admissao, cargo, salario, fk_id_pessoa, fk_id_contrato) " +
                        "VALUES (%s, %s, %s, %d, %s);",
                sqlDataValida(data_admissao),
                sqlValorValido(cargo),
                salarioSQL,
                idPessoa,
                fkContratoSQL
        );

        controleBancoDeDados.executarAtualizacaoBD(inserirSQL);
    }

    public void atualizarFuncionarioPorCPF(
            String cpf,
            String data_admissao,
            String cargo,
            Double salario,
            Integer fk_id_contrato
    ) throws SQLException {

        String salarioSQL = (salario == null) ? "NULL" : String.format(Locale.US, "%.2f", salario);
        String fkContratoSQL = (fk_id_contrato == null) ? "NULL" : Integer.toString(fk_id_contrato);

        String sql = String.format(
                "UPDATE tb_funcionario f " +
                        "JOIN tb_pessoa p ON p.ID_Pessoa = f.fk_id_pessoa " +
                        "SET f.data_admissao=%s, f.cargo=%s, f.salario=%s, f.fk_id_contrato=%s " +
                        "WHERE p.CPF=%s;",
                sqlDataValida(data_admissao),
                sqlValorValido(cargo),
                salarioSQL,
                fkContratoSQL,
                sqlValorValido(cpf)
        );
        controleBancoDeDados.executarAtualizacaoBD(sql);
    }

    public void deletarFuncionarioPorCPF(String cpf) throws SQLException {
        String sql = String.format(
                "DELETE f FROM tb_funcionario f " +
                        "JOIN tb_pessoa p ON p.ID_Pessoa = f.fk_id_pessoa " +
                        "WHERE p.CPF = %s;",
                sqlValorValido(cpf)
        );
        controleBancoDeDados.executarAtualizacaoBD(sql);
    }

    public ResultSet listarFuncionarios() throws SQLException {
        String sql = """
            SELECT
                f.id_funcionarios,
                f.data_admissao,
                f.cargo,
                f.salario,
                f.fk_id_pessoa,
                f.fk_id_contrato,
                p.ID_Pessoa,
                p.CPF,
                p.Nome,
                c.id_contrato,
                c.data_inicio,
                c.data_fim,
                c.valor_total
            FROM tb_funcionario f
            JOIN tb_pessoa p   ON p.ID_Pessoa   = f.fk_id_pessoa
            LEFT JOIN tb_contrato c ON c.id_contrato = f.fk_id_contrato;
            """;

        return controleBancoDeDados.executarCusultaBD(sql);
    }

    public ResultSet buscarFuncionarioPorCPF(String cpf) throws SQLException {
        String sql = String.format("""
            SELECT
                f.id_funcionarios,
                f.data_admissao,
                f.cargo,
                f.salario,
                f.fk_id_pessoa,
                f.fk_id_contrato,
                p.ID_Pessoa,
                p.CPF,
                p.Nome,
                c.id_contrato,
                c.data_inicio,
                c.data_fim,
                c.valor_total
            FROM tb_funcionario f
            JOIN tb_pessoa p   ON p.ID_Pessoa   = f.fk_id_pessoa
            LEFT JOIN tb_contrato c ON c.id_contrato = f.fk_id_contrato
            WHERE p.CPF = %s
            LIMIT 1;
            """, sqlValorValido(cpf));
        return controleBancoDeDados.executarCusultaBD(sql);
    }
}

