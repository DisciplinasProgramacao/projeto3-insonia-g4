import java.time.LocalDateTime;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SalvarArquivo{
    public static void main(String[] args){
        try{
            // Criando 3 estacionamentos;
            Estacionamento estacionamento1 = new Estacionamento("Estacionamento 1", 10, 14);
            Estacionamento estacionamento2 = new Estacionamento("Estacionamento 2", 10, 14);
            Estacionamento estacionamento3 = new Estacionamento("Estacionamento 3", 10, 14);

            // Criando 6 clientes;
            Cliente cliente1 = new Cliente("Cliente 1", "12345", Modalidade.HORISTA);
            Cliente cliente2 = new Cliente("Cliente 2", "54321", Modalidade.HORISTA);
            Cliente cliente3 = new Cliente("Cliente 3", "53367", Modalidade.DE_TURNO);
            Cliente cliente4 = new Cliente("Cliente 4", "74829", Modalidade.DE_TURNO);
            Cliente cliente5 = new Cliente("Cliente 5", "23679", Modalidade.MENSALISTA);
            Cliente cliente6 = new Cliente("Cliente 6", "17241", Modalidade.MENSALISTA);

            // Adicionando clientes ao estacionamento;
            estacionamento1.addCliente(cliente1);
            estacionamento1.addCliente(cliente2);
            estacionamento2.addCliente(cliente3);
            estacionamento2.addCliente(cliente4);
            estacionamento3.addCliente(cliente5);
            estacionamento3.addCliente(cliente6);

            // Criando 6 veículos;
            Veiculo veiculo1 = new Veiculo("ABC123");
            Veiculo veiculo2 = new Veiculo("XYZ789");
            Veiculo veiculo3 = new Veiculo("AEZ919");
            Veiculo veiculo4 = new Veiculo("IYO762");
            Veiculo veiculo5 = new Veiculo("AEC691");
            Veiculo veiculo6 = new Veiculo("HTJ571");

            // Adicionando veículos aos clientes;
            cliente1.addVeiculo(veiculo1);
            cliente2.addVeiculo(veiculo2);
            cliente3.addVeiculo(veiculo3);
            cliente4.addVeiculo(veiculo4);
            cliente5.addVeiculo(veiculo5);
            cliente6.addVeiculo(veiculo6);

            // Estacionando veículos;
            estacionamento1.estacionar(veiculo1.getPlaca());
            estacionamento1.estacionar(veiculo2.getPlaca());
            estacionamento2.estacionar(veiculo3.getPlaca());
            estacionamento2.estacionar(veiculo4.getPlaca());
            estacionamento3.estacionar(veiculo5.getPlaca());
            estacionamento3.estacionar(veiculo6.getPlaca());

            // Crie uma lista para armazenar as vagas;
            List<Vaga> vagas = new ArrayList<>();
            
            // Crie uma lista para armazenar os usos de vagas;
            List<UsoDeVaga> usos = new ArrayList<>();
            
            // Criar e adicionar vagas, usos e registros;
            int registrosPerClient = 10;
            for(int clienteIndex = 0; clienteIndex < 6; clienteIndex++){
                Cliente cliente = null;
                String placa = null;
                switch(clienteIndex){
                    case 0:
                        cliente = cliente1;
                        placa = veiculo1.getPlaca();
                        break;
                    case 1:
                        cliente = cliente2;
                        placa = veiculo2.getPlaca();
                        break;
                    case 2:
                        cliente = cliente3;
                        placa = veiculo3.getPlaca();
                        break;
                    case 3:
                        cliente = cliente4;
                        placa = veiculo4.getPlaca();
                        break;
                    case 4:
                        cliente = cliente5;
                        placa = veiculo5.getPlaca();
                        break;
                    case 5:
                        cliente = cliente6;
                        placa = veiculo6.getPlaca();
                        break;
                }
                for(int i = 0; i < registrosPerClient; i++){
                    Vaga vaga = new Vaga(i, i);
                    vagas.add(vaga);
                    UsoDeVaga uso = new UsoDeVaga(vaga, LocalDateTime.now(), 0, cliente);
                    usos.add(uso);
                }
            }

            // Crie um FileOutputStream para salvar os objetos em um arquivo binário;
            FileOutputStream fileOut = new FileOutputStream("dados.bin");

            // Crie um ObjectOutputStream para escrever objetos no arquivo;
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            // Escreva os objetos no arquivo;
            objectOut.writeObject(estacionamento1);
            objectOut.writeObject(cliente1);
            objectOut.writeObject(cliente2);
            objectOut.writeObject(estacionamento2);
            objectOut.writeObject(cliente3);
            objectOut.writeObject(cliente4);
            objectOut.writeObject(estacionamento3);
            objectOut.writeObject(cliente5);
            objectOut.writeObject(cliente6);

            // Adicona as vagas;
            for(Vaga vaga : vagas){
                objectOut.writeObject(vaga);
            }
            
            // Adiciona os usos de vagas;
            for(UsoDeVaga uso : usos){
                objectOut.writeObject(uso);
            }
            
            // Feche o ObjectOutputStream e o FileOutputStream;
            objectOut.close();
            fileOut.close();
        }catch(Exception e){e.printStackTrace();}
    }
}
