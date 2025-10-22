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
        String CPF = lerItemObg("CPF : ");
        String Nome = lerItemOpc("Nome (opcional):");
        String Dt_nasc = lerItemOpc("Data de nascimento (yyyy-mm-dd)(opcional): ");
        String Cep = lerItemOpc("Cep (opcional): ");
        String Munic = lerItemOpc("Municipio (opcional): ");
        String Uf = lerItemOpc("Uf (opcional): ");
        String Compl = lerItemOpc("Complemento (opcional): ");
        String Email = lerItemOpc("email (opcional): ");
        String tel1 = lerItemOpc("telefone1 (opcional): ");
        String tel2 = lerItemOpc("telefone2 (opcional): ");
        String dt_adms = lerItemObg("Data_admissão : ");
        String Cargo = lerItemObg("Cargo : ");
        Double Salario = Double.parseDouble(lerItemObg("Salario : "));
        Integer idContrato = lerIntObg("Digite o id do contrato que ele é responsável: ");

        System.out.println("Funcionario criado com sucesso!");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
        controleF.inserirFuncionario(CPF , Nome , Dt_nasc , Cep , Munic , Uf , Compl , Email , tel1 , tel2 , dt_adms , Cargo , Salario, idContrato);
    }
    private void autalFuncCPF() throws SQLException {
        System.out.println("===Atualizar Funcionario pelo CPF===");
        String CPF = lerItemObg("CPF: ");
        String Nome = lerItemOpc("Nome (opcional): ");
        String Dt_nasc = lerItemOpc("Data de nascimento (opcional): ");
        String Cep = lerItemOpc("Cep (opcional): ");
        String Munic = lerItemOpc("Municipio (opcional): ");
        String Uf = lerItemOpc("UF (opcional): ");
        String Compl = lerItemOpc("Complemento (opcional): ");
        String Email = lerItemOpc("Email (opcional): ");
        String Tel1 = lerItemOpc("Telefone1 (opcional): ");
        String Tel2 = lerItemOpc("Telefone2 (opcional): ");
        String Dt_admis = lerItemObg("Data_admissão: ");
        String Cargo = lerItemObg("Cargo: ");
        Double Salario = Double.parseDouble(lerItemObg("Salario: "));

        controleF.atualizarFuncionarioPorCPF(CPF , Dt_nasc , Cargo  ,Salario , null );

        System.out.println("\nFUncionário atualizado.");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
    }
    private void delFuncCPF() throws SQLException{
        System.out.println("===Atualizar Funcionario pelo CPF===");
        String cpf = lerItemObg("CPF: ");

        controleF.deletarFuncionarioPorCPF(cpf);
        System.out.println("FUncionario deletado comsucesso!");
    }
    private void mostrarFuncs() throws SQLException{
        System.out.println("\n===Lista de funcionarios===");
        ArrayList<Funcionario> funcionarios = controleF.listarFuncionarios();

        if(funcionarios.isEmpty()){
            System.out.println("\nA lista está vazia.");
            System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");

            return;
        }

        for (Funcionario funcionario : funcionarios) {
            System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
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
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
    }
    private void buscarFuncCPF() throws SQLException{
        System.out.println("===Buscar funcionario pelo CPF===");
        String CPF = lerItemObg("CPF: ");
        Funcionario funcionario = controleF.buscarFuncionarioPorCPF(CPF);

        if(funcionario == null){
            System.out.println("Nenhum funcionario foi encontrado para o CPF: " + CPF);
            return;
        }
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
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
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
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