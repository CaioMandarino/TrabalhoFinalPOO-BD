package ucb.projeto.controller;

import ucb.projeto.model.Funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Date;

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
        ResultSet resultado = controleBancoDeDados.executarConsultaBD(sqlBusca);

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
        String deletarFuncionarioSQL = String.format(
                "DELETE f FROM tb_funcionario f " +
                        "JOIN tb_pessoa p ON p.ID_Pessoa = f.fk_id_pessoa " +
                        "WHERE p.CPF = %s;",
                sqlValorValido(cpf)
        );

        String deletarPessoaSQL = String.format(
                "DELETE FROM tb_Pessoa WHERE CPF = %s;",
                sqlValorValido(cpf)
        );

        int numeroLinhas = controleBancoDeDados.executarAtualizacaoBD(deletarFuncionarioSQL);
        int numeroLinhos = controleBancoDeDados.executarAtualizacaoBD(deletarPessoaSQL);

        if (numeroLinhas > 0) {
            System.out.println("Funcionario deletado com sucesso!");
        }
    }

    public ArrayList<Funcionario> listarFuncionarios() throws SQLException {
        String sql = """
            SELECT
                id_funcionarios,
                f.data_admissao,
                f.cargo,
                f.salario,
                f.fk_id_contrato,
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
                p.Telefone2,
                c.id_contrato
            FROM tb_funcionario f
            JOIN tb_pessoa p   ON p.ID_Pessoa   = f.fk_id_pessoa
            LEFT JOIN tb_contrato c ON c.id_contrato = f.fk_id_contrato;
            """;

        ResultSet resultado = controleBancoDeDados.executarCusultaBD(sql);
        ArrayList <Funcionario> funcionarios = new ArrayList<Funcionario>();

        if(!resultado.next()){
            return funcionarios;
        }

        do {
            Integer id_funcionario = resultado.getInt("id_funcionarios");
            Date data_admissao = resultado.getDate("data_admissao");
            String cargo = resultado.getString("cargo");
            Float salario = resultado.getFloat("salario");
            Integer fk_id_contrato = resultado.getInt("fk_id_contrato");
            Integer id_pessoa = resultado.getInt("ID_Pessoa");
            String cpf    = resultado.getString("CPF");
            String nome   = resultado.getString("Nome");
            Date dataNascimento = resultado.getDate("Data_nasc");
            String municipio = resultado.getString("Municipio");
            String uf        = resultado.getString("UF");
            String cep       = resultado.getString("CEP");
            String compl     = resultado.getString("Complemento");
            String email     = resultado.getString("Email");

            String endereco = String.format("%s/%s, CEP %s%s", municipio, uf, cep, (compl != null && !compl.isBlank() ? " - " + compl : ""));

            Funcionario funcionario = new Funcionario(
                    id_pessoa,
                    cpf,
                    nome,
                    dataNascimento,
                    endereco,
                    email,
                    fk_id_contrato,
                    salario,
                    cargo,
                    data_admissao,
                    id_funcionario);

            funcionarios.add(funcionario);
        } while (resultado.next());

        return funcionarios;
    }

    public Funcionario buscarFuncionarioPorCPF(String cpf) throws SQLException {
        String sql = String.format("""
            SELECT
                f.id_funcionarios,
                f.data_admissao,
                f.cargo,
                f.salario,
                f.fk_id_contrato,
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
                p.Telefone2,
                c.id_contrato
            FROM tb_funcionario f
            JOIN tb_pessoa p   ON p.ID_Pessoa   = f.fk_id_pessoa
            LEFT JOIN tb_contrato c ON c.id_contrato = f.fk_id_contrato
            WHERE p.CPF = %s
            LIMIT 1;
            """, sqlValorValido(cpf));

        ResultSet resultado = controleBancoDeDados.executarCusultaBD(sql);

        if(!resultado.next()){
            return null;
        }

        Integer id_funcionario = resultado.getInt("id_funcionarios");
        Date data_admissao = resultado.getDate("data_admissao");
        String cargo = resultado.getString("cargo");
        Float salario = resultado.getFloat("salario");
        Integer fk_id_contrato = resultado.getInt("fk_id_contrato");
        Integer id_pessoa = resultado.getInt("ID_Pessoa");
        String nome   = resultado.getString("Nome");
        Date dataNascimento = resultado.getDate("Data_nasc");
        String municipio = resultado.getString("Municipio");
        String uf        = resultado.getString("UF");
        String cep       = resultado.getString("CEP");
        String compl     = resultado.getString("Complemento");
        String email     = resultado.getString("Email");

        String endereco = String.format("%s/%s, CEP %s%s", municipio, uf, cep, (compl != null && !compl.isBlank() ? " - " + compl : ""));

        Funcionario funcionario = new Funcionario(
                id_pessoa,
                cpf,
                nome,
                dataNascimento,
                endereco,
                email,
                fk_id_contrato,
                salario,
                cargo,
                data_admissao,
                id_funcionario);

        resultado.close();

        return  funcionario;
    }
}

