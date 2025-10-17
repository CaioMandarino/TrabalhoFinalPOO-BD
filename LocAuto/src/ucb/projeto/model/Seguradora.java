package ucb.projeto.model;

public class Seguradora {
    private int id_seguradora;
    private String cnpj;
    private String RazaoSocial;

    public int getId_seguradora() {
        return id_seguradora;
    }

    public void setId_seguradora(int id_seguradora) {
        this.id_seguradora = id_seguradora;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        RazaoSocial = razaoSocial;
    }

    public Seguradora(int id_seguradora, String cnpj, String razaoSocial) {
        this.id_seguradora = id_seguradora;
        this.cnpj = cnpj;
        RazaoSocial = razaoSocial;
    }

    @Override
    public String toString() {
        return "Seguradora{" +
                "id_seguradora=" + id_seguradora +
                ", cnpj='" + cnpj + '\'' +
                ", RazaoSocial='" + RazaoSocial + '\'' +
                '}';
    }


}
