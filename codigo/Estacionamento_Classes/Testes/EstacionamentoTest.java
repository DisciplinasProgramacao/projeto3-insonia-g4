import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class EstacionamentoTest {

    static List<Estacionamento> estacionamentos = new ArrayList<>();
    private Estacionamento estacionamento;
    private Cliente cliente;

    @BeforeEach
    void setUp(){
        estacionamento = new Estacionamento("Nome", 5, 10);
        cliente = new Cliente("Cliente1", "ID1", Modalidade.HORISTA);
        estacionamento.addCliente(cliente);
        estacionamentos.add(estacionamento);
    }

    @Test
    void testArrecadacaoMediaClientesHoristas() {
        // Considerando que o método arrecadacaoMediaClientesHoristas está correto;
        double arrecadacaoMedia = Estacionamento.arrecadacaoMediaClientesHoristas(1, estacionamentos);
        assertEquals(0.0, arrecadacaoMedia);
    }

    @Test
    void testEstacionar() {
        Veiculo veiculo = new Veiculo("ABC123");
        cliente.addVeiculo(veiculo);
        Vaga vaga = new Vaga(1, 1);
        assertDoesNotThrow(() -> estacionamento.estacionar(veiculo, estacionamento, cliente, vaga));
    }

    @Test
    void testTotalArrecadado() {
        // Considerando que o método totalArrecadado está correto;
        double totalArrecadado = estacionamento.totalArrecadado();
        assertEquals(0.0, totalArrecadado);
    }

    @Test
    void testArrecadacaoNoMes() {
        // Considerando que o método arrecadacaoNoMes está correto;
        double arrecadacaoNoMes = estacionamento.arrecadacaoNoMes(1);
        assertEquals(0.0, arrecadacaoNoMes);
    }

    @Test
    void testMediaClientesMensal() {
        // Considerando que o método mediaClientesMensal está correto;
        double mediaClientesMensal = estacionamento.mediaClientesMensal();
        assertEquals(0.0, mediaClientesMensal);
    }

    @Test
    public static void testTop5Clientes() {
        // Considerando que o método top5Clientes está correto;
        String top5Clientes = Estacionamento.top5Clientes(1, estacionamentos);
        assertEquals("", top5Clientes);
    }
}