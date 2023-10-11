import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estacionamento {

	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;

	public Estacionamento(String ngitome, int fileiras, int vagasPorFila) {

	}

	public void addVeiculo(Veiculo veiculo, String idCli) {

	}

	public void addCliente(Cliente cliente) {

	}

	private void gerarVagas() {

	}

	public void estacionar(String placa) {

	}

	public double sair(String placa) {

	}

	public double totalArrecadado() {
		double valTotal = 0;
		for (Cliente cliente : id) {
			valTotal += cliente.arrecadadoTotal();
		}
		return valTotal;
	}

	public double arrecadacaoNoMes(int mes) {
		double valArrecadado = 0;
		for (Cliente cliente : id) {
			valArrecadado += cliente.arrecadadoNoMes(mes);
		}
		return valArrecadado;
	}

	public double valorMedioPorUso() {
		double totalArrecadado = 0;
		int totalUsos = 0;
		for (Cliente cliente : id) {
			totalArrecadado += cliente.arrecadadoTotal();
			totalUsos += cliente.totalDeUsos();
		}
		return totalArrecadado / totalUsos;
	}

	public String top5Clientes(int mes) {
		Map<Cliente, Double> gastosPorCliente = new HashMap<>();
		for (Cliente cliente : id) {
			double gastos = cliente.arrecadadoNoMes(mes);
			if (gastos > 0) {
				gastosPorCliente.put(cliente, gastos);
			}
		}

		List<Map.Entry<Cliente, Double>> listaOrdenada = new ArrayList<>(gastosPorCliente.entrySet());
		listaOrdenada.sort(Map.Entry.<Cliente, Double>comparingByValue().reversed());

		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Map.Entry<Cliente, Double> entry : listaOrdenada) {
			if (i >= 5) {
				break;
			}
			sb.append(entry.getKey().getNome());
			sb.append(" - R$ ");
			sb.append(entry.getValue());
			sb.append("\n");
			i++;
		}
		return sb.toString();
	}

}
