import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.io.Serializable;

public class UsoDeVaga implements Serializable{

    private static final double FRACAO_USO = 0.25;
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;

    private Vaga vaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;
    private Servicos servicoContratado;

    public UsoDeVaga(Vaga vaga, LocalDateTime entrada) {
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = null;
        this.valorPago = 0.0;
        this.servicoContratado = null;
    }

    public void contratarServico() {

        this.servicoContratado = Servicos.selecionarServico();

    public void selecionarEstacionamento() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Selecione o estacionamento (1, 2 ou 3): ");
        int escolha = scanner.nextInt();
        
        if (escolha < 1 || escolha > 3) {
            throw new IllegalArgumentException("Escolha inválida. Por favor, selecione 1, 2 ou 3.");
        }
        
        this.numeroEstacionamento = escolha;
    }

    public int getNumeroEstacionamento() {
        return numeroEstacionamento;
    }
    }

    public void sair(LocalDateTime saida) {
        if (servicoContratado != null && Duration.between(entrada, saida).toMinutes() < servicoContratado.getTempoMinimo()) {
            long minutosRestantes = servicoContratado.getTempoMinimo() - Duration.between(entrada, saida).toMinutes();
            throw new IllegalStateException("Seu veículo ainda está no(a) " + servicoContratado.getNomeDoServico() + "! Ele estará disponível em " + minutosRestantes + " minutos.");
        }

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

        if (servicoContratado != null) {
            this.valorPago += servicoContratado.getCustoServico();
        }
    }

    public double getValorPago() {
        return valorPago;
    }

    public int getMes() {
        return entrada.getMonthValue();
    }
}
