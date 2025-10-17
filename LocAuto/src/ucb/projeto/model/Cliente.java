package ucb.projeto.model;

import java.util.Date;

public class Cliente extends  Pessoa{
    private int id_cliente;
    private int fk_idPessoa;

    public Cliente(int id, String CPF, String nome, Date data_nascimento, String endereco, String email) {
        super(id, CPF, nome, data_nascimento, endereco, email);
    }
}
