package ucb.projeto.view;

import ucb.projeto.controller.ControleBancoDeDados;
import ucb.projeto.model.Pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a senha do bando de dados: ");
        String senha = scanner.nextLine();

        try {
            ControleBancoDeDados controler = new ControleBancoDeDados(senha);
            System.out.println("Conexão bem sucedida");

            String inserirSQL = "INSERT INTO tb_pessoa(cpf, nome, data_nasc, email) VALUES ('00011122212', 'Caio Rangel', '2010-10-5', 'test123@gmail.com')";
            controler.executarAtualizacaoBD(inserirSQL);

            ResultSet resultado = controler.executarCusultaBD("SELECT * FROM tb_pessoa");

            while (resultado.next()) {
                int id = resultado.getInt("id_pessoa");
                String cpf = resultado.getString("cpf");
                String nome = resultado.getString("nome");
                String email = resultado.getString("email");

                System.out.println("ID: " + id);
                System.out.println("CPF " + cpf);
                System.out.println("Nome: " + nome);
                System.out.println("Email: " + email);
            }

        } catch (SQLException error) {
            System.out.println("Falha ao se conectar com banco de dados " + error);
            throw new RuntimeException(error);
        }
    }
}
