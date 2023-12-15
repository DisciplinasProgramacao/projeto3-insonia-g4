import java.text.SimpleDateFormat;
import java.util.*;

public class RelatorioCompleto implements Observer, Serializable {
    private Map<Cliente, Double> top5Clientes;
    private double arrecadacaoMensal;

    public RelatorioCompleto() {
        this.top5Clientes = new LinkedHashMap<>();
        this.arrecadacaoMensal = 0.0;
    }

    public void gerarRelatorio(Veiculo veiculo) {
        veiculo.ordenarUsosPorData();
        System.out.println("Relatório de Usos de Vaga para o Veículo de Placa: " + veiculo.getPlaca());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (int i = 0; i < veiculo.getTotalUsos(); i++) {
            UsoDeVaga uso = veiculo.getUsos()[i];
            Date data = uso.getData();
            String dataFormatada = dateFormat.format(data);
            System.out.println("Data: " + dataFormatada + " - Vaga: " + uso.getVaga().toString());
        }
    }

    @Override
    public void updateArrecadacao(Cliente cliente, double novaArrecadacao) {
        top5Clientes.put(cliente, novaArrecadacao);
        top5Clientes = sortByValue(top5Clientes);

        if (top5Clientes.size() > 5) {
            Iterator<Map.Entry<Cliente, Double>> iterator = top5Clientes.entrySet().iterator();
            Map.Entry<Cliente, Double> entry = null;
            for (int i = 0; i < 5 && iterator.hasNext(); i++) {
                entry = iterator.next();
            }
            if (entry != null) {
                iterator.remove();
            }
        }
        arrecadacaoMensal += novaArrecadacao;
    }

    public Map<Cliente, Double> getTop5Clientes() {
        return top5Clientes;
    }

    public double getArrecadacaoMensal() {
        return arrecadacaoMensal;
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
