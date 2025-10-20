package ucb.projeto.view;

import ucb.projeto.controller.ControleCarro;
import ucb.projeto.model.Carro;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CarrosCLIView {

    private final ControleCarro controle;
    private final Scanner scanner;

    public CarrosCLIView(ControleCarro controle, Scanner scanner) {
        this.controle = controle;
        this.scanner = scanner;
    }

    public void rodar() {
        while (true) {
            System.out.println("\n=== Menu Carros ===");
            System.out.println("1) Criar carro");
            System.out.println("2) Buscar por ID");
            System.out.println("3) Buscar por placa");
            System.out.println("4) Atualizar por ID");
            System.out.println("5) Deletar por ID");
            System.out.println("6) Listar todos");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");

            String op = scanner.nextLine();

            try {
                switch (op) {
                    case "1" -> criarCarro();
                    case "2" -> buscarPorId();
                    case "3" -> buscarPorPlaca();
                    case "4" -> atualizarPorId();
                    case "5" -> deletarPorId();
                    case "6" -> listarTodos();
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

    private void criarCarro() throws SQLException {
        System.out.println("\n== Criar carro ==");

        String placa = pedirObrigatorio("Placa: ");
        float quilometragem = pedirFloat("Quilometragem: ");
        String cor = pedirObrigatorio("Cor: ");
        String status = pedirObrigatorio("Status: ");
        int anoFabricacao = pedirInteiro("Ano de fabricação: ");
        String nome = pedirObrigatorio("Nome (modelo): ");

        Carro carro = new Carro(0, placa, quilometragem, cor, status, anoFabricacao, nome);
        controle.adicionarCarro(carro);

        System.out.println("Carro adicionado com sucesso.");
    }

    private void buscarPorId() throws SQLException {
        System.out.println("\n== Buscar carro por ID ==");
        int id = pedirInteiro("ID do carro: ");

        Carro carro = controle.buscarPorId(id);
        if (carro == null) {
            System.out.println("Nenhum carro encontrado com ID " + id);
        } else {
            System.out.println(carro.verCarro());
        }
    }

    private void buscarPorPlaca() throws SQLException {
        System.out.println("\n== Buscar carro por placa ==");
        String placa = pedirObrigatorio("Placa: ");

        Carro carro = controle.buscarPorPlaca(placa);
        if (carro == null) {
            System.out.println("Nenhum carro encontrado com a placa " + placa);
        } else {
            System.out.println(carro.verCarro());
        }
    }

    private void atualizarPorId() throws SQLException {
        System.out.println("\n== Atualizar carro por ID ==");
        int id = pedirInteiro("ID do carro: ");

        String novaPlaca = pedirObrigatorio("Nova placa: ");
        float novaQuilometragem = pedirFloat("Nova quilometragem: ");
        String novaCor = pedirObrigatorio("Nova cor: ");
        String novoStatus = pedirObrigatorio("Novo status: ");
        int novoAno = pedirInteiro("Novo ano de fabricação: ");
        String novoNome = pedirObrigatorio("Novo nome/modelo: ");

        Carro carro = new Carro(id, novaPlaca, novaQuilometragem, novaCor, novoStatus, novoAno, novoNome);
        controle.atualizarCarro(carro);

        System.out.println("Carro atualizado com sucesso.");
    }

    private void deletarPorId() throws SQLException {
        System.out.println("\n== Deletar carro por ID ==");
        int id = pedirInteiro("ID do carro: ");
        controle.excluirCarro(id);
        System.out.println("Carro deletado com sucesso.");
    }

    private void listarTodos() throws SQLException {
        System.out.println("\n== Lista de carros ==");
        List<Carro> carros = controle.listarCarros();

        if (carros.isEmpty()) {
            System.out.println("Nenhum carro encontrado.");
            return;
        }

        for (Carro carro : carros) {
            System.out.println(carro.verCarro());
        }
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

    private int pedirInteiro(String label) {
        while (true) {
            System.out.print(label);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException error) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private float pedirFloat(String label) {
        while (true) {
            System.out.print(label);
            try {
                return Float.parseFloat(scanner.nextLine());
            } catch (NumberFormatException error) {
                System.out.println("Digite um número decimal válido (ex: 123.45).");
            }
        }
    }
}
