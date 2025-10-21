package ucb.projeto.view;

import ucb.projeto.controller.ControleBancoDeDados;
import ucb.projeto.controller.ControleCarro;
import ucb.projeto.controller.ControleClientes;
import ucb.projeto.controller.ControleContratos;

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
            ControleCarro controleCarro = new ControleCarro(senha);
            ControleContratos controleContratos = new ControleContratos(controlerDB);
            ClientesCLIView clientesView = new ClientesCLIView(controleClientes, scanner);
            CarrosCLIView carrosCLIView = new CarrosCLIView(controleCarro, scanner);
            ContratoCLIView contratoCLIView = new ContratoCLIView(controleContratos, controleClientes, controleCarro, scanner);

            // A Fazer, criar um menu para as Views
            clientesView.rodar();
            carrosCLIView.rodar();
            contratoCLIView.rodar();

        } catch (SQLException error) {
            System.out.println("Falha ao se conectar com banco de dados " + error);
            throw new RuntimeException(error);
        }
    }
}
