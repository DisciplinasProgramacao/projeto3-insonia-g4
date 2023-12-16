import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class Veiculo implements Serializable{
    private String placa;// Placa do veículo;
    private UsoDeVaga[] usos;// Lista de usos do veículo;
    private int totalUsos;// Total de usos do veículo;

    // Construtor;
    public Veiculo(String placa) {
        this.placa = placa;
        this.usos = new UsoDeVaga[100];
        this.totalUsos = 0;
    }

    // Getters;
    public String getPlaca(){return this.placa;}

    public UsoDeVaga[] getUsos(){return this.usos;}

    public int getTotalUsos(){return this.totalUsos;}

    // Método para ordenar usos de vaga por data;
    public void ordenarUsosPorData() {
        Arrays.sort(usos, 0, totalUsos, Comparator.naturalOrder());
    }

    // Método para ordenar usos de vaga por valor;
    public void ordenarUsosPorValor(){
        Arrays.sort(usos, 0, totalUsos, new Comparator<UsoDeVaga>() {
            @Override
            public int compare(UsoDeVaga uso1, UsoDeVaga uso2) {
                return Double.compare(uso2.getValorPago(), uso1.getValorPago());
            }
        });
    }

    // Estacionar o veículo;
    public void estacionar(Vaga vaga, int escolha, Cliente cliente){
        if(totalUsos < usos.length){
            usos[totalUsos] = new UsoDeVaga(vaga, LocalDateTime.now(), escolha, cliente);
            totalUsos++;
        } else {
            System.out.println("Limite de usos atingido.");
        }
    }

    //***********Código para os relátorios***********

    // Calcula o total arrecadado pelo veículo;
    public double totalArrecadado(){
        double total = 0.0;
        ordenarUsosPorData();// Ordena os usos por data;
        for (int i = 0; i < totalUsos; i++) {
            total += usos[i].getValorPago();
        }
        return total;
    }

    // Calcula o total arrecadado pelo veículo em um mês;
    public double arrecadadoNoMes(int mes) {
        double totalMes = 0.0;
        ordenarUsosPorData();// Ordena os usos por data;
        for (int i = 0; i < totalUsos; i++) {
            if (usos[i].getMes() == mes) {
                totalMes += usos[i].getValorPago();
            }
        }
        return totalMes;
    }
}