import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estacionamento implements Serializable{
	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;
	private List<Observer> observers;

	// Construtor;
	public Estacionamento(String nome, int fileiras, int vagasPorFila){
		this.nome = nome;
		this.quantFileiras = fileiras;
		this.vagasPorFileira = vagasPorFila;
		this.id = new Cliente[0];
		this.observers = new ArrayList<>();
		gerarVagas();
	}

	// Getters;
	public String getNome(){return nome;}
	public int getQuantFileiras(){return quantFileiras;}
	public int getVagasPorFileira(){return vagasPorFileira;}

	// Adiciona Observer;
	public void addObserver(Observer observer){observers.add(observer);}

	// Remove Observer; 
	public void removeObserver(Observer observer){observers.remove(observer);}

	// Notifica o Observer;
	public void notifyObservers(Cliente cliente, double novaArrecadacao){
		for(Observer observer : observers){
			observer.updateArrecadacao(cliente, novaArrecadacao);
		}
	}

	// Adicionar veículo no estacionamento;
	public void addVeiculo(Veiculo veiculo, String idCli){
		// Encontrar o cliente com base no id;
		Cliente cliente = null;
		for(Cliente c : id){
			if(c.getID().equals(idCli)){
				cliente = c;
				break;
			}
		}
		if(cliente != null){cliente.addVeiculo(veiculo);} 
		else{MyIO.println("Erro - Cliente nao encontrado.");}
	}

	// Adiciona Cliente no Estacionamento;
	public void addCliente(Cliente cliente){
		Cliente[] novaLista = new Cliente[id.length + 1];
		System.arraycopy(id, 0, novaLista, 0, id.length);
		novaLista[novaLista.length - 1] = cliente;
		id = novaLista;
		// Adiciona o Relatório como um observer ao adicionar um cliente;
		addObserver(new Relatorio());
	}

	// Gerar as Vagas do estacionamento;
	private void gerarVagas(){
		int totalVagas = quantFileiras * vagasPorFileira;
		vagas = new Vaga[totalVagas];
		// Criar as vagas;
		for(int i = 0; i < totalVagas; i++){
			int fila = i / vagasPorFileira + 1;// Calcula a fila com base no número de vagas por fileira;
			int numero = i % vagasPorFileira + 1;// Calcula o número com base no número de vagas por fileira;
			vagas[i] = new Vaga(fila, numero);// Cria uma vaga com fila e número únicos;
		}
	}

	// Estacionar Carro do Cliente;
	/*public void estacionarCarroDoCliente(Cliente cliente, String placa){
		List<Veiculo> veiculos = cliente.getVeiculosAsList();
		if(cliente.getVeiculos() != null){
			for(Veiculo veiculo : veiculos){
				if(veiculo.getPlaca().equals(placa)){
					estacionar(placa);
					return;
				}
			}
			throw new IllegalArgumentException("Erro - Cliente não possui veículo com a placa " + placa);
		} else {
			throw new IllegalArgumentException("Erro - Cliente não possui veículo.");
		}
	}*/
	
	// Estacionar o veículo no estacionamento;
	public boolean estacionar(Veiculo veiculo, Estacionamento estacionamento, 
	Cliente cliente, Vaga vaga){
		// Tentativa de estacionar um veículo que já está estacionado (notificando e ignorando a operação);
		UsoDeVaga[] usosVeiculo = veiculo.getUsos();
		int totalUsosVeiculo = veiculo.getTotalUsos();
		if (totalUsosVeiculo > 0) {
			UsoDeVaga UltimoUso = usosVeiculo[totalUsosVeiculo - 1];
			LocalDateTime saida = UltimoUso.getSaida();
			if (saida == null) {
				MyIO.println("Veiculo com placa " + veiculo.getPlaca() + " ja esta estacionado.");
				return false;
			}	
		}
		// Verifica se a Vaga selecionada está disponível;
		Vaga vagaDisponivel = null;
		for(Vaga vaga1 : vagas){
			if (vaga1.getId().equals(vaga.getId()) 
			&& vaga1.isDisponivel()){
				vagaDisponivel = vaga1;
				break;
			}
		}
		if(vagaDisponivel == null){
			MyIO.println("Erro - A vaga nao esta disponivel.");
			return false;
		}
		else{
			if(vagaDisponivel.estacionar(cliente)){
				LocalDateTime entrada = LocalDateTime.now(); // Obtém a hora de entrada do veículo;
				MyIO.println("Veiculo com placa " + veiculo.getPlaca() + " estacionado na "
				+ vagaDisponivel.getId() + " no horario " + entrada);
				return true;
			} 
			else{
				MyIO.println("Erro - A vaga nao esta disponivel.");
				return false;
			}
		}
	}

	// Tirar o veículo da vaga no estacionamento;
	public double sair(Veiculo veiculo, Cliente cliente, Vaga vaga){
		//IMPLEMENTAR O SISTEMA DE TEMPO Mínimo;
		// Tentativa de sair com um carro que não está estacionado (notificando e ignorando a operação);
		UsoDeVaga[] usosVeiculo = veiculo.getUsos();
		int totalUsosVeiculo = veiculo.getTotalUsos();
		if(totalUsosVeiculo > 0){
			UsoDeVaga UltimoUso = usosVeiculo[totalUsosVeiculo - 1];
			LocalDateTime saida = UltimoUso.getSaida();
			if (saida != null) {
				MyIO.println("Veiculo com placa " + veiculo.getPlaca() + " não está estacionado.");
				return 0.0;
			}	
		}
		// Verifica as Vagas;
		Vaga vagaOcupada = null;
		for (Vaga vaga1 : vagas) {
			if (!vaga1.isDisponivel() && vaga1.getId().equals(vaga.getId())
			&& vaga1.getUsuario().getNome().equals(cliente.getNome())){
				vagaOcupada = vaga1;
				break;
			}
		}
		// Caso tenha uma vaga ocupada;
		if (vagaOcupada != null) {
			if (vagaOcupada.sair()) {
				LocalDateTime saida = LocalDateTime.now();// Obtém a hora de saída do veículo;
				System.out.println("Veiculo com placa " + veiculo.getPlaca() + " saiu da " + vagaOcupada.id + " no horario " + saida);
				UsoDeVaga uso = new UsoDeVaga(vagaOcupada, vagaOcupada.getEntrada(), cliente.getEscolha(), cliente);
				uso.sair(saida);
				return uso.getValorPago();
			}
			else{
				MyIO.println("Erro - A vaga nao esta ocupada.");
			}
		} 
		else{MyIO.println("Erro - Nao ha vagas ocupadas.");}
		return 0.0;
	}

	//***********Código para os relátorios***********

	// Arrecadação média dos Clientes Horistas;
	public double arrecadacaoMediaClientesHoristas(int mes){
		double totalArrecadadoHoristas = 0;
		int totalClientesHoristas = 0;

		for(Cliente cliente : id){
			if(cliente.getModalidade() == Modalidade.HORISTA){
				totalArrecadadoHoristas += cliente.arrecadadoNoMes(mes);
				totalClientesHoristas++;
			}
		}

		// Evita a divisão por zero;
		if(totalClientesHoristas > 0){
			return totalArrecadadoHoristas / totalClientesHoristas;
		}
		else{return 0;}
	}

	// Total arrecadado pelo estacionamento;
	public double totalArrecadado(){
		double valTotal = 0;
		for(Cliente cliente : id){
			valTotal += cliente.arrecadadoTotal();
		}
		return valTotal;
	}

	// Arrecadado pelo estacionamento em um Mês;
	public double arrecadacaoNoMes(int mes){
		double valArrecadado = 0;
		for(Cliente cliente : id){
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
			MyIO.println("Nenhum cliente teve arrecadação no mês corrente.");
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
