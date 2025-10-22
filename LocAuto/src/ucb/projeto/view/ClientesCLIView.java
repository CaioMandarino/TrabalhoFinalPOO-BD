package ucb.projeto.view;

import ucb.projeto.controller.ControleClientes;
import ucb.projeto.model.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientesCLIView {

    private final ControleClientes controle;
    private final Scanner scanner;

    public ClientesCLIView(ControleClientes controle, Scanner scanner) {
        this.controle = controle;
        this.scanner = scanner;
    }

    public void rodar() {
        while (true) {
            System.out.println("\n=== Menu Clientes ===");
            System.out.println("1) Criar cliente");
            System.out.println("2) Buscar por CPF");
            System.out.println("3) Atualizar por CPF");
            System.out.println("4) Deletar por CPF");
            System.out.println("5) Listar todos");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");
            String op = scanner.nextLine();

            try {
                switch (op) {
                    case "1" -> criarCliente();
                    case "2" -> buscarPorCPF();
                    case "3" -> atualizarPorCPF();
                    case "4" -> deletarPorCPF();
                    case "5" -> listarTodos();
                    case "0" -> {
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (SQLException error) {
                System.out.println("Erro SQL: " + error.getMessage());
            }
        }
    }

    private void criarCliente() throws SQLException {
        System.out.println("\n== Criar cliente ==");
        String cpf = pedirObrigatorio("CPF (obrigatório): ");
        String nome = pedirObrigatorio("Nome (obrigatório): ");
        String dataNasc = pedirOpcional("Data nasc (yyyy-MM-dd, opcional): ");
        String cep = pedirOpcional("CEP (opcional): ");
        String municipio = pedirOpcional("Município (opcional): ");
        String uf = pedirOpcional("UF (2 chars, opcional): ");
        String complemento = pedirOpcional("Complemento (opcional): ");
        String email = pedirOpcional("Email (opcional): ");
        String tel1 = pedirOpcional("Telefone1 (opcional): ");
        String tel2 = pedirOpcional("Telefone2 (opcional): ");

        controle.inserirCliente(cpf, nome, dataNasc, cep, municipio, uf, complemento, email, tel1, tel2);
        System.out.println("Cliente criado com sucesso.");
    }

    private void buscarPorCPF() throws SQLException {
        System.out.println("\n== Buscar por CPF ==");
        String cpf = pedirObrigatorio("CPF: ");

        ResultSet resultado = controle.buscarPorCPF(cpf);

        if (!resultado.next()) {
            System.out.println("Nenhum cliente encontrado para CPF " + cpf);
            return;
        }
        do {
            printLinha(resultado);
        } while (resultado.next());

        resultado.close();
    }

    private void atualizarPorCPF() throws SQLException {
        System.out.println("\n== Atualizar por CPF ==");
        String cpf        = pedirObrigatorio("CPF do cliente a atualizar: ");
        String nome       = pedirObrigatorio("Novo Nome: ");
        String dataNasc   = pedirOpcional("Data nasc yyyy-MM-dd (vazio = NULL): ");
        String cep        = pedirOpcional("CEP (vazio = NULL): ");
        String municipio  = pedirOpcional("Município (vazio = NULL): ");
        String uf         = pedirOpcional("UF (vazio = NULL): ");
        String complemento= pedirOpcional("Complemento (vazio = NULL): ");
        String email      = pedirOpcional("Email (vazio = NULL): ");
        String tel1       = pedirOpcional("Telefone1 (vazio = NULL): ");
        String tel2       = pedirOpcional("Telefone2 (vazio = NULL): ");

        controle.atualizarCliente(cpf,
                convertaParaNULLSePrecisar(nome),
                convertaParaNULLSePrecisar(dataNasc),
                convertaParaNULLSePrecisar(cep),
                convertaParaNULLSePrecisar(municipio),
                convertaParaNULLSePrecisar(uf),
                convertaParaNULLSePrecisar(complemento),
                convertaParaNULLSePrecisar(email),
                convertaParaNULLSePrecisar(tel1),
                convertaParaNULLSePrecisar(tel2)
        );
        System.out.println("Cliente atualizado.");
    }

    private void deletarPorCPF() throws SQLException {
        System.out.println("\n== Deletar por CPF ==");
        String cpf = pedirObrigatorio("CPF: ");
        controle.deletarCliente(cpf);
        System.out.println("Cliente deletado.");
    }

    private void listarTodos() throws SQLException {
        System.out.println("\n== Lista de clientes ==");
        ArrayList<Cliente> clientes = controle.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        for (Cliente cliente: clientes) {
            System.out.println(cliente.verCliente());
        }
    }

    private static String convertaParaNULLSePrecisar(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }

    private String pedirObrigatorio(String msg) {
        while (true) {
            System.out.print(msg);
            String valor = scanner.nextLine();
            if (valor != null && !valor.isBlank()) {
                return valor;
            }
            System.out.println("Valor obrigatório.");
        }
    }

    private String pedirOpcional(String msg) {
        System.out.print(msg);
        String valor = scanner.nextLine();
        return (valor == null || valor.isBlank()) ? null : valor;
    }

    private void printLinha(ResultSet resultado) throws SQLException {
        System.out.printf(
                "ID_Cliente=%d, FK=%d, CPF=%s, Nome=%s, Data_nasc=%s, CEP=%s, Municipio=%s, UF=%s, Comp=%s, Email=%s, Tel1=%s, Tel2=%s%n",
                resultado.getInt("ID_Cliente"),
                resultado.getInt("Fk_ID_Pessoa"),
                resultado.getString("CPF"),
                resultado.getString("Nome"),
                resultado.getString("Data_nasc"),
                resultado.getString("CEP"),
                resultado.getString("Municipio"),
                resultado.getString("UF"),
                resultado.getString("Complemento"),
                resultado.getString("Email"),
                resultado.getString("Telefone1"),
                resultado.getString("Telefone2")
        );
    }
}