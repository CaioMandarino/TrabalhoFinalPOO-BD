package ucb.projeto.view;

import ucb.projeto.controller.ControleBancoDeDados;
import ucb.projeto.controller.ControleCarro;
import ucb.projeto.controller.ControleClientes;
import ucb.projeto.controller.ControleFuncionario;
import ucb.projeto.controller.ControleContratos;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a senha do banco de dados: ");
        String senha = scanner.nextLine();

        ControleBancoDeDados controlerDB;
        try {
            controlerDB = new ControleBancoDeDados(senha);
            ControleClientes controleClientes = new ControleClientes(controlerDB);
            ControleFuncionario controlfunc = new ControleFuncionario(controlerDB);
            ControleCarro controleCarro = new ControleCarro(senha);
            ControleContratos controleContratos = new ControleContratos(controlerDB);
            FuncionariosCLIView funcionarioView = new FuncionariosCLIView( controlfunc , scanner);
            ClientesCLIView clientesView = new ClientesCLIView(controleClientes, scanner);
            CarrosCLIView carrosCLIView = new CarrosCLIView(controleCarro, scanner);
            ContratoCLIView contratoCLIView = new ContratoCLIView(controleContratos, controleClientes, controleCarro, scanner);
            String escolha = "";
            do{
                System.out.println("\n--- Menu Principal ---");
                System.out.println("1 - Clientes");
                System.out.println("2 - Funcionarios");
                System.out.println("3 - Carros");
                System.out.println("4 - Contratos");
                System.out.println("S - Sair");
                System.out.print("Escolha uma opção: ");

                escolha = scanner.nextLine();

                switch (escolha){
                    case "1":
                        clientesView.rodar();
                        break;
                    case "2":
                        funcionarioView.menu();
                        break;
                    case "3":
                        carrosCLIView.rodar();
                        break;
                    case "4":
                        contratoCLIView.rodar();
                        break;
                    case "S": break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");

                }
            }

            while (!escolha.equalsIgnoreCase("s"));
                scanner.close();


        } catch (SQLException error) {
            System.out.println("Falha ao se conectar com banco de dados " + error);
            throw new RuntimeException(error);
        }


    }
}
