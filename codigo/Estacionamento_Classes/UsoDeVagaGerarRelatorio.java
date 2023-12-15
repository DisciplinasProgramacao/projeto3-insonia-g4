import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class UsoDeVagaGerarRelatorio implements Serializable{

    // Cliente tem acesso ao histórico de uso do estacionamento;
    public static void HistoricoUsoEstacionamento(List<Cliente> clientes){
        // Inserir Clientes;
        MyIO.println("Digite o ID do cliente: ");
        String idCliente1 = MyIO.readLine();
        Cliente clienteSelecionado = null;
        for (Cliente cliente1 : clientes) {
            if (cliente1.getID().equals(idCliente1)) {
                clienteSelecionado = cliente1;
                break;
            }
        }
        // Verifica se o Cliente Existe;
        if(clienteSelecionado == null){
            MyIO.println("Cliente nao existe.");
            return;
        }
        Map<String, Veiculo> veiculos = clienteSelecionado.getVeiculos();
        // Caso o Cliente não possua veículos;
        if (veiculos.isEmpty()){
            MyIO.println("O cliente nao possui veiculos.");
            return;
        }
        // Imprime o Histórico;
        MyIO.println("Historico de uso do estacionamento para o cliente " + clienteSelecionado.getNome());
        MyIO.println("---------------------------------------------------------------");
        for (Veiculo veiculo : veiculos.values()) {
            MyIO.println("Placa do veiculo: " + veiculo.getPlaca());
            MyIO.println("Data e Hora      |   Vaga   |   Valor Pago");
            UsoDeVaga[] usos = veiculo.getUsos();
            if (usos != null) {
                for (UsoDeVaga uso : usos) {
                    if (uso != null) {
                        MyIO.println(uso.getEntrada() + " | " + uso.getVaga().getId() + " | " + uso.getValorPago());
                    }
                }
            }
            MyIO.println("---------------------------------------------------------------");
        }
    }

    // Método auxiliar para verificar se uma data está dentro de um interval;
    private static boolean DentroDaData(LocalDateTime dateToCheck, LocalDate startDate, LocalDate endDate) {
        LocalDate date = dateToCheck.toLocalDate();
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    // Cliente tem acesso a um filtro de histórico por datas;
    public static void HistoricoPorDatas(List<Cliente> clientes){
        // Inserir Clientes;
        MyIO.println("Digite o ID do cliente: ");
        String idCliente1 = MyIO.readLine();
        Cliente clienteSelecionado = null;
        for (Cliente cliente1 : clientes) {
            if (cliente1.getID().equals(idCliente1)) {
                clienteSelecionado = cliente1;
                break;
            }
        }
        // Verifica se o Cliente Existe;
        if(clienteSelecionado == null){
            MyIO.println("Cliente nao existe.");
            return;
        }
        Map<String, Veiculo> veiculos = clienteSelecionado.getVeiculos();
        // Caso o Cliente não possua veículos;
        if (veiculos.isEmpty()){
            MyIO.println("O cliente nao possui veiculos.");
            return;
        }

        MyIO.println("Digite a data inicial (no formato yyyy-MM-dd): ");
        LocalDate dataInicial = LocalDate.parse(MyIO.readLine());

        MyIO.println("Digite a data final (no formato yyyy-MM-dd): ");
        LocalDate dataFinal = LocalDate.parse(MyIO.readLine());

        MyIO.println("Historico de uso do estacionamento para o cliente " + clienteSelecionado.getNome());
        MyIO.println("Periodo: " + dataInicial + " - " + dataFinal);
        MyIO.println("---------------------------------------------------------------");

        for (Veiculo veiculo : veiculos.values()) {
            MyIO.println("Placa do veiculo: " + veiculo.getPlaca());
            MyIO.println("Data e Hora      |   Vaga   |   Valor Pago");
            UsoDeVaga[] usos = veiculo.getUsos();
            if (usos != null) {
                for (UsoDeVaga uso : usos) {
                    if (uso != null && DentroDaData(uso.getEntrada(), dataInicial, dataFinal)) {
                        MyIO.println(uso.getEntrada() + " | " + uso.getVaga().getId() + " | " + uso.getValorPago());
                    }
                }
            }
            MyIO.println("---------------------------------------------------------------");
        }
    }

    // Relatórios de Utilização: ordem crescente de data ou decrescente de valor;
    public static void RelatorioUtilizacao(){
        return;
    }

    // Valor total arrecadado por um estacionamento;
    public static void TotalArrecadadoEstacionamento(List<Estacionamento> estacionamentos){
        MyIO.println("Digite o Nome do Estacionamento: ");
        String nomeEstacionamento = MyIO.readLine();
        Estacionamento estacionamentoAtual = null;
        for (Estacionamento estacionamento1 : estacionamentos) {
            if (estacionamento1.getNome().equals(nomeEstacionamento)) {
                estacionamentoAtual = estacionamento1;
                break;
            }
        }
        if(estacionamentoAtual == null){
            MyIO.println("Estacionamento nao existe.");
            return;
        }
        double totalArrecadado = estacionamentoAtual.totalArrecadado();
        System.out.println("O total arrecadado pelo estacionamento " + estacionamentoAtual.getNome() + " e: " + totalArrecadado);
    }

    // Valor arrecadado em um mês;
    public static void ArrecadadoMes(){
        return;
    }
}