import java.util.Scanner;

public class Servicos {
    private int idServico;
    private String nomeDoServico;
    private double custoServico;
    private int tempoMinimo;

    // Construtor da classe
    public Servicos(int idServico, String nomeDoServico, double custoServico, int tempoMinimo) {
        this.idServico = idServico;
        this.nomeDoServico = nomeDoServico;
        this.custoServico = custoServico;
        this.tempoMinimo = tempoMinimo;
    }

    // Método estático para selecionar um serviço
    public static Servicos selecionarServico() {
        // Exibe um menu para o usuário
        System.out.println("Escolha o serviço (0 a 3): ");
        System.out.println("0 - Nenhum");
        System.out.println("1 - Manobrista");
        System.out.println("2 - Lavagem");
        System.out.println("3 - Polimento");

        // Lê a escolha do usuário a partir do console
        Scanner scanner = new Scanner(System.in);
        int escolha = scanner.nextInt();

        // Com base na escolha do usuário, cria e retorna uma instância da classe Servicos
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

    // Métodos "getters" para obter os valores dos atributos
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
}
