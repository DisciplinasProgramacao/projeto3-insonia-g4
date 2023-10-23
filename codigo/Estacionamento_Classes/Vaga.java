import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Vaga implements Serializable{

    protected String id;
    private boolean disponivel;
    private Cliente cliente; // Representa o cliente que está ocupando a vaga no momento
    private List<RegistroHistorico> historico; // Para manter o histórico de uso da vaga
    private long entradaMillis; // Momento em que o cliente estacionou na vaga

    public Vaga(int fila, int numero) {
        this.id = "Vaga " + fila + numero;
        this.disponivel = true;
        this.cliente = null;
        this.historico = new ArrayList<>();
        this.entradaMillis = 0;
    }

    public boolean estacionar(Cliente cliente) {
        if (disponivel) {
            this.cliente = cliente;
            this.disponivel = false;
            this.entradaMillis = System.currentTimeMillis();
            return true;
        } else {
            return false; // A vaga já está ocupada
        }
    }

    public boolean sair() {
        if (!disponivel) {
            // Calcular o tempo em que o cliente ficou estacionado e calcular a cobrança
            double tempoEstacionado = calcularTempoEstacionado(); 
            double valorCobranca = calcularValorCobranca(tempoEstacionado); 

            // Registre o uso da vaga no histórico
            RegistroHistorico registro = new RegistroHistorico(cliente, id, tempoEstacionado, valorCobranca);
            historico.add(registro);

            this.cliente = null;
            this.disponivel = true;
            return true;
        } else {
            return false; // A vaga já está vazia
        }
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public List<RegistroHistorico> getHistorico() {
        return historico;
    }

    

    private double calcularTempoEstacionado() {
        if (cliente == null){
            return 0.0;}
             long tempoSaidaMillis = System.currentTimeMillis();

            double tempoEstacionado = (tempoSaidaMillis - entradaMillis) / (1000.0 * 60.0); // Calcula o tempo estacionado em minutos

            return tempoEstacionado;
    }

    double calcularValorCobranca(double tempoEstacionado) {
        // Valor por fração de tempo (15 minutos), Limite máximo de cobrança, 
        // Calcular o número de frações de tempo (15 minutos) arredondando para cima, 
		// Calcular o valor da cobrança, Verificar se o valor ultrapassou o limite
    double valorPorFracao = 4.0; 
    double limiteCobranca = 50.0;
    int numFracoes = (int) Math.ceil(tempoEstacionado / 15.0);
    double valorCobranca = numFracoes * valorPorFracao;
    

    if (valorCobranca > limiteCobranca) {
        valorCobranca = limiteCobranca;
    }
    
    return valorCobranca;
    }
}
