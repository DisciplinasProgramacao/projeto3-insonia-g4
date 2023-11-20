import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private String nome;
    private Cliente[] id;
    private Vaga[] vagas;
    private int quantFileiras;
    private int vagasPorFileira;

    public Estacionamento(String nome, int fileiras, int vagasPorFila) {
        this.nome = nome;
        this.quantFileiras = fileiras;
        this.vagasPorFileira = vagasPorFila;
        this.id = new Cliente[0];
        gerarVagas();
    }

    public void addCliente(Cliente cliente) {
        for (Cliente c : id) {
            if (c.getID().equals(cliente.getID())) {
                throw new IllegalArgumentException("Erro - Cliente com ID duplicado.");
            }
        }
        Cliente[] novaLista = new Cliente[id.length + 1];
        for (int i = 0; i < id.length; i++) {
            novaLista[i] = id[i];
        }
        novaLista[novaLista.length - 1] = cliente;
        id = novaLista;
    }

    public void estacionar(String placa, int escolha, String idCliente) {
        Cliente cliente = null;
        for (Cliente c : id) {
            if (c.getID().equals(idCliente)) {
                cliente = c;
                break;
            }
        }
        if (cliente != null) {
            Veiculo veiculo = cliente.possuiVeiculo(placa);
            if (veiculo != null) {
                Vaga vagaDisponivel = null;
                for (Vaga vaga : vagas) {
                    if (vaga.isDisponivel()) {
                        vagaDisponivel = vaga;
                        break;
                    }
                }
                if (vagaDisponivel != null) {
                    UsoDeVaga usoDeVaga = new UsoDeVaga(vagaDisponivel, LocalDateTime.now(), escolha, cliente);
			if(vagaDisponivel != null){
				if(vagaDisponivel.estacionar(clienteDoVeiculo)){
					LocalDateTime entrada = LocalDateTime.now();// Obtém a hora de entrada do veículo;
					System.out.println("Veículo com placa " + placa + " estacionado na " 
					+ vagaDisponivel.id + " no horário " + entrada);
					// Falta alguma coisa aqui? Essa função apenas imprime uma mensagem?;
				 } else {
                    throw new IllegalArgumentException("Erro - Não há vagas disponíveis.");
                }
            } else {
                throw new IllegalArgumentException("Erro - Veículo não encontrado.");
            }
        } else {
            throw new IllegalArgumentException("Erro - Cliente não encontrado.");
        }
    }
public void sair(String placa, String idCliente) {
    Cliente cliente = null;
    for (Cliente c : id) {
        if (c.getID().equals(idCliente)) {
            cliente = c;
            break;
        }
    }
    if (cliente != null) {
        Veiculo veiculo = cliente.possuiVeiculo(placa);
        if (veiculo != null) {
            UsoDeVaga usoDoVeiculo = null;
            for (Veiculo v : cliente.getVeiculos().values()) {
                for (UsoDeVaga uso : v.getUsos()) {
                    if (uso.getVaga().getCliente().equals(cliente) && uso.getVaga().getVeiculo().equals(veiculo)) {
                        usoDoVeiculo = uso;
                        break;
                    }
                }
            }
            if (usoDoVeiculo != null) {
                LocalDateTime saida = LocalDateTime.now(); // Data/hora de saída do veículo
                double valorPago = usoDoVeiculo.sair(saida); // Realiza a operação de saída e calcula o valor a pagar
                // Restante do código para concluir a operação de saída...
            } else {
                throw new IllegalArgumentException("Erro - Uso do veículo não encontrado.");
            }
        } else {
            throw new IllegalArgumentException("Erro - Veículo não encontrado.");
        }
    } else {
        throw new IllegalArgumentException("Erro - Cliente não encontrado.");
    }
}
private void gerarVagas() {
        if (quantFileiras <= 0 || vagasPorFileira <= 0) {
            throw new IllegalArgumentException(
                    "Erro - Quantidade de fileiras e vagas por fileira deve ser maior que zero.");
        }

        int totalVagas = quantFileiras * vagasPorFileira;
        vagas = new Vaga[totalVagas];

        for (int i = 0; i < totalVagas; i++) {
            int fila = i / vagasPorFileira + 1;
            int numero = i % vagasPorFileira + 1;
            vagas[i] = new Vaga(fila, numero);
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
