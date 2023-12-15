import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.io.Serializable;

public class RelatorioCompleto implements Observer, Serializable {
    private Map<Cliente, Double> top5Clientes;
    private double arrecadacaoMensal;

    // Construtor;
    public RelatorioCompleto() {
        this.top5Clientes = new LinkedHashMap<>();
        this.arrecadacaoMensal = 0.0;
    }

    // Getters;
    public Map<Cliente, Double> getTop5Clientes() {
        return top5Clientes;
    }
    public double getArrecadacaoMensal() {
        return arrecadacaoMensal;
    }

    @Override
    public void updateArrecadacao(Cliente cliente, double novaArrecadacao) {
        top5Clientes.put(cliente, novaArrecadacao);
        top5Clientes = sortByValue(top5Clientes);

        if (top5Clientes.size() > 5) {
            Iterator<Map.Entry<Cliente, Double>> iterator = top5Clientes.entrySet().iterator();
            Map.Entry<Cliente, Double> entry = null;
            for (int i = 0; i < 5 && iterator.hasNext(); i++) {
                entry = iterator.next();
            }
            if (entry != null) {
                iterator.remove();
            }
        }
        arrecadacaoMensal += novaArrecadacao;
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    //***********Código para os relátorios***********
    
    // 0 - Gerar Relatorio;
    public static void gerarRelatorio(List<Veiculo> veiculos) {
        Veiculo veiculo = null;
        MyIO.println("Digite a placa do veiculo: ");
        String placa = MyIO.readLine();
        for(Veiculo veiculo1 : veiculos){
            if(veiculo1.getPlaca().equals(placa)){
                veiculo = veiculo1;
                break;
            }
        }
        if(veiculo == null){
            MyIO.println("Erro - Veiculo nao encontrado.");
            return;
        }
        veiculo.ordenarUsosPorData();
        System.out.println("Relatório de Usos de Vaga para o Veículo de Placa: " + veiculo.getPlaca());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (int i = 0; i < veiculo.getTotalUsos(); i++) {
            UsoDeVaga uso = veiculo.getUsos()[i];
            Date data = uso.getData();
            String dataFormatada = dateFormat.format(data);
            System.out.println("Data: " + dataFormatada + " - Vaga: " + uso.getVaga().toString());
        }
    }

    // 1 - Cliente tem acesso ao histórico de uso do estacionamento;
    public static void gerarHistoricoEstacionamento(List<Estacionamento> estacionamentos){
        MyIO.println("Digite o nome do estacionamento: ");
        String nomeEstacionamento = MyIO.readLine();
        Estacionamento estacionamentoAtual = null;
        for(Estacionamento estacionamento1 : estacionamentos){
            if (estacionamento1.getNome().equals(nomeEstacionamento)){
                estacionamentoAtual = estacionamento1;
                break;
            }
        }
        if(estacionamentoAtual == null){
            MyIO.println("Erro - Estacionamento nao encontrado.");
            return;
        }
        Cliente[] clientes = estacionamentoAtual.getClientes();
        for (Cliente cliente : clientes){
            List<Veiculo> veiculos = cliente.getVeiculosAsList();
            for(Veiculo veiculo : veiculos){
                UsoDeVaga[] usos = veiculo.getUsos();
                //IMPORTANTE;
                //UsoDeVaga não possui variável que marca qual é o seu Estacionamento;
            }
        }
    }

    // Método auxiliar para verificar se uma data está dentro de um interval;
    private static boolean DentroDaData(LocalDateTime dateToCheck, LocalDate startDate, LocalDate endDate) {
        LocalDate date = dateToCheck.toLocalDate();
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    // 2 - Cliente tem acesso a um filtro de histórico por datas;
    public static void HistoricoPorDatas(List<Cliente> clientes){
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

        MyIO.println("Digite a data inicial (no formato yyyy/MM/dd): ");
        String inputInicial = MyIO.readLine();
        String dataInicialString = inputInicial.replace('/', '-');
        LocalDate dataInicial = LocalDate.parse(dataInicialString);
        MyIO.println("Digite a data final (no formato yyyy/MM/dd): ");
        String inputFinal = MyIO.readLine();
        String dataFinalString = inputFinal.replace('/', '-');
        LocalDate dataFinal = LocalDate.parse(dataFinalString);

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

    // 3 - Relatórios de Utilização: ordem crescente de data ou decrescente de valor;
    public static void RelatorioUtilizacao(){
        return;
    }

    // 4 - Valor total arrecadado por um estacionamento;
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
        MyIO.println("O total arrecadado pelo estacionamento " + estacionamentoAtual.getNome() + " e: " + totalArrecadado);
    }

    // 5 - Valor arrecadado em um mes;
    public static void ArrecadadoMes(){
        return;
    }

    // 6 - Valor médio por utilização do estacionamento;
    public static void MediaUtilizacaoEstacionamento(){
        return;
    }

    // 7 - Ranking dos 5 clientes que mais geraram arrecadação em um mês;
    public static void Ranking5Clientes(){
        return;
    }

    // 8 - Relatório de arrecadação de todos os estacionamentos, em ordem decrescente;
    public static void RelatorioArrecadacaoDecrescente(){
        return;
    }

    /* 
    * 9 - Quantas vezes os clientes mensalistas utilizaram um estacionamento 
    * no mês corrente, em valores absolutos e em porcentagem de uso.
    */
    public static void ClientesMensalistasMesCorrente(){
        return;
    }

    // 10 - Arrecadação média gerada pelos clientes horistas no mês atual;
    public static void ArrecadacaoMediaHoristasAtual(){
        return;
    }
}