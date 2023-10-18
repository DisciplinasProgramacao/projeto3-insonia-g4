import java.io.Serializable;
import java.time.LocalDateTime;

public class Veiculo implements Serializable {
    private String placa;
    private UsoDeVaga[] usos;
    private int totalUsos;

    public Veiculo(String placa) {
        this.placa = placa;
        this.usos = new UsoDeVaga[100];
        this.totalUsos = 0;
    }

    public void estacionar(Vaga vaga) {
        if (totalUsos < usos.length) {
            usos[totalUsos] = new UsoDeVaga(vaga, LocalDateTime.now());
            totalUsos++;
        } else {
            System.out.println("Limite de usos atingido.");
        }
    }

    public String getPlaca(){
        return placa;
    }

    public double sair() {
        // Implemente o cálculo do valor a pagar ao sair de uma vaga aqui
        return 0.0;
    }

    public double totalArrecadado() {
        double total = 0.0;
        for (int i = 0; i < totalUsos; i++) {
            total += usos[i].getValorPago();
        }
        return total;
    }

    public double arrecadadoNoMes(int mes) {
        double totalMes = 0.0;
        for (int i = 0; i < totalUsos; i++) {
            if (usos[i].getMes() == mes) {
                totalMes += usos[i].getValorPago();
            }
        }
        return totalMes;
    }

    public int totalDeUsos() {
        return totalUsos;
    }
}