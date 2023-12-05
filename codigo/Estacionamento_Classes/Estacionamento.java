import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estacionamento implements Serializable {
	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;
	private List<Observer> observers;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.nome = nome;
		this.quantFileiras = fileiras;
		this.vagasPorFileira = vagasPorFila;
		this.id = new Cliente[0];
		this.observers = new ArrayList<>();
		gerarVagas();
	}

	// Getters;
	public String getNome() {
		return nome;
	}

	public int getQuantFileiras() {
		return quantFileiras;
	}

	public int getVagasPorFileira() {
		return vagasPorFileira;
	}

	// Adicionar veículo no estacionamento;
	public void addVeiculo(Veiculo veiculo, String idCli) {
		// Encontrar o cliente com base no id;
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

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	public void notifyObservers(Cliente cliente, double novaArrecadacao) {
		for (Observer observer : observers) {
			observer.updateArrecadacao(cliente, novaArrecadacao);
		}
	}

	public void addCliente(Cliente cliente) {
		for (Cliente c : id) {
			if (c.getID().equals(cliente.getID())) {
				throw new IllegalArgumentException("Erro - Cliente com ID duplicado.");
			}
		}
		Cliente[] novaLista = new Cliente[id.length + 1];
		System.arraycopy(id, 0, novaLista, 0, id.length);
		novaLista[novaLista.length - 1] = cliente;
		id = novaLista;

		addObserver(new Relatorio()); // Adiciona o Relatório como um observer ao adicionar um cliente
	}

	public double arrecadacaoMediaClientesHoristas(int mes) {
		double totalArrecadadoHoristas = 0;
		int totalClientesHoristas = 0;

		for (Cliente cliente : id) {
			if (cliente.getModalidade() == Modalidade.HORISTA) {
				totalArrecadadoHoristas += cliente.arrecadadoNoMes(mes);
				totalClientesHoristas++;
			}
		}

		// Evita a divisão por zero;
		if (totalClientesHoristas > 0) {
			return totalArrecadadoHoristas / totalClientesHoristas;
		} else {
			return 0;
		}
	}

	// Gerar as Vagas do estacionamento;
	private void gerarVagas() {
		if (quantFileiras <= 0 || vagasPorFileira <= 0) {
			throw new IllegalArgumentException(
					"Erro - Quantidade de fileiras e vagas por fileira deve ser maior que zero.");
		}

		int totalVagas = quantFileiras * vagasPorFileira;
		vagas = new Vaga[totalVagas];

		// Criar as vagas;
		for (int i = 0; i < totalVagas; i++) {
			int fila = i / vagasPorFileira + 1;// Calcula a fila com base no número de vagas por fileira;
			int numero = i % vagasPorFileira + 1;// Calcula o número com base no número de vagas por fileira;
			vagas[i] = new Vaga(fila, numero);// Cria uma vaga com fila e número únicos;
		}
	}

	public void estacionarCarroDoCliente(Cliente cliente, String placa) {
		List<Veiculo> veiculos = cliente.getVeiculosAsList();
		if (cliente.getVeiculos() != null) {
			for (Veiculo veiculo : veiculos) {
				if (veiculo.getPlaca().equals(placa)) {
					estacionar(placa);
					return;
				}
			}
			throw new IllegalArgumentException("Erro - Cliente não possui veículo com a placa " + placa);
		} else {
			throw new IllegalArgumentException("Erro - Cliente não possui veículo.");
		}
	}

	// Estacionar o veículo no estacionamento;
	public void estacionar(String placa) {
		Veiculo veiculoParaEstacionar = null;
		Cliente clienteDoVeiculo = null;

		// Procura o cliente por meio do ID;
		for (Cliente cliente : id) {
			Veiculo veiculo = cliente.possuiVeiculo(placa);

			// Caso o veículo seja encontrado;
			if (veiculo != null) {
				veiculoParaEstacionar = veiculo;
				clienteDoVeiculo = cliente;
				break;
			}
		}

		// Caso o veículo seja encontrado;
		if (veiculoParaEstacionar != null) {
			Vaga vagaDisponivel = null;
			for (Vaga vaga : vagas) {
				if (vaga.isDisponivel()) {
					vagaDisponivel = vaga;
					break;
				}
			}

			// Caso tenha uma vaga disponível;
			if (vagaDisponivel != null) {
				if (vagaDisponivel.estacionar(clienteDoVeiculo)) {
					LocalDateTime entrada = LocalDateTime.now();// Obtém a hora de entrada do veículo;
					System.out.println("Veículo com placa " + placa + " estacionado na "
							+ vagaDisponivel.id + " no horário " + entrada);
					vagaDisponivel.estacionar(clienteDoVeiculo);

				} else {
					throw new IllegalArgumentException("Erro - A vaga não está disponível.");
				}
			} else {
				throw new IllegalArgumentException("Erro - Não há vagas disponíveis.");
			}
		}
		// Caso o veículo não seja encontrado;
		else {
			throw new IllegalArgumentException("Erro - Veículo não encontrado.");
		}
	}

	// Tirar o veículo da vaga no estacionamento;
	public double sair(String placa, int escolha) {
		Veiculo veiculoParaSair = null;
		Cliente clienteDoVeiculo = null;

		// Procura o cliente por meio do ID;
		for (Cliente cliente : id) {
			Veiculo veiculo = cliente.possuiVeiculo(placa);
			// Caso o veículo seja encontrado;
			if (veiculo != null) {
				veiculoParaSair = veiculo;
				clienteDoVeiculo = cliente;
				break;
			}
		}

		// Caso o veículo seja encontrado;
		if (veiculoParaSair != null) {
			Vaga vagaOcupada = null;
			for (Vaga vaga : vagas) {
				if (!vaga.isDisponivel() && vaga.getUsuario().getNome().equals(clienteDoVeiculo.getNome())) {
					vagaOcupada = vaga;
					break;
				}
			}

			// Caso tenha uma vaga ocupada;
			if (vagaOcupada != null) {
				if (vagaOcupada.sair()) {
					LocalDateTime saida = LocalDateTime.now();// Obtém a hora de saída do veículo;
					System.out.println("Veículo com placa " + placa + " saiu da "
							+ vagaOcupada.id + " no horário " + saida);
					UsoDeVaga uso = new UsoDeVaga(vagaOcupada, vagaOcupada.getEntrada(), escolha, clienteDoVeiculo);
					uso.sair(saida);
					return uso.getValorPago();
				} else {
					throw new IllegalArgumentException("Erro - A vaga não está ocupada.");
				}
			} else {
				throw new IllegalArgumentException("Erro - Não há vagas ocupadas.");
			}
		}

		// Caso o veículo não seja encontrado;
		else {
			throw new IllegalArgumentException("Erro - Veículo não encontrado.");
		}
	}

	// Total arrecadado pelo estacionamento;
	public double totalArrecadado() {
		double valTotal = 0;
		for (Cliente cliente : id) {
			valTotal += cliente.arrecadadoTotal();
		}
		return valTotal;
	}

	// Arrecadado pelo estacionamento em um Mês;
	public double arrecadacaoNoMes(int mes) {
		double valArrecadado = 0;
		for (Cliente cliente : id) {
			valArrecadado += cliente.arrecadadoNoMes(mes);
		}
		return valArrecadado;
	}

	// Media dos clientes mensalisatas no mes corrente;
	public double mediaClientesMensal() {
		int mesCorrente = LocalDateTime.now().getMonthValue();
		double somaArrecadacao = 0.0;
		int contadorClientes = 0;

		for (Cliente cliente : id) {
			double arrecadacaoNoMes = cliente.arrecadadoNoMes(mesCorrente);
			if (arrecadacaoNoMes > 0) {
				somaArrecadacao += arrecadacaoNoMes;
				contadorClientes++;
			}
		}

		if (contadorClientes > 0) {
			return somaArrecadacao / contadorClientes;
		} else {
			System.out.println("Nenhum cliente teve arrecadação no mês corrente.");
			return 0.0;
		}
	}

	// Média arrecadada;
	public double valorMedioPorUso() {
		double totalArrecadado = 0;
		int totalUsos = 0;
		for (Cliente cliente : id) {
			totalArrecadado += cliente.arrecadadoTotal();
			totalUsos += cliente.totalDeUsos();
		}
		return totalArrecadado / totalUsos;
	}

	// Os 5 clientes que mais gastaram;
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
