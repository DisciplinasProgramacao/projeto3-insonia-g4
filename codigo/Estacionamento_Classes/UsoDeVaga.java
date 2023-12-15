import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

public class UsoDeVaga implements Serializable, UsoDeVagaPrototype, Comparable<UsoDeVaga>{
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;

    private Vaga vaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;
    private Servicos servicoContratado;
    private Date data;
    private Cliente cliente;
    private Modalidade modalidadeCliente;// Adicionando a modalidade do cliente;

    // Constructor com a adição da modalidade do cliente;
    public UsoDeVaga(Vaga vaga, LocalDateTime entrada, int escolha, Cliente cliente){
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = null;
        this.valorPago = 0.0;
        this.servicoContratado = Servicos.selecionarServico(escolha);
        this.data = new Date();
        this.cliente = cliente;
        this.modalidadeCliente = cliente.getModalidade();// Setando a modalidade do cliente;
    }

    // Getters;
    public LocalDateTime getEntrada(){return this.entrada;}

    public LocalDateTime getSaida(){return this.saida;}

    public double getValorPago(){return this.valorPago;}//mudou isso;

    public int getMes(){return this.entrada.getMonthValue();}//mudou isso;

    public Date getData(){return this.data;}

    public Vaga getVaga(){return this.vaga;}

    public Cliente getCliente(){return this.cliente;}

    // Método para verificar permissão de saída;
    public boolean permissaoSaida(LocalDateTime saida) {
        if (servicoContratado != null && Duration.between(entrada, saida).toMinutes() < servicoContratado.getTempoMinimo()) {
            long minutosRestantes = servicoContratado.getTempoMinimo() - Duration.between(entrada, saida).toMinutes();
            System.err.println("Seu veiculo ainda esta no(a) " + servicoContratado.getNomeDoServico()
            + "! Ele estara disponivel em " + minutosRestantes + " minutos.");
            return false;
        } 
        else{return true;}
    }

    // Método para sair da vaga;
    public double sair(LocalDateTime saida){
        this.saida = saida;
        long minutos = Duration.between(entrada, saida).toMinutes();
        // Ficou estacionado por menos de 15 minutos;
        if (minutos <= 15) {
            this.valorPago = 0.0;
        } 
        else {
            int horas = (int) Math.ceil((double) minutos / 60);
            if (horas <= 1) {
                this.valorPago = VALOR_FRACAO;
            } else if (horas >= 9) {
                this.valorPago = VALOR_MAXIMO;
            } else {
                this.valorPago = VALOR_FRACAO + (horas - 1) * VALOR_FRACAO;
            }
        }
        
        if (servicoContratado != null) {
            this.valorPago += servicoContratado.getCustoServico();
        }
        // Ajuste para calcular a cobrança com base na modalidade do cliente;
        this.valorPago += cliente.calcularCobranca();
        return getValorPago();
    }

    @Override
    public int compareTo(UsoDeVaga other) {
        return this.data.compareTo(other.data);
    }

    //**********Prototype (em Construção)***********/
    // Implementação do método clonar() para criar uma cópia do objeto UsoDeVaga;
    @Override
    public UsoDeVaga clonar(){
        UsoDeVaga copia = new UsoDeVaga(this.vaga, this.entrada, 0, this.cliente);
        copia.vaga = this.vaga;
        copia.entrada = this.entrada;
        copia.saida = this.saida;
        copia.valorPago = this.valorPago;
        copia.servicoContratado = this.servicoContratado;
        copia.data = this.data;
        copia.cliente = this.cliente;
        copia.modalidadeCliente = this.modalidadeCliente;
        return copia;
    }
}