package ucb.projeto.view;

import com.mysql.cj.protocol.Resultset;
import ucb.projeto.model.Funcionario;
import ucb.projeto.controller.ControleFuncionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;



public class FuncionariosCLIView {

    private final ControleFuncionario controleF;
    private final Scanner scanner;

    public FuncionariosCLIView(ControleFuncionario controleF, Scanner scanner) {
        this.scanner = scanner;
        this.controleF = controleF;
    }

    public void menu() {
        while (true) {
            System.out.println("===Menu Funcionario===");
            System.out.println("[1]-Criar funcionario;");
            System.out.println("[2]-Atualizar funcionario pelo CPF;");
            System.out.println("[3]-Deletar funcionário pelo CPF;");
            System.out.println("[4]-Mostrar todos os funcionarios");
            System.out.println("[5]-Buscar funcionario pelo CPF");
            System.out.println("[0]-sair");
            String op = scanner.nextLine();

            try {
                switch (op) {
                    case "1" -> criarFunc();
                    case "2" -> autalFuncCPF();
                    case "3" -> delFuncCPF();
                    case "4" -> mostrarFuncs();
                    case "5" -> buscarFuncCPF();
                    case "0" -> {
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }

            } catch( SQLException error ){
                System.out.println("Erro SQL : " + error.getMessage());
            }
        }
    }

    private void criarFunc() throws SQLException{
        System.out.println("===Criar funcionario===");
        String CPF = lerItemOpc("CPF: ");
        String Nome = lerItemOpc("Nome:");
        String Dt_nasc = lerItemOpc("Data de nascimento (yyyy-mm-dd): ");
        String Cep = lerItemOpc("Cep: ");
        String Munic = lerItemOpc("Municipio: ");
        String Uf = lerItemOpc("Uf: ");
        String Compl = lerItemOpc("Complemento: ");
        String Email = lerItemOpc("email: ");
        String tel1 = lerItemOpc("telefone1: ");
        String tel2 = lerItemOpc("telefone2:");
        String dt_adms = lerItemObg("Data_admissão(obrigatorio):");
        String Cargo = lerItemObg("Cargo:");
        Double Salario = Double.parseDouble(lerItemObg("Salario(obrigatorio):"));
        Integer idContrato = lerIntObg("Digite o id do contrato que ele é responsável:(obrigatorio) ");

        // colocar o criado com sucesso e oq é opcional
        controleF.inserirFuncionario(CPF , Nome , Dt_nasc , Cep , Munic , Uf , Compl , Email , tel1 , tel2 , dt_adms , Cargo , Salario, idContrato);
    }
    private void autalFuncCPF() throws SQLException {
        System.out.println("===Atualizar Funcionario pelo CPF===");
        String CPF = lerItemObg("CPF: ");
        String Nome = lerItemOpc("Nome: ");
        String Dt_nasc = lerItemOpc("Data de nascimento: ");
        String Cep = lerItemOpc("Cep: ");
        String Munic = lerItemOpc("Municipio: ");
        String Uf = lerItemOpc("UF: ");
        String Compl = lerItemOpc("Complemento: ");
        String Email = lerItemOpc("Email: ");
        String Tel1 = lerItemOpc("Telefone1: ");
        String Tel2 = lerItemOpc("Telefone2: ");
        String Dt_admis = lerItemOpc("Data_admissão: ");
        String Cargo = lerItemOpc("Cargo: ");
        Double Salario = Double.parseDouble(lerItemOpc("Salario: "));

        controleF.atualizarFuncionarioPorCPF(CPF , Dt_nasc , Cargo  ,Salario , null );

        System.out.println("\nCliente atualizado.");
    }
    private void delFuncCPF() throws SQLException{
        System.out.println("===Atualizar Funcionario pelo CPF===");
        String cpf = lerItemObg("CPF: ");

        controleF.deletarFuncionarioPorCPF(cpf);
    }
    private void mostrarFuncs() throws SQLException{
        System.out.println("\n===Lista de funcionarios===");
        ArrayList<Funcionario> funcionarios = controleF.listarFuncionarios();

        if(funcionarios.isEmpty()){
            System.out.println("\nA lista está vazia.");
            return;
        }

        for (Funcionario funcionario : funcionarios) {
            System.out.println("id_funcionario: " + funcionario.getId_funcionario());
            System.out.println("data_Admissao: " + funcionario.getData_Admissao());
            System.out.println("cargo: " + funcionario.getCargo());
            System.out.println("salario: " + funcionario.getSalario());
            System.out.println("id_contrato: " + funcionario.getId_contrato());

            System.out.println("Id: " + funcionario.getId());
            System.out.println("CPF: " + funcionario.getCPF());
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data_nascimento: " + funcionario.getData_nascimento());
            System.out.println("Endereco: " + funcionario.getEndereco());
            System.out.println("Email: " + funcionario.getEmail());
        }
    }
    private void buscarFuncCPF() throws SQLException{
        System.out.println("===Buscar funcionario pelo CPF===");
        String CPF = lerItemObg("CPF: ");
        Funcionario funcionario = controleF.buscarFuncionarioPorCPF(CPF);

        if(funcionario == null){
            System.out.println("Nenhum funcionario foi encontrado para o CPF: " + CPF);
            return;
        }

        System.out.println("id_funcionario: " + funcionario.getId_funcionario());
        System.out.println("data_Admissao: " + funcionario.getData_Admissao());
        System.out.println("cargo: " + funcionario.getCargo());
        System.out.println("salario: " + funcionario.getSalario());
        System.out.println("id_contrato: " + funcionario.getId_contrato());

        System.out.println("Id: " + funcionario.getId());
        System.out.println("CPF: " + funcionario.getCPF());
        System.out.println("Nome: " + funcionario.getNome());
        System.out.println("Data_nascimento: " + funcionario.getData_nascimento());
        System.out.println("Endereco: " + funcionario.getEndereco());
        System.out.println("Email: " + funcionario.getEmail());
    }


    private String lerItemOpc(String msg){
        System.out.println(msg);
        String valor = scanner.nextLine();
        return (valor == null || valor.isBlank())? null : valor;
    }

    private String lerItemObg( String msg){
        while (true) {
            System.out.print(msg);
            String valor = scanner.nextLine();
            if (valor != null && !valor.isBlank()) {
                return valor;
            }
            System.out.println("Valor obrigatório.");
        }
    }

    private int lerIntObg(String msg) {
        while (true) {
            System.out.print(msg + " ");
            try {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Digite um número inteiro válido.");
                scanner.nextLine();
            }
        }
    }


}