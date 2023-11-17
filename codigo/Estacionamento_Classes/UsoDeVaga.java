import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

public class UsoDeVaga implements Serializable {
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;

    private Vaga vaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;
    private Servicos servicoContratado;
    private Date data;
    private Cliente cliente;

    // Constructor com a adição do cliente;
    public UsoDeVaga(Vaga vaga, LocalDateTime entrada, int escolha, Cliente cliente) {
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = null;
        this.valorPago = 0.0;
        this.servicoContratado = Servicos.selecionarServico(escolha);
        this.data = new Date();
        this.cliente = cliente;
    }

    // Getters;
    public double getValorPago() {
        return valorPago;
    }

    public int getMes() {
        return entrada.getMonthValue();
    }

    public LocalDateTime getSaida() {
        return this.saida;
    }

    public Date getData() {
        return this.data;
    }

    public Vaga getVaga() {
        return this.vaga;
    }

    // Método para verificar permissão de saída;
    public boolean permissaoSaida(LocalDateTime saida) {
        if (servicoContratado != null
                && Duration.between(entrada, saida).toMinutes() < servicoContratado.getTempoMinimo()) {
            long minutosRestantes = servicoContratado.getTempoMinimo() - Duration.between(entrada, saida).toMinutes();
            System.err.print("Seu veículo ainda está no(a) " + servicoContratado.getNomeDoServico()
                    + "! Ele estará disponível em " + minutosRestantes + " minutos.");
            return false;
        } else {
            return true;
        }
    }

    // Método para sair da vaga;
    public double sair(LocalDateTime saida) {
        this.saida = saida;
        long minutos = Duration.between(entrada, saida).toMinutes();

        if (minutos <= 15) {
            this.valorPago = 0.0;
        } else {
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
}
