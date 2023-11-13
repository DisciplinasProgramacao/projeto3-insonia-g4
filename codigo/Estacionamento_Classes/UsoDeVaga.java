import java.time.Duration;
import java.time.LocalDateTime;
import java.io.Serializable;

public class UsoDeVaga implements Serializable {
    private static final double FRACAO_USO = 0.25;
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;

    private Vaga vaga;
    private LocalDateTime entrada;//Horário de Entrada do Veículo na vaga;
    private LocalDateTime saida;//Horário de Saida do Veículo na vaga;
    private double valorPago;//Valor pago pelo uso da vaga;
    private Servicos servicoContratado;//Tipo de serviço contratado;

    //Constructor;
    public UsoDeVaga(Vaga vaga, LocalDateTime entrada) {
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = null;
        this.valorPago = 0.0;
        this.servicoContratado = null;
    }

    //Getters;
    public double getValorPago() {
        return valorPago;
    }

    public int getMes() {
        return entrada.getMonthValue();
    }

    //Escolher um serviço da classe Servicos;
    public void contratarServico(int escolha) {
        this.servicoContratado = Servicos.selecionarServico(escolha);
    }

    //???;
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

    //Sair da vaga;
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

        return getValorPago();
    }
}