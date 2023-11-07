import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * <h1>Cliente</h1>
 * Classe Cliente.
 * @author Gabriel Alejandro Figueiro Galindo
 * @since 10/10/2023
*/
public class Cliente implements Serializable{
    private String nome;//Nome do cliente;
    private String id;//Identificador;
    private Map<String, Veiculo> veiculos;//Map para armazenar os veiculos, com placas de veículos como chaves;

    //Constructor;
    public Cliente(String nome, String id){
        this.nome = nome;
        this.id = id;
        this.veiculos = new HashMap<>();
    }

	//Getters;
	public String getNome(){return nome;}
	public String getID(){return id;}
	public Map<String, Veiculo> getVeiculos(){return veiculos;}

    //Método para adicionar Veiculo para o Map de veículos;
    public void addVeiculo(Veiculo veiculo){
        if(veiculos.containsKey(veiculo.getPlaca())){
            throw new IllegalArgumentException("Erro - Carro já inserido.");
        }
        veiculos.put(veiculo.getPlaca(), veiculo);
    }

	//Verifique se o Cliente possui um Veículo com uma placa específica;
    public Veiculo possuiVeiculo(String placa){
        return veiculos.get(placa);
    }

	//Total de usos;
	public int totalDeUsos(){
		int totalDeUsos = 0;
		for(Veiculo veiculo : veiculos.values()){
			totalDeUsos += veiculo.totalDeUsos();
        }
        return totalDeUsos;
    }

	//Arrecadado pelo veiculo com a placa específica;
	public double arrecadadoPorVeiculo(String placa){
		Veiculo veiculo = veiculos.get(placa);
		if(veiculo == null){
			throw new IllegalArgumentException("Erro - Veiculo não encontrado.");
		}
		return veiculo.totalArrecadado();
	}

	//Total arrecadado pelo Cliente;
    public double arrecadadoTotal(){
        double TotalArrecadado = 0;
        for(Veiculo veiculo : veiculos.values()){
            TotalArrecadado += veiculo.totalArrecadado();
        }
        return TotalArrecadado;
    }
	
    //Arrecadado no mês pelo Cliente;
    public double arrecadadoNoMes(int mes){
        double ArrecadadoMes = 0;
        for(Veiculo veiculo : veiculos.values()){
            ArrecadadoMes += veiculo.arrecadadoNoMes(mes);
        }
        return ArrecadadoMes;
    }
}