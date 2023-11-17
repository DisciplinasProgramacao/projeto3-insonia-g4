import java.io.Serializable;

public class RegistroHistorico implements Serializable{
    private Cliente cliente;
    private String id;// Id do Registro;
    private double tempoEstacionado;// Tempo que o véiculo ficou estacionado;
    private double valorCobranca;// Valor cobrado ao cliente;

    // Constructor;
    public RegistroHistorico(Cliente cliente, String id, double tempoEstacionado, double valorCobranca){
        this.cliente = cliente;
        this.id = id;
        this.tempoEstacionado = tempoEstacionado;
        this.valorCobranca = valorCobranca;
    }

    // Getters;
    public Cliente getCliente(){
		return cliente;
	}
    public String getID(){
        return id;
    }
    public double getTempoEstacionado(){
        return tempoEstacionado;
    }
    public double getValorCobranca(){
        return valorCobranca;
    }
}