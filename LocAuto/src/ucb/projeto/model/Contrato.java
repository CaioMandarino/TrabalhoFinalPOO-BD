package ucb.projeto.model;

public class Contrato{
    private Funcionario funcionario;
    private Seguradora seguradora;
    private Carro carro;
    private Cliente cliente;

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Seguradora getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Contrato(Funcionario funcionario, Seguradora seguradora, Carro carro, Cliente cliente) {
        this.funcionario = funcionario;
        this.seguradora = seguradora;
        this.carro = carro;
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "funcionario=" + funcionario +
                ", seguradora=" + seguradora +
                ", carro=" + carro +
                ", cliente=" + cliente +
                '}';
    }
}
