package ucb.projeto.view;

import ucb.projeto.controller.ControleBancoDeDados;
import ucb.projeto.controller.ControleClientes;
import ucb.projeto.controller.ControleFuncionario;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a senha do bando de dados: ");
        String senha = scanner.nextLine();

        ControleBancoDeDados controlerDB;
        try {
            controlerDB = new ControleBancoDeDados(senha);
            ControleClientes controleClientes = new ControleClientes(controlerDB);
            ClientesCLIView clientesView = new ClientesCLIView(controleClientes, scanner);
            clientesView.rodar();

        } catch (SQLException error) {
            System.out.println("Falha ao se conectar com banco de dados " + error);
            throw new RuntimeException(error);
        }

        ControleFuncionario controlfunc = new ControleFuncionario(controlerDB);
        FuncionariosCLIView func = new FuncionariosCLIView( controlfunc , scanner);
        func.menu();
    }
}
