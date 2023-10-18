import java.time.LocalDateTime;
import java.time.Duration;

public class UsoDeVaga {

    private static final double FRACAO_USO = 0.25;
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;

    private Vaga vaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;

    public UsoDeVaga(Vaga vaga, LocalDateTime entrada) {
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = null;
        this.valorPago = 0.0;
    }

    public void sair(LocalDateTime saida) {
        this.saida = saida;
        long minutos = Duration.between(entrada, saida).toMinutes();
        
        if (minutos <= 15) {
            this.valorPago = 0.0;
        } else {
            int horas = (int)Math.ceil((double)minutos / 60);
            if (horas <= 1) {
                this.valorPago = VALOR_FRACAO;
            } else if (horas >= 9) {
                this.valorPago = VALOR_MAXIMO;
            } else {
                this.valorPago = VALOR_FRACAO + (horas - 1) * VALOR_FRACAO;
            }
        }
    }

    public double getValorPago() {
        return valorPago;
    }

    public int getMes() {
        return entrada.getMonthValue();
    }
}
