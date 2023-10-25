import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void menu() {
        System.out.println("Escolha uma opção: ");
        System.out.println("1 - Criar estacionamento");
        System.out.println("2 - Criar cliente");
        System.out.println("3 - Estacionar veículo");
        System.out.println("4 - Sair com veículo");
        System.out.println("5 - Salvar dados");
        System.out.println("6 - Sair");
    }

    public static Estacionamento criarEstacionamento() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do estacionamento: ");
        String nome = sc.nextLine();

        System.out.println("Digite a quantidade de fileiras: ");
        int fileiras = sc.nextInt();

        System.out.println("Digite a quantidade de vagas por fileira: ");
        int vagasPorFileira = sc.nextInt();
        sc.close();

        Estacionamento estacionamento = new Estacionamento(nome, fileiras, vagasPorFileira);
        return estacionamento;
    }

    public static Cliente criarCliente() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do cliente: ");
        String nomeCliente = sc.nextLine();

        System.out.println("Digite o ID do cliente: ");
        String idCliente = sc.nextLine();

        Cliente cliente = new Cliente(nomeCliente, idCliente);

        System.out.println("Digite a quantidade de veículos:");
        int qtdVeiculos = sc.nextInt();

        for (int i = 0; i < qtdVeiculos; i++) {
            System.out.println("Digite a placa do veículo: ");
            String placa = sc.nextLine();
            Veiculo veiculo = new Veiculo(placa);
            cliente.addVeiculo(veiculo);
        }

        sc.close();
        return cliente;
    }

    public static void estacionarVeiculo(Veiculo veiculo, Estacionamento estacionamento, Vaga vaga, Cliente cliente) {
        estacionamento.estacionar(veiculo.getPlaca());
        cliente.possuiVeiculo(veiculo.getPlaca()).estacionar(vaga);
    }

    public static void sairVeiculo(Veiculo veiculo, Estacionamento estacionamento, Cliente cliente) {
        estacionamento.sair(veiculo.getPlaca());
        cliente.possuiVeiculo(veiculo.getPlaca()).sair();
    }

    public static void salvarDados(List<Estacionamento> estacionamentos, List<Cliente> clientes) throws IOException {
        FileOutputStream estacioOut = new FileOutputStream("estacionamentos.ser");
        ObjectOutputStream estacioObjectOut = new ObjectOutputStream(estacioOut);

        FileOutputStream clienteOut = new FileOutputStream("clientes.ser");
        ObjectOutputStream clienteObjectOut = new ObjectOutputStream(clienteOut);

        FileOutputStream vagaOut = new FileOutputStream("vagas.ser");
        ObjectOutputStream vagaObjectOut = new ObjectOutputStream(vagaOut);

        for (Estacionamento estacionamento : estacionamentos) {
            estacioObjectOut.writeObject(estacionamento);
        }

        for (Cliente cliente : clientes) {
            clienteObjectOut.writeObject(cliente);
        }

        estacioObjectOut.close();
        estacioOut.close();
        clienteObjectOut.close();
        clienteOut.close();
    }

    public static void main(String[] args) throws IOException {
        List<Estacionamento> estacionamentos = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        List<Veiculo> veiculos = new ArrayList<>();
        int escolha;

        Scanner sc = new Scanner(System.in);

        menu();
        escolha = sc.nextInt();

        switch (escolha) {
            case 1:
                Estacionamento estacionamento = criarEstacionamento();
                estacionamentos.add(estacionamento);
                menu();
                escolha = sc.nextInt();

            case 2:
                Cliente cliente = criarCliente();
                clientes.add(cliente);
                menu();
                escolha = sc.nextInt();

            case 3:
                System.out.println("Digite a placa do veículo: ");
                String placa = sc.nextLine();
                System.out.println("Digite o nome do estacionamento: ");
                String nomeEstacionamento = sc.nextLine();
                System.out.println("Digite o ID do cliente: ");
                String idCliente = sc.nextLine();
                System.out.println("Digite a fila da vaga: ");
                int fila = sc.nextInt();
                System.out.println("Digite o número da vaga: ");
                int coluna = sc.nextInt();
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
                menu();
                escolha = sc.nextInt();

            case 4:
                System.out.println("Digite a placa do veículo: ");
                String placa1 = sc.nextLine();
                System.out.println("Digite o nome do estacionamento: ");
                String nomeEstacionamento1 = sc.nextLine();
                System.out.println("Digite o ID do cliente: ");
                String idCliente1 = sc.nextLine();
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
                menu();
                escolha = sc.nextInt();

            case 5:
                salvarDados(estacionamentos, clientes);
                menu();
                escolha = sc.nextInt();

            case 6:
                System.out.println("Saindo...");
                break;

            default:
                System.out.println("Opção inválida.");
                break;

        }

        sc.close();
    }
}
