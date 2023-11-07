import java.io.Serializable;

public class RegistroHistorico implements Serializable{
    private Cliente cliente;
    private String id;
    private double tempoEstacionado;
    private double valorCobranca;

    //Constructor;
    public RegistroHistorico(Cliente cliente, String id, double tempoEstacionado, double valorCobranca){
        this.cliente = cliente;
        this.id = id;
        this.tempoEstacionado = tempoEstacionado;
        this.valorCobranca = valorCobranca;
    }

    //Getters;
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