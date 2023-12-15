import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum Modalidade {
    HORISTA, DE_TURNO, MENSALISTA
}

public class Cliente implements Serializable, Observer{
    private String nome;
    private String id;
    private Map<String, Veiculo> veiculos;
    private int escolha;// Serviço escolhido pelo Cliente ao Estacionar;
    private Modalidade modalidade;
    private Estacionamento estacionamento;

    // Construtores;
    public Cliente(String nome, String id, Modalidade modalidade, 
    Estacionamento estacionamento){
        this.nome = nome;
        this.id = id;
        this.modalidade = modalidade;
        this.veiculos = new HashMap<>();
        this.estacionamento = estacionamento;
    }

    public Cliente(String nome, String id, Modalidade modalidade){
        this.nome = nome;
        this.id = id;
        this.modalidade = modalidade;
        this.veiculos = new HashMap<>();
        this.estacionamento = null;
    }

    // Setters;
    public void setNome(String nome){this.nome = nome;}

    public void setID(String id){this.id = id;}

    public void setEscolha(int escolha){this.escolha = escolha;}

    public void setModalidade(Modalidade modalidade){
        this.modalidade = modalidade;
    }

    public void setEstacionamento(Estacionamento estacionamento){
        this.estacionamento = estacionamento;
    }

    // Getters;
    public String getNome(){return nome;}

    public String getID(){return id;}

    public Map<String, Veiculo> getVeiculos(){return veiculos;}

    public int getEscolha(){return escolha;}

    public Modalidade getModalidade(){return modalidade;}

    public Estacionamento getEstacionamento(){return estacionamento;}

    public List<Veiculo> getVeiculosAsList(){
        return new ArrayList<>(veiculos.values());
    }

    public Veiculo possuiVeiculo(String placa){
        return veiculos.get(placa);
    }

    // Adicionar veiculo na lista do cliente;
    public void addVeiculo(Veiculo veiculo){
        if(veiculos.containsKey(veiculo.getPlaca())){
            MyIO.println("Erro - Carro ja inserido.");
        }
        else{
            veiculos.put(veiculo.getPlaca(), veiculo);
        }
    }

    // Calcular a Cobrança com base na Modalidade do Cliente;
    public double calcularCobranca(){
        switch (modalidade) {
            case HORISTA:
                return calcularCobrancaHorista();
            case DE_TURNO:
                return 200;// Mensalidade fixa de R$200;
            case MENSALISTA:
                return 500;// Mensalidade fixa de R$500;
            default:
                throw new IllegalArgumentException("Modalidade invalida");
        }
    }

    // Calcular a Cobrança de Clientes da Modalidade Horista;
    private double calcularCobrancaHorista(){
        double totalArrecadado = 0;
        for(Veiculo veiculo : veiculos.values()){
            totalArrecadado += veiculo.totalArrecadado();
        }
        return totalArrecadado;
    }

    // Fazer um update do valor da Arrecadação;
    @Override
    public void updateArrecadacao(Cliente cliente, double novaArrecadacao){
        double novaArrecadacaoCliente = arrecadadoTotal() + novaArrecadacao;
        estacionamento.notifyObservers(this, novaArrecadacaoCliente);
    }

    //***********Código para os relátorios***********

    // Método para obter o total de usos;
    public int totalDeUsos(){
        int totalDeUsos = 0;
        // Soma o número de vezes, cada veículo do Cliente foi estacionado; 
        for (Veiculo veiculo : veiculos.values()){
            totalDeUsos += veiculo.getTotalUsos();
        }
        return totalDeUsos;
    }

    // Método para obter o valor arrecadado por um veículo com uma placa específica;
    public double arrecadadoPorVeiculo(String placa){
        Veiculo veiculo = veiculos.get(placa);
        if(veiculo == null){
            MyIO.println("Erro - Veiculo nao encontrado.");
            return 0;
        }
        else{
            return veiculo.totalArrecadado();
        }
    }

    // Método para obter o total arrecadado pelo cliente;
    public double arrecadadoTotal(){
        double totalArrecadado = 0;
        for(Veiculo veiculo : veiculos.values()){
            totalArrecadado += veiculo.totalArrecadado();
        }
        return totalArrecadado;
    }

    // Método para obter o total arrecadado em um mês;
    public double arrecadadoNoMes(int mes){
        if(mes < 1 || mes > 12){
            MyIO.println("Mes invalido. Insira um numero entre 1 e 12.");
            return 0;
        }
        else{
            // Soma o total arrecadado por cada veículo no mês escolhido;
            double totalArrecadadoNoMes = 0;
            for(Veiculo veiculo : veiculos.values()){
                totalArrecadadoNoMes += arrecadadoPorVeiculoNoMes(veiculo, mes);
            }
            return totalArrecadadoNoMes;
        }
    }

    // Método para obter o valor arrecadado por um veículo, com uma placa específica, em um mês específico;
    private double arrecadadoPorVeiculoNoMes(Veiculo veiculo, int mes){
        if(mes < 1 || mes > 12){
            MyIO.println("Mes invalido. Insira um numero entre 1 e 12.");
            return 0;
        }
        double totalArrecadadoNoMes = 0;
        for (UsoDeVaga uso : veiculo.getUsos()) {
            LocalDateTime dataUso = uso.getEntrada();
            if (dataUso.getMonthValue() == mes) {
                totalArrecadadoNoMes += uso.getValorPago();
            }
        }
        return totalArrecadadoNoMes;
    }
}