package ucb.projeto.view;

import ucb.projeto.controller.ControleBancoDeDados;
import ucb.projeto.controller.ControleClientes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ClientesCLIView {

    private final ControleClientes controle;
    private final Scanner scanner;

    public ClientesCLIView(ControleClientes controle, Scanner scanner) {
        this.controle = controle;
        this.scanner = scanner;
    }

    public void run() {
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
        String cpf = ask("CPF (obrigatório): ");
        String nome = ask("Nome (obrigatório): ");
        String dataNasc = askOpt("Data nasc (yyyy-MM-dd, opcional): ");
        String cep = askOpt("CEP (opcional): ");
        String municipio = askOpt("Município (opcional): ");
        String uf = askOpt("UF (2 chars, opcional): ");
        String complemento = askOpt("Complemento (opcional): ");
        String email = askOpt("Email (opcional): ");
        String tel1 = askOpt("Telefone1 (opcional): ");
        String tel2 = askOpt("Telefone2 (opcional): ");

        controle.inserirCliente(cpf, nome, dataNasc, cep, municipio, uf, complemento, email, tel1, tel2);
        System.out.println("Cliente criado com sucesso.");
    }

    private void buscarPorCPF() throws SQLException {
        System.out.println("\n== Buscar por CPF ==");
        String cpf = ask("CPF: ");

        ResultSet resultado = controle.buscarPorCPF(cpf);

        if (!resultado.next()) {
            System.out.println("Nenhum cliente encontrado para CPF " + cpf);
            return;
        }
        do {
            printRow(resultado);
        } while (resultado.next());

        resultado.close();
    }

    private void atualizarPorCPF() throws SQLException {
        System.out.println("\n== Atualizar por CPF ==");
        String cpf        = ask("CPF do cliente a atualizar: ");
        String nome       = ask("Novo Nome: ");
        String dataNasc   = askOpt("Data nasc yyyy-MM-dd (vazio = NULL): ");
        String cep        = askOpt("CEP (vazio = NULL): ");
        String municipio  = askOpt("Município (vazio = NULL): ");
        String uf         = askOpt("UF (vazio = NULL): ");
        String complemento= askOpt("Complemento (vazio = NULL): ");
        String email      = askOpt("Email (vazio = NULL): ");
        String tel1       = askOpt("Telefone1 (vazio = NULL): ");
        String tel2       = askOpt("Telefone2 (vazio = NULL): ");

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
        String cpf = ask("CPF: ");
        controle.deletarCliente(cpf);
        System.out.println("Cliente deletado.");
    }

    private void listarTodos() throws SQLException {
        System.out.println("\n== Lista de clientes ==");
        ResultSet resultado = controle.listarClientes();

        if (!resultado.next()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        do {
            printRow(resultado);
        } while (resultado.next());

        resultado.close();
    }

    private static String convertaParaNULLSePrecisar(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }

    private String ask(String label) {
        while (true) {
            System.out.print(label);
            String valor = scanner.nextLine();
            if (valor != null && !valor.isBlank()) {
                return valor;
            }
            System.out.println("Valor obrigatório.");
        }
    }

    private String askOpt(String label) {
        System.out.print(label);
        String valor = scanner.nextLine();
        return (valor == null || valor.isBlank()) ? null : valor;
    }

    private void printRow(ResultSet resultado) throws SQLException {
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