import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        //Criando uma Vaga
        Vaga vaga1 = new Vaga(0, 0);
        
        // Criando um estacionamento
        Estacionamento estacionamento = new Estacionamento("Meu Estacionamento", 5, 10);

        // Criando clientes
        Cliente cliente1 = new Cliente("Cliente 1", "12345");
        Cliente cliente2 = new Cliente("Cliente 2", "54321");

        // Adicionando clientes ao estacionamento
        estacionamento.addCliente(cliente1);
        estacionamento.addCliente(cliente2);

        // Criando veículos
        Veiculo veiculo1 = new Veiculo("ABC123");
        Veiculo veiculo2 = new Veiculo("XYZ789");

        // Adicionando veículos aos clientes
        cliente1.addVeiculo(veiculo1);
        cliente2.addVeiculo(veiculo2);

        // Estacionando veículos
        estacionamento.estacionar(veiculo1.getPlaca());

        // Contratando serviços
        UsoDeVaga uso = new UsoDeVaga(vaga1, LocalDateTime.now());
        uso.contratarServico();

        // Saindo do estacionamento
        LocalDateTime horaSaida = LocalDateTime.now().plusHours(2); // Simulando 2 horas depois da entrada
        uso.sair(horaSaida);

        // Imprimindo resultados
        System.out.println("Valor Pago: " + uso.getValorPago());
        System.out.println("Total Arrecadado pelo Cliente 1: " + cliente1.arrecadadoTotal());
        System.out.println("Total de Usos pelo Cliente 2: " + cliente2.totalDeUsos());

        // Exemplo de uso do RegistroHistorico
        RegistroHistorico registro = new RegistroHistorico(cliente1, veiculo1.getPlaca(), uso.getValorPago(), uso.getValorPago());
    }
}
