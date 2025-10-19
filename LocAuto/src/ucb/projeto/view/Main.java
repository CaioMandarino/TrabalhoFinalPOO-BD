package ucb.projeto.view;

import ucb.projeto.controller.ControleBancoDeDados;
import ucb.projeto.controller.ControleClientes;
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
            ControleBancoDeDados controlerDB = new ControleBancoDeDados(senha);
            ControleClientes controleClientes = new ControleClientes(controlerDB);
            ClientesCLIView clientesView = new ClientesCLIView(controleClientes, scanner);
            clientesView.run();

        } catch (SQLException error) {
            System.out.println("Falha ao se conectar com banco de dados " + error);
            throw new RuntimeException(error);
        }
    }
}
