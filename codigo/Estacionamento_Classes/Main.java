import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    // Função para criar o Estacionamento;
    public static Estacionamento criarEstacionamento() {

        MyIO.println("Digite o nome do estacionamento: ");
        String nome = MyIO.readLine();
    
        MyIO.println("Digite a quantidade de fileiras: ");
        int fileiras = MyIO.readInt();
    
        MyIO.println("Digite a quantidade de vagas por fileira: ");
        int vagasPorFileira = MyIO.readInt();
    
        Estacionamento estacionamento = new Estacionamento(nome, fileiras, vagasPorFileira);
        return estacionamento;
    }
    
    // Função para criar o Cliente;
    public static Cliente criarCliente() {
        Cliente cliente;

        MyIO.println("Digite o nome do cliente: ");
        String nomeCliente = MyIO.readLine();
    
        MyIO.println("Digite o ID do cliente: ");
        String idCliente = MyIO.readLine();

        MyIO.println("Escolha a Modalidade: ");
        MyIO.println("1-HORISTA");
        MyIO.println("2-DE_TURNO");
        MyIO.println("3-MENSALISTA");
        int modalidade = MyIO.readInt();

        if(modalidade == 1){
            cliente = new Cliente(nomeCliente, idCliente, Modalidade.HORISTA);
        }
        else if (modalidade == 2){
            cliente = new Cliente(nomeCliente, idCliente, Modalidade.DE_TURNO);
        }
        else if(modalidade == 3){
            cliente = new Cliente(nomeCliente, idCliente, Modalidade.MENSALISTA);
        }

        MyIO.println("Digite a quantidade de veículos:");
        int qtdVeiculos = MyIO.readInt();
    
        for (int i = 0; i < qtdVeiculos; i++) {
            MyIO.println("Digite a placa do veículo: ");
            String placa = MyIO.readLine();
            Veiculo veiculo = new Veiculo(placa);
            cliente.addVeiculo(veiculo);
        }
        return cliente;
    }

    // Função para Estacionar o Veículo;
    public static void estacionarVeiculo(Veiculo veiculo, Estacionamento estacionamento, 
    Vaga vaga, Cliente cliente) {
        estacionamento.estacionar(veiculo.getPlaca());
        cliente.possuiVeiculo(veiculo.getPlaca()).estacionar(vaga);
    }

    // Função para fazer o veículo sair da vaga e do Estacionamento;
    public static void sairVeiculo(Veiculo veiculo, Estacionamento estacionamento, Cliente cliente) {
        //estacionamento.sair(veiculo.getPlaca());//IMPORTANTE: Função sair de estacionamento não foi implementada;
        cliente.possuiVeiculo(veiculo.getPlaca()).sair();
    }

    // Salvar os Dados em um arquivo;
    public static void salvarDados(List<Estacionamento> estacionamentos, 
    List<Cliente> clientes) throws IOException {
        // Crie um FileOutputStream para salvar os objetos em um arquivo binário;
        FileOutputStream estacioOut = new FileOutputStream("estacionamentos.ser");
        ObjectOutputStream estacioObjectOut = new ObjectOutputStream(estacioOut);

        FileOutputStream clienteOut = new FileOutputStream("clientes.ser");
        ObjectOutputStream clienteObjectOut = new ObjectOutputStream(clienteOut);

        // Escreve os estacionamentos no arquivo;
        for (Estacionamento estacionamento : estacionamentos) {
            estacioObjectOut.writeObject(estacionamento);
        }

        // Escreve os clientes no arquivo;
        for (Cliente cliente : clientes) {
            clienteObjectOut.writeObject(cliente);
        }

        // Fecha o arquivo;
        estacioObjectOut.close();
        estacioOut.close();
        clienteObjectOut.close();
        clienteOut.close();
    }

    // Menu de opções de operações;
    public static void menu() {
        System.out.println("Escolha uma opção: ");
        System.out.println("1 - Criar estacionamento");
        System.out.println("2 - Criar cliente");
        System.out.println("3 - Estacionar veículo");
        System.out.println("4 - Sair com veículo");
        System.out.println("5 - Salvar dados");
        System.out.println("6 - Sair");
    }

    // Main do Arquivo;
    public static void main(String[] args) throws IOException {
        List<Estacionamento> estacionamentos = new ArrayList<>();// Lista de Estacionamento;
        List<Cliente> clientes = new ArrayList<>();// Lista de Clientes;
        List<Veiculo> veiculos = new ArrayList<>();// Lista de Veículos;
        int escolha;// escolha do usuário;

        menu();
        escolha = MyIO.readInt();
        
        while(escolha != 6){
            MyIO.println("escolha foi: " + escolha);
            switch (escolha) {
                case 1:
                    // Criar Estacionamento;
                    Estacionamento estacionamento = criarEstacionamento();
                    estacionamentos.add(estacionamento);
                    break;
                case 2:
                    // Criar Cliente;
                    Cliente cliente = criarCliente();
                    clientes.add(cliente);
                    break;
                case 3:
                    //
                    MyIO.println("Digite a placa do veículo: ");
                    String placa = MyIO.readLine();
                    MyIO.println("Digite o nome do estacionamento: ");
                    String nomeEstacionamento = MyIO.readLine();
                    MyIO.println("Digite o ID do cliente: ");
                    String idCliente = MyIO.readLine();
                    MyIO.println("Digite a fila da vaga: ");
                    int fila = MyIO.readInt();
                    MyIO.println("Digite o número da vaga: ");
                    int coluna = MyIO.readInt();

                    Estacionamento estacionamentoAtual = null;
                    Cliente clienteAtual = null;
                    Veiculo veiculoAtual = null;
                    Vaga vagaAtual = new Vaga(fila, coluna);
                    for (Estacionamento estacionamento1 : estacionamentos) {
                        if (estacionamento1.getNome().equals(nomeEstacionamento)) {
                            estacionamentoAtual = estacionamento1;
                            break;
                        }
                    }
                    for (Cliente cliente1 : clientes) {
                        if (cliente1.getID().equals(idCliente)) {
                            clienteAtual = cliente1;
                            break;
                        }
                    }
                    for (Veiculo veiculo1 : veiculos) {
                        if (veiculo1.getPlaca().equals(placa)) {
                            veiculoAtual = veiculo1;
                            break;
                        }
                    }
                    estacionarVeiculo(veiculoAtual, estacionamentoAtual, vagaAtual, clienteAtual);
                case 4:
                    MyIO.println("Digite a placa do veículo: ");
                    String placa1 = MyIO.readLine();
                    MyIO.println("Digite o nome do estacionamento: ");
                    String nomeEstacionamento1 = MyIO.readLine();
                    MyIO.println("Digite o ID do cliente: ");
                    String idCliente1 = MyIO.readLine();
                    Estacionamento estacionamentoAtual1 = null;
                    Cliente clienteAtual1 = null;
                    Veiculo veiculoAtual1 = null;
                    for (Estacionamento estacionamento1 : estacionamentos) {
                        if (estacionamento1.getNome().equals(nomeEstacionamento1)) {
                            estacionamentoAtual1 = estacionamento1;
                            break;
                        }
                    }
                    for (Cliente cliente1 : clientes) {
                        if (cliente1.getID().equals(idCliente1)) {
                            clienteAtual1 = cliente1;
                            break;
                        }
                    }
                    for (Veiculo veiculo1 : veiculos) {
                        if (veiculo1.getPlaca().equals(placa1)) {
                            veiculoAtual1 = veiculo1;
                            break;
                        }
                    }
                    sairVeiculo(veiculoAtual1, estacionamentoAtual1, clienteAtual1);
                case 5:
                    // Salvar o Arquivo;
                    salvarDados(estacionamentos, clientes);
                    break;
                case 6:
                    // Sair do sistema;
                    System.out.println("Saindo...");
                    break;
                default:
                    // Opção inválida;
                    System.out.println("Opção inválida.");
                    break;
            }
            // Ordenar em ordem decrescente;
            Collections.sort(estacionamentos, Comparator.comparingDouble(Estacionamento::totalArrecadado).reversed());
            menu();
            escolha = MyIO.readInt();
        }
    }
    /*MyIO.println("Escolha o serviço (0 a 3): ");
        MyIO.println("0 - Nenhum");
        MyIO.println("1 - Manobrista");
        MyIO.println("2 - Lavagem");
        MyIO.println("3 - Polimento");*/
}