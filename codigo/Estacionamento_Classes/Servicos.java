import java.io.Serializable;

public class Servicos implements Serializable{
    private int idServico;// Id do serviço;
    private String nomeDoServico;// Nome do Serviço;
    private double custoServico;// Custo do Serviço;
    private int tempoMinimo;

    // Constructor;
    public Servicos(int idServico, String nomeDoServico, 
    double custoServico, int tempoMinimo) {
        this.idServico = idServico;
        this.nomeDoServico = nomeDoServico;
        this.custoServico = custoServico;
        this.tempoMinimo = tempoMinimo;
    }

    // Getters;
    public int getIdServico() {
        return idServico;
    }
    public String getNomeDoServico() {
        return nomeDoServico;
    }
    public double getCustoServico() {
        return custoServico;
    }
    public int getTempoMinimo() {
        return tempoMinimo;
    }

    // Função de selecionar o serviço;
    public static Servicos selecionarServico(int escolha){
        // O valor de int escolha decide o serviço;
        switch (escolha) {
            case 0:
                return new Servicos(0, "Nenhum", 0.0, 0);
            case 1:
                return new Servicos(1, "Manobrista", 5.0, 0);
            case 2:
                return new Servicos(2, "Lavagem", 20.0, 60);
            case 3:
                return new Servicos(3, "Polimento", 45.0, 120);
            default:
                throw new IllegalArgumentException("Escolha inválida. Por favor, escolha entre 0 e 3.");
        }
    }
}