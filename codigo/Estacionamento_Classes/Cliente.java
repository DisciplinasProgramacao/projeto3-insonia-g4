import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

enum Modalidade {
    HORISTA, DE_TURNO, MENSALISTA
}

public class Cliente implements Serializable {
    private String nome;// Nome do Cliente;
    private String id;// Id do Cliente;
    private Map<String, Veiculo> veiculos;// Lista de veículos do Cliente;
    private Modalidade modalidade;

    // Construtor com a adição da modalidade;
    public Cliente(String nome, String id, Modalidade modalidade) {
        this.nome = nome;
        this.id = id;
        this.modalidade = modalidade;
        this.veiculos = new HashMap<>();
    }

    // Métodos getters;
    public String getNome() {
        return nome;
    }

    public String getID() {
        return id;
    }

    public Map<String, Veiculo> getVeiculos() {
        return veiculos;
    }

    public Modalidade getModalidade(){
        return modalidade;
    }

    // Método para adicionar veículo com base na modalidade;
    public void addVeiculo(Veiculo veiculo) {
        if (veiculos.containsKey(veiculo.getPlaca())) {
            throw new IllegalArgumentException("Erro - Carro já inserido.");
        }
        veiculos.put(veiculo.getPlaca(), veiculo);
    }

    // Método para calcular o valor a ser cobrado pelo cliente;
    public double calcularCobranca() {
        switch (modalidade) {
            case HORISTA:
                return calcularCobrancaHorista();
            case DE_TURNO:
                return 200; // Mensalidade fixa de R$200;
            case MENSALISTA:
                return 500; // Mensalidade fixa de R$500;
            default:
                throw new IllegalArgumentException("Modalidade inválida");
        }
    }

    // Método para calcular a cobrança para clientes horistas;
    private double calcularCobrancaHorista() {
        double totalArrecadado = 0;
        for (Veiculo veiculo : veiculos.values()) {
            totalArrecadado += veiculo.totalArrecadado();
        }
        return totalArrecadado;
    }

    // Outros métodos permanecem inalterados;

    // Método para verificar se o cliente possui um veículo com uma placa específica;
    public Veiculo possuiVeiculo(String placa) {
        return veiculos.get(placa);
    }

    // Método para obter o total de usos;
    public int totalDeUsos() {
        int totalDeUsos = 0;
        for (Veiculo veiculo : veiculos.values()) {
            totalDeUsos += veiculo.totalDeUsos();
        }
        return totalDeUsos;
    }

    // Método para obter o valor arrecadado por um veículo com uma placa específica;
    public double arrecadadoPorVeiculo(String placa) {
        Veiculo veiculo = veiculos.get(placa);
        if (veiculo == null) {
            throw new IllegalArgumentException("Erro - Veículo não encontrado.");
        }
        return veiculo.totalArrecadado();
    }

    // Método para obter o total arrecadado pelo cliente;
    public double arrecadadoTotal() {
        double totalArrecadado = 0;
        for (Veiculo veiculo : veiculos.values()) {
            totalArrecadado += veiculo.totalArrecadado();
        }
        return totalArrecadado;
    }

    // Método para obter o total arrecadado em um mês;
    public double arrecadadoNoMes(int mes){
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês inválido. Insira um número de mês entre 1 e 12.");
        }

        double totalArrecadadoNoMes = 0;
        
        for (Veiculo veiculo : veiculos.values()) {
            totalArrecadadoNoMes += arrecadadoPorVeiculoNoMes(veiculo, mes);
        }
        return totalArrecadadoNoMes;
    }

    /* Método para obter o valor arrecadado por um veículo com 
    uma placa específica em um mês específico; */
    private double arrecadadoPorVeiculoNoMes(Veiculo veiculo, int mes) {
        double totalArrecadadoNoMes = 0;
        
        /*for (Uso uso : veiculo.getUsos()) {
            Calendar dataUso = Calendar.getInstance();
            dataUso.setTime(uso.getData());
            int mesUso = dataUso.get(Calendar.MONTH) + 1; // Calendar.MONTH é baseado em zero
            if (mesUso == mes) {
                totalArrecadadoNoMes += uso.getValor();
            }
        }*/
        
        return totalArrecadadoNoMes;
    }
}