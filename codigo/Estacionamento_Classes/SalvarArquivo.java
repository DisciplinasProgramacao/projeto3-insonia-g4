import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SalvarArquivo{
    // Salvamento dos objetos em arquivo binário;
    public static void salvarObjetos(Estacionamento[] estacionamentos, Cliente[] clientes, 
    Veiculo[] veiculos){
        try{
            // Crie um FileOutputStream para salvar os objetos em um arquivo binário;
            FileOutputStream fileOut = new FileOutputStream("dados.bin");

            // Crie um ObjectOutputStream para escrever objetos no arquivo;
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            //Inserir Estacionamentos;
            for (Estacionamento estacionamento : estacionamentos) {
                objectOut.writeObject(estacionamento);
            }

            //Inserir Clientes;
            for (Cliente cliente : clientes) {
                objectOut.writeObject(cliente);
            }

            //Inserir Veículos;
            for (Veiculo veiculo : veiculos) {
                objectOut.writeObject(veiculo);
            }

            // Feche o ObjectOutputStream e o FileOutputStream;
            objectOut.close();
            fileOut.close();
        }catch(Exception e){e.printStackTrace();}
    }

    // Função Main;
    public static void main(String[] args){
        // Criando 3 estacionamentos;
        Estacionamento[] estacionamentos = new Estacionamento[3];
        estacionamentos[0] = new Estacionamento("Estacionamento 1", 1, 15);
        estacionamentos[1] = new Estacionamento("Estacionamento 2", 1, 15);
        estacionamentos[2] = new Estacionamento("Estacionamento 3", 1, 15);

        // Criando 10 clientes;
        Cliente[] clientes = new Cliente[10];
        clientes[0] = new Cliente("Cliente 1", "12345", Modalidade.HORISTA);
        clientes[1] = new Cliente("Cliente 2", "54321", Modalidade.HORISTA);
        clientes[2] = new Cliente("Cliente 3", "53367", Modalidade.DE_TURNO);
        clientes[3] = new Cliente("Cliente 4", "74829", Modalidade.DE_TURNO);
        clientes[4] = new Cliente("Cliente 5", "23679", Modalidade.MENSALISTA);
        clientes[5] = new Cliente("Cliente 6", "17241", Modalidade.MENSALISTA);
        clientes[6] = new Cliente("Cliente 7", "27549", Modalidade.MENSALISTA);
        clientes[7] = new Cliente("Cliente 8", "34681", Modalidade.MENSALISTA);
        clientes[8] = new Cliente("Cliente 9", "90231", Modalidade.DE_TURNO);
        clientes[9] = new Cliente("Cliente 10", "17439", Modalidade.HORISTA);

        // Adicionando clientes aos estacionamentos;
        estacionamentos[0].addCliente(clientes[0]);
        estacionamentos[0].addCliente(clientes[1]);
        estacionamentos[0].addCliente(clientes[2]);
        estacionamentos[0].addCliente(clientes[3]);
        estacionamentos[1].addCliente(clientes[4]);
        estacionamentos[1].addCliente(clientes[5]);
        estacionamentos[2].addCliente(clientes[6]);
        estacionamentos[2].addCliente(clientes[7]);
        estacionamentos[2].addCliente(clientes[8]);
        estacionamentos[2].addCliente(clientes[9]);

        // Criando 15 veículos;
        Veiculo[] veiculos = new Veiculo[15];
        veiculos[0] = new Veiculo("ABC123");
        veiculos[1] = new Veiculo("XYZ789");
        veiculos[2] = new Veiculo("AEZ919");
        veiculos[3] = new Veiculo("IYO762");
        veiculos[4] = new Veiculo("AEC691");
        veiculos[5] = new Veiculo("HTJ571");
        veiculos[6] = new Veiculo("RTE673");
        veiculos[7] = new Veiculo("OPW823");
        veiculos[8] = new Veiculo("LKO513");
        veiculos[9] = new Veiculo("KLR283");
        veiculos[10] = new Veiculo("MLN793");
        veiculos[11] = new Veiculo("YMN898");
        veiculos[12] = new Veiculo("RWI456");
        veiculos[13] = new Veiculo("UYI821");
        veiculos[14] = new Veiculo("TEW957");

        // Adicionando veículos aos clientes;
        clientes[0].addVeiculo(veiculos[0]);
        clientes[1].addVeiculo(veiculos[1]);
        clientes[2].addVeiculo(veiculos[2]);
        clientes[3].addVeiculo(veiculos[3]);
        clientes[4].addVeiculo(veiculos[4]);
        clientes[5].addVeiculo(veiculos[5]);
        clientes[6].addVeiculo(veiculos[6]);
        clientes[7].addVeiculo(veiculos[7]);
        clientes[8].addVeiculo(veiculos[8]);
        clientes[9].addVeiculo(veiculos[9]);
        clientes[9].addVeiculo(veiculos[10]);
        clientes[9].addVeiculo(veiculos[11]);
        clientes[9].addVeiculo(veiculos[12]);
        clientes[9].addVeiculo(veiculos[13]);
        clientes[9].addVeiculo(veiculos[14]);

        // 60 usos de estacionamento;
        int registrosPorCliente = 4;
        int clienteIndex = 0;
        do {
            Cliente clienteAtual;
            String placaAtual;
            Estacionamento estacionamentoAtual;
            // Estacionamento;
            if (clienteIndex < 4) {
                estacionamentoAtual = estacionamentos[0];
            } else if (clienteIndex < 6) {
                estacionamentoAtual = estacionamentos[1];
            } else {
                estacionamentoAtual = estacionamentos[2];
            }
            // Cliente e veiculo;
            if (clienteIndex < 10) {
                clienteAtual = clientes[clienteIndex];
                placaAtual = veiculos[clienteIndex].getPlaca();
            } else {
                clienteAtual = clientes[9];
                placaAtual = veiculos[clienteIndex].getPlaca();
            }
            // Realizar a entrada e saída do estacionamento;
            for (int i = 0; i < registrosPorCliente; i++) {
                Vaga vaga = new Vaga(1, i + 1);
                boolean certo = estacionamentoAtual.estacionar(clienteAtual.possuiVeiculo(placaAtual),
                estacionamentoAtual, clienteAtual, vaga);
                if(certo){
                    clienteAtual.possuiVeiculo(placaAtual).estacionar(vaga, 0, clienteAtual);
                    clienteAtual.setEscolha(0);
                    estacionamentoAtual.sair(clienteAtual.possuiVeiculo(placaAtual), clienteAtual, vaga);
                }
            }
            clienteIndex++;
        } while (clienteIndex < 15);

        // Criar um arquivo para salvar os dados;
        salvarObjetos(estacionamentos, clientes, veiculos);
    }
}