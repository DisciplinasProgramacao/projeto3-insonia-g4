import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

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
        Cliente cliente = null;

        MyIO.println("Digite o nome do cliente: ");
        String nomeCliente = MyIO.readLine();

        MyIO.println("Digite o ID do cliente: ");
        String idCliente = MyIO.readLine();

        MyIO.println("Modalidades Existentes:");
        MyIO.println("1-HORISTA");
        MyIO.println("2-DE_TURNO");
        MyIO.println("3-MENSALISTA");
        MyIO.println("Escolha a Modalidade: ");
        int modalidade = MyIO.readInt();// Modalidade do Cliente;
        if (modalidade == 1) {
            cliente = new Cliente(nomeCliente, idCliente, Modalidade.HORISTA);
        } else if (modalidade == 2) {
            cliente = new Cliente(nomeCliente, idCliente, Modalidade.DE_TURNO);
        } else if (modalidade == 3) {
            cliente = new Cliente(nomeCliente, idCliente, Modalidade.MENSALISTA);
        }
        return cliente;
    }

    // Função para Estacionar o Veículo;
    public static void estacionarVeiculo(Veiculo veiculo, Estacionamento estacionamento, 
    int escolha, Vaga vaga, Cliente cliente) {
        estacionamento.estacionar(veiculo.getPlaca());
        cliente.possuiVeiculo(veiculo.getPlaca()).estacionar(vaga, escolha, cliente);
    }

    // Função para fazer o veículo sair da vaga e do Estacionamento;
    public static void sairVeiculo(Veiculo veiculo, Estacionamento estacionamento, 
    int escolha, Cliente cliente) {
        estacionamento.sair(veiculo.getPlaca(), escolha);
    }

    // Salvar os Dados em um arquivo;
    public static void salvarDados(List<Estacionamento> estacionamentos, 
    List<Cliente> clientes) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream("dados.bin");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
    
            // Salvar Estacionamentos;
            for (Estacionamento estacionamento : estacionamentos) {
                objectOut.writeObject(estacionamento);
            }
    
            // Salvar Clientes;
            for (Cliente cliente : clientes) {
                objectOut.writeObject(cliente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    // Menu de opções de operações;
    public static void menu() {
        MyIO.println("Lista de opcoes:");
        MyIO.println("1 - Criar estacionamento");
        MyIO.println("2 - Criar cliente");
        MyIO.println("3 - Estacionar veiculo");
        MyIO.println("4 - Sair com veiculo");
        MyIO.println("5 - Salvar dados");
        MyIO.println("6 - Sair");
        MyIO.println("Escolha uma opcao: ");
    }

    // Função para ler dados do arquivo binário
    public static void lerDados(List<Estacionamento> estacionamentos, List<Cliente> clientes, 
    List<Veiculo> veiculos) {
        try (FileInputStream fileInput = new FileInputStream("dados.bin");
             ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {

            Object obj;
            while ((obj = objectInput.readObject()) != null) {
                if (obj instanceof Estacionamento) {
                    estacionamentos.add((Estacionamento) obj);
                } else if (obj instanceof Cliente) {
                    clientes.add((Cliente) obj);
                } else if (obj instanceof Veiculo) {
                    veiculos.add((Veiculo) obj);
                }
            }
        } catch (EOFException ignored) {
            MyIO.println("Arquivo aberto com sucesso.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Main do Arquivo;
    public static void main(String[] args) throws IOException {
        List<Estacionamento> estacionamentos = new ArrayList<>();// Lista de Estacionamento;
        List<Cliente> clientes = new ArrayList<>();// Lista de Clientes;
        List<Veiculo> veiculos = new ArrayList<>();// Lista de Veículos;

        // Lê o arquivo dados.bin;
        lerDados(estacionamentos, clientes, veiculos);

        int escolha;// escolha do usuário;
        menu();
        escolha = MyIO.readInt();

        while (escolha != 6) {
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
                    
                    // Criar Veículos;
                    MyIO.println("Digite a quantidade de veiculos: ");
                    int qtdVeiculos = MyIO.readInt();
                    
                    for (int i = 0; i < qtdVeiculos; i++) {
                        MyIO.println("Digite a placa do veiculo: ");
                        String placa = MyIO.readLine();
                        Veiculo veiculo = new Veiculo(placa);
                        cliente.addVeiculo(veiculo);
                        veiculos.add(veiculo);
                    }

                    break;

                case 3:
                    // Estacionar o Veículo;
                    MyIO.println("Digite a placa do veiculo: ");
                    String placa = MyIO.readLine();
                    MyIO.println("Digite o nome do estacionamento: ");
                    String nomeEstacionamento = MyIO.readLine();
                    MyIO.println("Digite o ID do cliente: ");
                    String idCliente = MyIO.readLine();
                    MyIO.println("Digite a fila da vaga: ");
                    int fila = MyIO.readInt();
                    MyIO.println("Digite o numero da vaga: ");
                    int coluna = MyIO.readInt();

                    MyIO.println("Tipo de servico");
                    MyIO.println("0-Nenhum");
                    MyIO.println("1-Manobrista");
                    MyIO.println("2-Lavagem");
                    MyIO.println("3-Polimento");
                    MyIO.println("Digite a sua escolha de servico: ");
                    int servico = MyIO.readInt();
                    
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
                            estacionamentoAtual.addCliente(clienteAtual);
                            break;
                        }
                    }
                    for (Veiculo veiculo1 : veiculos) {
                        if (veiculo1.getPlaca().equals(placa)) {
                            veiculoAtual = veiculo1;
                            break;
                        }
                    }
                    //System.out.println(vagaAtual.getUsuario());
                    estacionarVeiculo(veiculoAtual, estacionamentoAtual, servico, vagaAtual, clienteAtual);

                case 4:
                    // Sair com o veículo;
                    MyIO.println("Digite a placa do veiculo: ");
                    String placa1 = MyIO.readLine();
                    MyIO.println("Digite o nome do estacionamento: ");
                    String nomeEstacionamento1 = MyIO.readLine();
                    MyIO.println("Digite o ID do cliente: ");
                    String idCliente1 = MyIO.readLine();

                    MyIO.println("Tipo de servico");
                    MyIO.println("0-Nenhum");
                    MyIO.println("1-Manobrista");
                    MyIO.println("2-Lavagem");
                    MyIO.println("3-Polimento");
                    MyIO.println("Digite a sua escolha de servico: ");
                    escolha = MyIO.readInt();

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
                    sairVeiculo(veiculoAtual1, estacionamentoAtual1, escolha, clienteAtual1);

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
                    System.out.println("Opcao invalida.");
                    break;
            }
            // Ordenar em ordem decrescente;
            Collections.sort(estacionamentos, Comparator.comparingDouble(Estacionamento::totalArrecadado).reversed());
            menu();
            escolha = MyIO.readInt();
        }
    }
}