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
        String nome;
        int fileiras;
        int vagasPorFileira;
        do{
            MyIO.println("Digite o nome do estacionamento: ");
            nome = MyIO.readLine();
            MyIO.println("Digite a quantidade de fileiras: ");
            fileiras = MyIO.readInt();
            MyIO.println("Digite a quantidade de vagas por fileira: ");
            vagasPorFileira = MyIO.readInt();
            //Caso de erro;
            if(fileiras <= 0 || vagasPorFileira <= 0){
                MyIO.println("Erro - Quantidade de fileiras e vagas por fileira deve ser maior que zero.");
            }
        }while(fileiras <= 0 || vagasPorFileira <= 0);

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

        int modalidade;// Modalidade do Cliente;
        do{
            MyIO.println("Modalidades Existentes:");
            MyIO.println("1-HORISTA");
            MyIO.println("2-DE_TURNO");
            MyIO.println("3-MENSALISTA");
            MyIO.println("Escolha a Modalidade: ");
            modalidade = MyIO.readInt();
            // Caso uma opção inválida seja selecionada;
            if (modalidade < 1 || modalidade > 3) {
                MyIO.println("Opcao invalida. Escolha novamente.");
            }
        } while(modalidade < 1 || modalidade > 3);
        
        // Cliente é criado baseado na modalidade selecionada;
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
    int escolha, Vaga vaga, Cliente cliente){
        estacionamento.estacionar(veiculo.getPlaca());
        cliente.possuiVeiculo(veiculo.getPlaca()).estacionar(vaga, escolha, cliente);
        cliente.setEscolha(escolha);
    }

    // Função para fazer o veículo sair da vaga e do Estacionamento;
    public static void sairVeiculo(Veiculo veiculo, Estacionamento estacionamento,
    int escolha, Cliente cliente) {
        double valorPago;
        valorPago = estacionamento.sair(veiculo.getPlaca(), escolha);
        MyIO.println("Valor Pago: " + valorPago);
    }

    // Função para mudar a modalidade do Cliente;
    public static void MudarModalidade(String idCliente, List<Cliente> clientes){
        boolean clienteEncontrado = false;// Determina se o cliente foi encontrado ou não;
        //Processo de busca do cliente;
        for (Cliente cliente : clientes) {
            if (cliente.getID().equals(idCliente)) {
                // Modificar a modalidade do cliente selecionado;
                int novaModalidade;
                do {
                    MyIO.println("Nova Modalidade:");
                    MyIO.println("1 - HORISTA");
                    MyIO.println("2 - DE_TURNO");
                    MyIO.println("3 - MENSALISTA");
                    MyIO.println("Escolha a nova modalidade: ");
                    novaModalidade = MyIO.readInt();
                    // Caso uma opção inválida seja selecionada;
                    if (novaModalidade < 1 || novaModalidade > 3) {
                        MyIO.println("Opcao invalida. Escolha novamente.");
                    }
                } while (novaModalidade < 1 || novaModalidade > 3);
    
                switch (novaModalidade) {
                    case 1:
                        cliente.setModalidade(Modalidade.HORISTA);
                        break;
                    case 2:
                        cliente.setModalidade(Modalidade.DE_TURNO);
                        break;
                    case 3:
                        cliente.setModalidade(Modalidade.MENSALISTA);
                        break;
                    default:
                        MyIO.println("Opcao invalida. Escolha novamente.");
                        break;
                }
                clienteEncontrado = true;
                MyIO.println("Modalidade mudada para: " + cliente.getModalidade());
                break;
            }
        }
        if(!clienteEncontrado){
            MyIO.println("Cliente com ID " + idCliente + " não foi encontrado.");
        }
    }

    // Salvar os Dados em um arquivo;
    public static void salvarDados(List<Estacionamento> estacionamentos,
    List<Cliente> clientes) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream("dados.bin");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            // Salvar Estacionamentos;
            for(Estacionamento estacionamento : estacionamentos){
                objectOut.writeObject(estacionamento);
            }
            // Salvar Clientes;
            for(Cliente cliente : clientes){
                objectOut.writeObject(cliente);
            }
        } 
        catch(IOException e){e.printStackTrace();}
    }

    // Função para ler dados do arquivo binário;
    public static void lerDados(List<Estacionamento> estacionamentos, List<Cliente> clientes,
    List<Veiculo> veiculos, int tentativas) {
        try (FileInputStream fileInput = new FileInputStream("dados.bin");
        ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
            Object obj;
            while((obj = objectInput.readObject()) != null){
                if (obj instanceof Estacionamento) {
                    estacionamentos.add((Estacionamento) obj);
                } 
                else if (obj instanceof Cliente) {
                    clientes.add((Cliente) obj);
                } 
                else if (obj instanceof Veiculo) {
                    veiculos.add((Veiculo) obj);
                }
            }
        } catch (EOFException ignored) {
            MyIO.println("Arquivo aberto com sucesso.");
        } catch (IOException | ClassNotFoundException e) {
            // Caso o número de tentativas seja maior que 0;
            if (tentativas > 0) {
                SalvarArquivo.main(new String[]{});
                lerDados(estacionamentos, clientes, veiculos, tentativas - 1);
            } else {e.printStackTrace();}
        }
    }

    // Menu de opções de operações;
    public static void menu() {
        MyIO.println("Lista de opcoes:");
        MyIO.println("1 - Criar estacionamento");
        MyIO.println("2 - Criar cliente");
        MyIO.println("3 - Estacionar veiculo");
        MyIO.println("4 - Sair com veiculo");
        MyIO.println("5 - Mudar seu plano");
        MyIO.println("6 - Gerar Relatorio");
        MyIO.println("7 - Sair");
        MyIO.println("Escolha uma opcao: ");
    }

    // Menu para os relatórios que podem ser gerados;
    public static void MenuGerarRelatório(List<Estacionamento> estacionamentos, List<Cliente> clientes, 
    List<Veiculo> veiculos){
        MyIO.println("Lista de Relatorios:");
        MyIO.println("1 - Historico de uso do estacionamento");
        MyIO.println("2 - Historico por datas");
        MyIO.println("3 - Relatórios de utilização: ordem crescente de data ou decrescente de valor;");
        MyIO.println("4 - Valor total arrecadado por um estacionamento");
        MyIO.println("5 - Valor arrecadado em um mes");
        MyIO.println("6 - Valor medio por utilizacao do estacionamento");
        MyIO.println("7 - Ranking dos 5 clientes que mais geraram arrecadacao em um mes");
        MyIO.println("8 - Relatorio de arrecadacao de todos os estacionamentos, em ordem decrescente");
        MyIO.println("9 - Quantas vezes os clientes mensalistas utilizaram um estacionamento no mes corrente");
        MyIO.println("10 - Arrecadacao media gerada pelos clientes horistas no mes atual");
        MyIO.println("11 - Cancelar");
        MyIO.println("Escolha uma opcao: ");
        int escolha;// escolha do usuário;
        escolha = MyIO.readInt();
        switch (escolha){
            case 1:
                MyIO.println("Digite o ID do cliente: ");
                String idCliente1 = MyIO.readLine();
                Cliente clienteSelecionado = null;
                for (Cliente cliente1 : clientes) {
                        if (cliente1.getID().equals(idCliente1)) {
                            clienteSelecionado = cliente1;
                            break;
                        }
                }
                UsoDeVagaGerarRelatorio.HistoricoUsoEstacionamento(clienteSelecionado);
                break;
            case 2:
                MyIO.println("Digite o ID do cliente: ");
                String idCliente2 = MyIO.readLine();
                Cliente clienteSelecionado2 = null;
                for (Cliente cliente1 : clientes) {
                        if (cliente1.getID().equals(idCliente2)) {
                            clienteSelecionado2 = cliente1;
                            break;
                        }
                }
                UsoDeVagaGerarRelatorio.HistoricoPorDatas(clienteSelecionado2);
                break;
            case 3:
                break;
            case 4:
                MyIO.println("Digite o Nome do Estacionamento: ");
                String nomeEstacionamento = MyIO.readLine();
                Estacionamento estacionamentoAtual = null;
                for (Estacionamento estacionamento1 : estacionamentos) {
                        if (estacionamento1.getNome().equals(nomeEstacionamento)) {
                            estacionamentoAtual = estacionamento1;
                            break;
                        }
                }
                UsoDeVagaGerarRelatorio.TotalArrecadadoEstacionamento(estacionamentoAtual);
                break;
            case 5:
                //UsoDeVagaGerarRelatorio.ArrecadadoPorMes();
                break;
            case 6:
                //UsoDeVagaGerarRelatorio.MediaUtilizacaoEstacionamento();
                break;
            case 7:
                //UsoDeVagaGerarRelatorio.Ranking5Clientes();
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                MyIO.println("Processo Cancelado.");
                break;
            default:
                MyIO.println("Opcao invalida.");
                break;
        }
    }

    // Main do Arquivo;
    public static void main(String[] args) throws IOException {
        List<Estacionamento> estacionamentos = new ArrayList<>();// Lista de Estacionamento;
        List<Cliente> clientes = new ArrayList<>();// Lista de Clientes;
        List<Veiculo> veiculos = new ArrayList<>();// Lista de Veículos;

        int tentativas = 1;// Número de vezes que lerDados pode ser realizado antes de dar erro;

        // Lê o arquivo dados.bin;
        lerDados(estacionamentos, clientes, veiculos, tentativas);

        int escolha;// escolha do usuário;
        menu();
        escolha = MyIO.readInt();

        while(escolha != 7){
            switch(escolha){
                case 1:
                    // Criar Estacionamento;
                    Estacionamento estacionamento = criarEstacionamento();
                    estacionamentos.add(estacionamento);
                    // Salvar o Arquivo;
                    salvarDados(estacionamentos, clientes);
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
                    // Salvar o Arquivo;
                    salvarDados(estacionamentos, clientes);
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
                    estacionarVeiculo(veiculoAtual, estacionamentoAtual, servico, vagaAtual, clienteAtual);
                    break;

                case 4:
                    // Sair com o veículo;
                    MyIO.println("Digite a placa do veiculo: ");
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
                    sairVeiculo(veiculoAtual1, estacionamentoAtual1, clienteAtual1.getEscolha(), clienteAtual1);
                    break;

                case 5:
                    // Mudar o plano de utilização do estacionamento;
                    MyIO.println("Digite o ID do cliente: ");
                    String idCliente2 = MyIO.readLine();
                    MudarModalidade(idCliente2, clientes);
                    // Salvar o Arquivo;
                    salvarDados(estacionamentos, clientes);
                    break;

                case 6:
                    // Gerar Relatório;
                    MenuGerarRelatório(estacionamentos, clientes, veiculos);
                    break;

                case 7:
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