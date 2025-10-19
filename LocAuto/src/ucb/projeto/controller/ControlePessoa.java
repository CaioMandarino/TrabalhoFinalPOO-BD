package ucb.projeto.controller;

import java.sql.SQLException;

abstract class ControlePessoa {
    protected ControleBancoDeDados controleBancoDeDados;

    public ControlePessoa(ControleBancoDeDados controleBancoDeDados) {
        this.controleBancoDeDados = controleBancoDeDados;
    }

    protected static String sqlValorValido(String valor) {
        return valor == null ? "NULL" : "'" + valor.replace("'", "''") + "'";
    }

    protected static String sqlDataValida(String data) {
        return (data == null || data.isBlank()) ? "NULL" : "'" + data + "'";
    }

    public void inserirPessoa(
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

        if (cpf == null || cpf.isBlank() || nome == null || nome.isBlank()) {
            System.out.println("CPF e Nome são obrigatórios.");
            return;
        }

        String inserirSQL = String.format(
                "INSERT INTO tb_Pessoa " +
                        "(CPF, Nome, Data_nasc, CEP, Municipio, UF, Complemento, Email, Telefone1, Telefone2) " +
                        "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s);",
                sqlValorValido(cpf),
                sqlValorValido(nome),
                sqlDataValida(data_nasc),
                sqlValorValido(cep),
                sqlValorValido(municipio),
                sqlValorValido(uf),
                sqlValorValido(complemento),
                sqlValorValido(email),
                sqlValorValido(telefone1),
                sqlValorValido(telefone2)
        );

        controleBancoDeDados.executarAtualizacaoBD(inserirSQL);
    }



}
