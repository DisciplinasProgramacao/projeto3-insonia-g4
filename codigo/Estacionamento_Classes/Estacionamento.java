import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estacionamento{
	private String nome;//Nome do estacionamento;
	private Cliente[] id;//Vetor de clientes no estacionamento;
	private Vaga[] vagas;//Vetor das vagas no estacionamento;
	private int quantFileiras;//Quantidade de fileiras no estacionamento;
	private int vagasPorFileira;//Vagas por fileira;

	//Constructor;
	public Estacionamento(String ngitome, int fileiras, int vagasPorFila){
		this.nome = ngitome;
		this.quantFileiras = fileiras;
		this.vagasPorFileira = vagasPorFila;
		this.id = new Cliente[0];//Inicialmente, nenhum cliente registrado;
		gerarVagas();
	}
	public Estacionamento(){
		this.nome = "";
		this.quantFileiras = 1;
		this.vagasPorFileira = 1;
		this.id = new Cliente[0];
		gerarVagas();
	}

    //Getters;
    public String getNome() {return nome;}
	public int getQuantFileiras() {return quantFileiras;}
	public int getVagasPorFileira() {return vagasPorFileira;}

    //Adicionar veículo no estacionamento;
	public void addVeiculo(Veiculo veiculo, String idCli){
		//Encontrar o cliente com base no id;
		Cliente cliente = null;
		for (Cliente c : id) {
			if (c.getID().equals(idCli)) {
				cliente = c;
				break;
			}
		}
		if (cliente != null) {
			cliente.addVeiculo(veiculo);
		} 
        else {
			throw new IllegalArgumentException("Erro - Cliente não encontrado.");
		}
	}

    //Adicionar cliente no estacionamento;
	public void addCliente(Cliente cliente) {
		//Verificar se o cliente já existe com base no ID;
		for (Cliente c : id) {
			if (c.getID().equals(cliente.getID())) {
				throw new IllegalArgumentException("Erro - Cliente com ID duplicado.");
			}
		}
		//Adicionar o novo cliente à lista de clientes;
		Cliente[] novaLista = new Cliente[id.length + 1];
		for (int i = 0; i < id.length; i++) {
			novaLista[i] = id[i];
		}
		novaLista[novaLista.length - 1] = cliente;
		id = novaLista;
	}

    //Gerar as Vagas do estacionamento;
	private void gerarVagas() {
		if (quantFileiras <= 0 || vagasPorFileira <= 0) {
			throw new IllegalArgumentException(
					"Erro - Quantidade de fileiras e vagas por fileira deve ser maior que zero.");
		}

		int totalVagas = quantFileiras * vagasPorFileira;
		vagas = new Vaga[totalVagas];

		//Criar as vagas;
		for (int i = 0; i < totalVagas; i++) {
			int fila = i / vagasPorFileira + 1;//Calcula a fila com base no número de vagas por fileira;
			int numero = i % vagasPorFileira + 1;//Calcula o número com base no número de vagas por fileira;
			vagas[i] = new Vaga(fila, numero);//Cria uma vaga com fila e número únicos;
		}
	}

    //Estacionar o veículo no estacionamento;
	public void estacionar(String placa){
		Veiculo veiculoParaEstacionar = null;
		Cliente clienteDoVeiculo = null;

		//Procura o cliente por meio do ID;
		for(Cliente cliente : id){
			Veiculo veiculo = cliente.possuiVeiculo(placa);
			//Caso o veículo seja encontrado;
			if(veiculo != null){
				veiculoParaEstacionar = veiculo;
				clienteDoVeiculo = cliente;
				break;
			}
		}

		//Caso o veículo seja encontrado;
		if(veiculoParaEstacionar != null){
			Vaga vagaDisponivel = null;
			for(Vaga vaga : vagas){
				if(vaga.isDisponivel()){
					vagaDisponivel = vaga;
					break;
				}
			}

			//Caso tenha uma vaga disponível;
			if(vagaDisponivel != null){
				if(vagaDisponivel.estacionar(clienteDoVeiculo)){
					LocalDateTime entrada = LocalDateTime.now();//Obtém a hora de entrada do veículo;
					System.out.println("Veículo com placa " + placa + " estacionado na " 
					+ vagaDisponivel.id + " no horário " + entrada);
					//Falta alguma coisa aqui? Essa função apenas imprime uma mensagem?;
				}
				else{
					throw new IllegalArgumentException("Erro - A vaga não está disponível.");
				}
			}
			else{
				throw new IllegalArgumentException("Erro - Não há vagas disponíveis.");
			}
		}
		//Caso o veículo não seja encontrado;
		else{
			throw new IllegalArgumentException("Erro - Veículo não encontrado.");
		}
	}

	/*public double sair(String placa) {
		//Encontra o cliente e o véiculo por meio da placa;
		Cliente clienteDoVeiculo = null;
		Veiculo veiculoParaSair = null;
	
		for (Cliente cliente : id) {
			Veiculo veiculo = cliente.possuiVeiculo(placa);
			if (veiculo != null) {
				clienteDoVeiculo = cliente;
				veiculoParaSair = veiculo;
				break;
			}
		}
	
		if (veiculoParaSair != null) {
			//Calcular a duração e o custo do estacionamento;
			LocalDateTime saida = LocalDateTime.now();//Pega o tempo de saída;
			LocalDateTime entrada = veiculoParaSair.getEntrada();//Pega a hora da entrada;

			Duration duracao = Duration.between(entrada, saida);//Pega a duração (intervalo entre a entrada e saída);
			long minutosEstacionado = duracao.toMinutes();
	
			// You can define your pricing logic here. For example, charge $1 per hour:
			double valorPorMinuto = 1.0 / 60; // $1 per hour is divided by 60 to get per-minute rate
			double custoEstacionamento = valorPorMinuto * minutosEstacionado;
	
			// Remove the vehicle from the client's list of vehicles
			clienteDoVeiculo.removerVeiculo(placa);
	
			// Mark the parking space as available
			for (Vaga vaga : vagas) {
				if (vaga.estacionado(veiculoParaSair)) {
					vaga.liberarVaga();
					break;
				}
			}
	
			// Return the cost of parking for this vehicle
			return custoEstacionamento;
		} else {
			throw new IllegalArgumentException("Erro - Veículo não encontrado.");
		}
	}*/

    //Total arrecadado pelo estacionamento;
	public double totalArrecadado() {
		double valTotal = 0;
		for (Cliente cliente : id) {
			valTotal += cliente.arrecadadoTotal();
		}
		return valTotal;
	}

    //Arrecadado pelo estacionamento em um Mês;
	public double arrecadacaoNoMes(int mes) {
		double valArrecadado = 0;
		for (Cliente cliente : id) {
			valArrecadado += cliente.arrecadadoNoMes(mes);
		}
		return valArrecadado;
	}

	//Média arrecadada;
	public double valorMedioPorUso() {
		double totalArrecadado = 0;
		int totalUsos = 0;
		for (Cliente cliente : id) {
			totalArrecadado += cliente.arrecadadoTotal();
			totalUsos += cliente.totalDeUsos();
		}
		return totalArrecadado / totalUsos;
	}

	//Os 5 clientes que mais gastaram;
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