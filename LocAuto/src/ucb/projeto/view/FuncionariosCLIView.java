package ucb.projeto.view;

import com.mysql.cj.protocol.Resultset;
import ucb.projeto.model.Funcionario;
import ucb.projeto.controller.ControleFuncionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        String Dt_nasc = lerItemOpc("Data de nascimento:");
        String Cep = lerItemOpc("Cep");
        String Munic = lerItemOpc("Municipio");
        String Uf = lerItemOpc("Uf");
        String Compl = lerItemOpc("Complemento:");
        String Email = lerItemOpc("email:");
        String tel1 = lerItemOpc("telefone1:");
        String tel2 = lerItemOpc("telefone2:");
        String dt_adms = lerItemObg("Data_admissão(obrifatorio):");
        String Cargo = lerItemObg("Cargo:");
        Double Salario = Double.parseDouble(lerItemObg("Salario(obrigatorio):"));
        //controleF.inserirFuncionario(CPF , Nome , Dt_nasc , Cep , Munic , Uf , Compl , Email , tel1 , tel2 , dt_adms , Cargo , Salario, );
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

    }
    private void mostrarFuncs() throws SQLException{
        System.out.println("\n===Lista de funcionarios===");
        //ResultSet resultado = controleF.listarFuncionarios();
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        if(!resultado.next()){
            System.out.println("\nA lista esta vazia.");
            return;
        }

        do{
            System.out.println(
                    "-_-_-_-_-_-_-_-_-_-_"+
                    "ID_funcionario:"+ resultado.getInt("id_funcionarios")+
                    "CPF: " + resultado.getString("cpf")+
                    "Nome: " + resultado.getString("nome")+
                    "Data de Nascimento: " + resultado.getDate("data_nasc")+
                    "Cep: " + resultado.getString("cep")+
                    "Munic: " + resultado.getString("municipio")+
                    "Uf: " + resultado.getString("uf")+
                    "Complemento: " + resultado.getString("complemento")+
                    " Email: " + resultado.getString("email")+
                    " telefone 1: " + resultado.getDouble("telefone1")+
                    "telefone 2: " + resultado.getDouble("telefone2")+
                    "Data-admissão: "+resultado.getDate("data_admissao")+
                    "Cargo: "+resultado.getString("cargo")+
                    "Salario: "+resultado.getDouble("salario")+
                    "Fk_id_pessoa: "+resultado.getInt("fk_id_pessoa")+
                    "Fk_id_contrato: "+resultado.getInt("fk_id_contrato")+
                    "-_-_-_-_-_-_-_-_-_-_"
            );
        }while(resultado.next());

    }
    private void buscarFuncCPF() throws SQLException{
        System.out.println("===Buscar funcionario pelo CPF===");
        String CPF = lerItemObg("CPF");
        ResultSet resultado = controleF.buscarFuncionarioPorCPF(CPF);

        if( !resultado.next()){
            System.out.println("Nenhum funcionario foi encontrado para o CPF : " + CPF);
            return;
        }
        System.out.println(
                "-_-_-_-_-_-_-_-_-_-_"+
                "ID_funcionario:"+ resultado.getInt("id_funcionarios")+
                "CPF: " + resultado.getString("cpf")+
                "Nome: " + resultado.getString("nome")+
                "Data de Nascimento: " + resultado.getDate("data_nasc")+
                "Cep: " + resultado.getString("cep")+
                "Munic: " + resultado.getString("municipio")+
                "Uf: " + resultado.getString("uf")+
                "Complemento: " + resultado.getString("complemento")+
                " Email: " + resultado.getString("email")+
                " telefone 1: " + resultado.getDouble("telefone1")+
                "telefone 2: " + resultado.getDouble("telefone2")+
                "Data-admissão: "+resultado.getDate("data_admissao")+
                "Cargo: "+resultado.getString("cargo")+
                "Salario: "+resultado.getDouble("salario")+
                "Fk_id_pessoa: "+resultado.getInt("fk_id_pessoa")+
                "Fk_id_contrato: "+resultado.getInt("fk_id_contrato")+
                "-_-_-_-_-_-_-_-_-_-_"
        );
        resultado.close();

    }


    private String lerItemOpc(String msg){
        System.out.println(msg);
        String valor = scanner.nextLine();
        return (valor == null || valor.isBlank())? null : valor;
    }

    private String lerItemObg( String msg){

        String valor;
        while(true) {
            System.out.println(msg);
            valor = scanner.nextLine();
            if( valor != null && !(valor.isBlank()) ){
                return valor;
            }
            System.out.println("Este valor é obrigatorio.");
        }
    }
}