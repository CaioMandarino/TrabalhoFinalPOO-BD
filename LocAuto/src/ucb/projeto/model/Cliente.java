package ucb.projeto.model;

import java.util.Date;

public class Cliente extends  Pessoa{
    private int id_cliente;
    private int fk_idPessoa;

    public Cliente(int id_cliente, int id, String CPF, String nome, Date data_nascimento, String endereco, String email) {
        super(id, CPF, nome, data_nascimento, endereco, email);
        this.fk_idPessoa = id;
        this.id_cliente = id_cliente;
    }

    public String verCliente() {
        String data = Data_nascimento.toString();

        return "id_cliente: " + this.id_cliente
                + "; fk_idPessoa: " + this.fk_idPessoa
                + "; CPF: " + this.CPF
                + "; nome: " + this.nome
                + "; Data_nascimento: " + data
                + "; Endereco: " + this.Endereco
                + "; Email: " + this.Email;
    }

}
