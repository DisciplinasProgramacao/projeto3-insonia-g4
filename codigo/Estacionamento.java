import java.time.LocalDateTime;
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

	// CONSTRUTORES
	public Estacionamento(String ngitome, int fileiras, int vagasPorFila) {
		this.nome = ngitome;
		this.quantFileiras = fileiras;
		this.vagasPorFileira = vagasPorFila;
		this.id = new Cliente[0]; // Inicialmente, nenhum cliente registrado
		gerarVagas();
	}

	public Estacionamento() {
		this.nome = "";
		this.quantFileiras = 1;
		this.vagasPorFileira = 1;
		this.id = new Cliente[0];
		gerarVagas();
	}

	public void addVeiculo(Veiculo veiculo, String idCli) {
		// Encontrar o cliente com base no id
		Cliente cliente = null;
		for (Cliente c : id) {
			if (c.getID().equals(idCli)) {
				cliente = c;
				break;
			}
		}
		if (cliente != null) {
			cliente.addVeiculo(veiculo);
		} else {
			throw new IllegalArgumentException("Erro - Cliente não encontrado.");
		}
	}

	public void addCliente(Cliente cliente) {
		// Verificar se o cliente já existe com base no ID
		for (Cliente c : id) {
			if (c.getID().equals(cliente.getID())) {
				throw new IllegalArgumentException("Erro - Cliente com ID duplicado.");
			}
		}

		// Adicionar o novo cliente à lista de clientes
		Cliente[] novaLista = new Cliente[id.length + 1];
		for (int i = 0; i < id.length; i++) {
			novaLista[i] = id[i];
		}
		novaLista[novaLista.length - 1] = cliente;
		id = novaLista;
	}


	private void gerarVagas() {
		if (quantFileiras <= 0 || vagasPorFileira <= 0) {
			throw new IllegalArgumentException(
				"Erro - Quantidade de fileiras e vagas por fileira deve ser maior que zero.");
		}
	
		int totalVagas = quantFileiras * vagasPorFileira;
		vagas = new Vaga[totalVagas];
	
		// Criar as vagas
		for (int i = 0; i < totalVagas; i++) {
			int fila = i / vagasPorFileira + 1; // Calcula a fila com base no número de vagas por fileira
			int numero = i % vagasPorFileira + 1; // Calcula o número com base no número de vagas por fileira
			vagas[i] = new Vaga(fila, numero); // Cria uma vaga com fila e número únicos
		}
	}

	
	public void estacionar(String placa) {
		Veiculo veiculoParaEstacionar = null;
		Cliente clienteDoVeiculo = null;

		for (Cliente cliente : id) {
			Veiculo veiculo = cliente.possuiVeiculo(placa);
			if (veiculo != null) {
				veiculoParaEstacionar = veiculo;
				clienteDoVeiculo = cliente;
				break;
			}
		}

		if (veiculoParaEstacionar != null) {
			Vaga vagaDisponivel = null;
			for (Vaga vaga : vagas) {
				if (vaga.isDisponivel()) {
					vagaDisponivel = vaga;
					break;
				}
			}

			if (vagaDisponivel != null) {
				if (vagaDisponivel.estacionar(clienteDoVeiculo)) {
					LocalDateTime entrada = LocalDateTime.now(); // Obtém a hora de entrada do veículo
					System.out.println("Veículo com placa " + placa + " estacionado na vaga " + vagaDisponivel.id);

				} else {
					throw new IllegalArgumentException("Erro - A vaga não está disponível.");
				}
			} else {
				throw new IllegalArgumentException("Erro - Não há vagas disponíveis.");
			}
		} else {
			throw new IllegalArgumentException("Erro - Veículo não encontrado.");
		}
	}







public boolean existeVagaDisponivel() {
        for (Vaga vaga : vagas) {
            if (vaga.isDisponivel()) {
                return true;
            }
        }
        return false;
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
