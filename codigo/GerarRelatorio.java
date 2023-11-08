import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class GerarRelatorio {
    public static void gerarRelatorio(Veiculo veiculo) {
        veiculo.ordenarUsosPorData(); // Garante que os usos de vaga estejam ordenados por data

        System.out.println("Relatório de Usos de Vaga para o Veículo de Placa: " + veiculo.getPlaca());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        for (int i = 0; i < veiculo.totalUsos; i++) {
            UsoDeVaga uso = veiculo.usos[i];
            Date data = uso.getData();
            String dataFormatada = dateFormat.format(data);
            System.out.println("Data: " + dataFormatada + " - Vaga: " + uso.getVaga().toString());
        }
    }
