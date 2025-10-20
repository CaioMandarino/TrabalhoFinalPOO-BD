package ucb.projeto.model;

public class Carro {
    private int Id_Carro;
    private String placa;
    private float Quilometragem;
    private String Cor;
    private String Status;
    private int AnoFabricacao;
    private String Nome;

    public int getId_Carro() {
        return Id_Carro;
    }

    public void setId_Carro(int id_Carro) {
        Id_Carro = id_Carro;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public float getQuilometragem() {
        return Quilometragem;
    }

    public void setQuilometragem(float quilometragem) {
        Quilometragem = quilometragem;
    }

    public String getCor() {
        return Cor;
    }

    public void setCor(String cor) {
        Cor = cor;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getAnoFabricacao() {
        return AnoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        AnoFabricacao = anoFabricacao;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Carro(int id_Carro, String placa, float quilometragem, String cor, String status, int anoFabricacao, String nome) {
        Id_Carro = id_Carro;
        this.placa = placa;
        Quilometragem = quilometragem;
        Cor = cor;
        Status = status;
        AnoFabricacao = anoFabricacao;
        Nome = nome;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "Id_Carro=" + Id_Carro +
                ", placa='" + placa + '\'' +
                ", Quilometragem=" + Quilometragem +
                ", Cor='" + Cor + '\'' +
                ", Status='" + Status + '\'' +
                ", AnoFabricacao=" + AnoFabricacao +
                ", Nome='" + Nome + '\'' +
                '}';
    }
    public String verCarro() {
        return String.format(
                "ID: %d%n" +
                        "Placa: %s%n" +
                        "Quilometragem: %.2f km%n" +
                        "Cor: %s%n" +
                        "Status: %s%n" +
                        "Ano de Fabricação: %d%n" +
                        "Nome: %s%n" +
                        "-----------------------------",
                Id_Carro, placa, Quilometragem, Cor, Status, AnoFabricacao, Nome
        );
    }

}
