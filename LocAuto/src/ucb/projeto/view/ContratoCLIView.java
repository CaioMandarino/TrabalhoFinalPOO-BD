package ucb.projeto.view;

import ucb.projeto.controller.ControleCarro;
import ucb.projeto.controller.ControleClientes;
import ucb.projeto.controller.ControleContratos;
import ucb.projeto.model.Carro;
import ucb.projeto.model.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ContratoCLIView {

    private final ControleContratos controleContratos;
    private final ControleClientes controleClientes;
    private final ControleCarro controleCarro;
    private final Scanner scanner;

    public ContratoCLIView(ControleContratos controleContratos, ControleClientes controleClientes, ControleCarro controleCarro, Scanner scanner) {
        this.controleContratos = controleContratos;
        this.controleClientes = controleClientes;
        this.controleCarro = controleCarro;
        this.scanner = scanner;
    }

    public void rodar() {
        while (true) {
            System.out.println("\n=== Menu Contratos ===");
            System.out.println("1) Criar contrato");
            System.out.println("2) Buscar contratos por CPF do cliente");
            System.out.println("3) Atualizar contrato por ID");
            System.out.println("4) Deletar contrato por ID");
            System.out.println("5) Listar todos os contratos");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");
            String op = scanner.nextLine();

            try {
                switch (op) {
                    case "1" -> criarContrato();
                    case "2" -> buscarPorCPF();
                    case "3" -> atualizarPorID();
                    case "4" -> deletarPorID();
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

    private void criarContrato() throws SQLException {
        System.out.println("\n== Criar contrato ==");


        Cliente cliente = encontrarClientePorCPF();
        if (cliente == null) {
            return;
        }


        Carro carro = encontrarCarroPorPlaca();
        if (carro == null) {
            return;
        }


        String dataInicio = pedirObrigatorio("Data Início (yyyy-mm-dd):  ");
        String dataFim = pedirOpcional("Data Fim (opcional): ");
        Float valor = pedirValorFloatOpcional("Valor total (opcional): ");

        controleContratos.criarContrato(cliente, carro, dataInicio, dataFim, valor);
        System.out.println("Contrato criado com sucesso.");
    }

    private void buscarPorCPF() throws SQLException {
        System.out.println("\n== Buscar contratos por CPF ==");
        String cpf = pedirObrigatorio("CPF do Cliente: ");

        ResultSet resultado = controleContratos.consultarContratosPorCPF(cpf);

        if (!resultado.next()) {
            System.out.println("Nenhum contrato encontrado para o CPF " + cpf);
            return;
        }
        do {
            printLinha(resultado);
        } while (resultado.next());

        resultado.close();
    }

    private void atualizarPorID() throws SQLException {
        System.out.println("\n== Atualizar por ID do Contrato ==");
        int idContrato = pedirInteiroObrigatorio("ID do Contrato a atualizar: ");


        System.out.println("Insira os dados do NOVO ou MESMO cliente:");
        Cliente cliente = encontrarClientePorCPF();
        if (cliente == null) {
            return;
        }


        System.out.println("Insira os dados do NOVO ou MESMO carro:");
        Carro carro = encontrarCarroPorPlaca();
        if (carro == null) {
            return;
        }


        String dataInicio = pedirObrigatorio("Nova Data Início (yyyy-mm-dd): ");
        String dataFim = pedirOpcional("Nova Data Fim (yyyy-mm-dd): ");
        Float valor = pedirValorFloatOpcional("Novo Valor total: ");

        controleContratos.atualizarContrato(idContrato, cliente, carro, dataInicio, dataFim, valor);
        System.out.println("Contrato atualizado com sucesso.");
    }

    private void deletarPorID() throws SQLException {
        System.out.println("\n== Deletar por ID do Contrato ==");
        int idContrato = pedirInteiroObrigatorio("ID do Contrato a deletar: ");
        controleContratos.deletarContrato(idContrato);
        System.out.println("Contrato deletado.");
    }

    private void listarTodos() throws SQLException {
        System.out.println("\n== Lista de contratos ==");
        ResultSet resultado = controleContratos.listarContratos();

        if (!resultado.next()) {
            System.out.println("Nenhum contrato encontrado.");
            return;
        }

        do {
            printLinha(resultado);
        } while (resultado.next());

        resultado.close();
    }


    private Cliente encontrarClientePorCPF() throws SQLException {
        String cpf = pedirObrigatorio("CPF do Cliente: ");
        ResultSet resultado = controleClientes.buscarPorCPF(cpf);

        if (!resultado.next()) {
            System.out.println("Cliente com CPF " + cpf + " não encontrado.");
            resultado.close();
            return null;
        }

        int idCliente = resultado.getInt("ID_Cliente");
        int id = resultado.getInt("ID_Pessoa");
        String nome = resultado.getString("Nome");
        java.util.Date dataNascimento = resultado.getDate("Data_nasc");
        String municipio = resultado.getString("Municipio");
        String uf = resultado.getString("UF");
        String cep = resultado.getString("CEP");
        String compl = resultado.getString("Complemento");
        String email = resultado.getString("Email");
        String endereco = String.format("%s/%s, CEP %s%s", municipio, uf, cep, (compl != null && !compl.isBlank() ? " - " + compl : ""));

        Cliente cliente = new Cliente(
                idCliente,
                id,
                cpf,
                nome,
                dataNascimento,
                endereco,
                email
        );

        System.out.printf("Cliente encontrado: %s (CPF: %s)%n", cliente.getNome(), cliente.getCPF());
        resultado.close();
        return cliente;
    }

    private Carro encontrarCarroPorPlaca() {
        String placa = pedirObrigatorio("Placa do Carro: ");
        Carro carro = controleCarro.buscarPorPlaca(placa);

        if (carro == null) {
            System.out.println("Carro com placa " + placa + " não encontrado.");
            return null;
        }

        System.out.printf("Carro encontrado: %s (Placa: %s)%n", carro.getNome(), carro.getPlaca());
        return carro;
    }

    private String pedirObrigatorio(String label) {
        while (true) {
            System.out.print(label);
            String valor = scanner.nextLine();
            if (valor != null && !valor.isBlank()) {
                return valor;
            }
            System.out.println("Valor obrigatório.");
        }
    }

    private int pedirInteiroObrigatorio(String label) {
        while (true) {
            String valorStr = pedirObrigatorio(label);
            try {
                return Integer.parseInt(valorStr);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Insira um número inteiro.");
            }
        }
    }

    private String pedirOpcional(String label) {
        System.out.print(label);
        String valor = scanner.nextLine();
        return (valor == null || valor.isBlank()) ? null : valor;
    }

    private Float pedirValorFloatOpcional(String label) {
        while (true) {
            System.out.print(label);
            String valor = scanner.nextLine();
            if (valor == null || valor.isBlank()) {
                return null;
            }
            try {
                // Substitui vírgula por ponto para garantir a conversão correta
                return Float.parseFloat(valor.replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Insira um número (ex: 1500.50) ou deixe em branco.");
            }
        }
    }

    private void printLinha(ResultSet resultado) throws SQLException {
                System.out.printf(
                "ID_Contrato: %d | Início: %s | Fim: %s | Valor: R$ %.2f | Cliente: %s (CPF: %s) | Carro: %s (Placa: %s)%n",
                resultado.getInt("id_contrato"),
                resultado.getString("data_inicio"),
                resultado.getString("data_fim"),
                resultado.getFloat("valor_total"),
                resultado.getString("Nome"),
                resultado.getString("CPF"),
                resultado.getString("nome_carro"),
                resultado.getString("placa")
        );
    }
}