import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.time.LocalDate;

public class UsoDeVagaGerarRelatorio implements Serializable{
    // Cliente tem acesso ao histórico de uso do estacionamento;
    public static void HistoricoUsoEstacionamento(Cliente clienteSelecionado){
        Map<String, Veiculo> veiculos = clienteSelecionado.getVeiculos();

        if (veiculos.isEmpty()){
            MyIO.println("O cliente não possui veículos.");
            return;
        }

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
    public static void HistoricoPorDatas(Cliente clienteSelecionado) {
        Map<String, Veiculo> veiculos = clienteSelecionado.getVeiculos();

        if (veiculos.isEmpty()) {
            MyIO.println("O cliente não possui veículos.");
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

    // Valor total arrecadado por um estacionamento;
    public static void TotalArrecadadoEstacionamento(Estacionamento estacionamento){
        double totalArrecadado = estacionamento.totalArrecadado();
        System.out.println("O total arrecadado pelo estacionamento " + estacionamento.getNome() + " é: " + totalArrecadado);
    }
}