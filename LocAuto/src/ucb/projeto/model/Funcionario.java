package ucb.projeto.model;
import java.util.Date;

public class Funcionario extends Pessoa {
    private int id_funcionario;
    private Date data_Admissao;
    private String cargo;
    private float salario;
    private int id_contrato;

    public Funcionario(int id, String CPF, String nome, Date data_nascimento, String endereco, String email, int id_contrato, float salario, String cargo, Date data_Admissao, int id_funcionario) {
        super(id, CPF, nome, data_nascimento, endereco, email);
        this.id_contrato = id_contrato;
        this.salario = salario;
        this.cargo = cargo;
        this.data_Admissao = data_Admissao;
        this.id_funcionario = id_funcionario;
    }

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public Date getData_Admissao() {
        return data_Admissao;
    }

    public void setData_Admissao(Date data_Admissao) {
        this.data_Admissao = data_Admissao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }
};
