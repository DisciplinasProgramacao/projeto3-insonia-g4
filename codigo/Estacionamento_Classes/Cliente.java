import java.util.ArrayList;
import java.util.List;
/**
* <h1>Cliente</h1>
* Classe Cliente.
* @author Gabriel Alejandro Figueiro Galindo
* @since 10/10/2023
*/
public class Cliente{
	private String nome;//Nome do cliente;
	private String id;//identificador;
	private List<Veiculo> veiculos;//Lista de veiculos;
	//Construtor;
	public Cliente(String nome, String id){
		this.nome = nome;
		this.id = id;
		this.veiculos = new ArrayList<>();
	}
	//Getters;
	public String getNome(){return nome;}
	public String getID(){return id;}
	public List<Veiculo> getVeiculos(){return veiculos;}
	
	//Setters;
	public void setNome(String nome){this.nome = nome;}
	public void setID(String id){this.id = id;}
	public void setVeiculos(List<Veiculo> veiculos){this.veiculos = veiculos;}
	
	//Metodo para adicionar veículos na lista do cliente;
	public void addVeiculo(Veiculo veiculo){
		veiculos.add(veiculo);
	}
	
	//Verifica se o Cliente possui o veículo com base em sua placa;
	public Veiculo possuiVeiculo(String placa){
		for(Veiculo veiculo : veiculos){
			if(veiculo.getPlaca() == placa){
				return veiculo;
			}
		}
		throw new IllegalArgumentException("Erro - Veiculo não encontrado.");
	}

        //Total de usos;
	public int totalDeUsos(){
		int totalDeUsos = 0;
		for(Veiculo veiculo : veiculos){
			totalDeUsos += veiculo.totalDeUsos();
			return totalDeUsos;
		}
	}
	
        //Pega o total arrecadado pelo veículo;
	public double arrecadadoPorVeiculo(String placa){
		for(Veiculo veiculo : veiculos){
			if(veiculo.getPlaca() == placa){
				return veiculo.totalArrecadado();
			}
		}
		throw new IllegalArgumentException("Erro - Veiculo não encontrado.");
	}

        //Total arrecadado pelo cliente;
	public double arrecadadoTotal(){
		double TotalArrecadado = 0;
		for(Veiculo veiculo : veiculos){
			TotalArrecadado += veiculo.totalArrecadado();
			return TotalArrecadado;
		}
	}

        //Arrecadado no mês pelo cliente;
	public double arrecadadoNoMes(int mes){
		double ArrecadadoMes = 0;
		for(Veiculo veiculo : veiculos){
			ArrecadadoMes += veiculo.arrecadadoNoMes(mes);
			return ArrecadadoMes;
		}
	}
}