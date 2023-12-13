import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstacionamentoTest {

    private Estacionamento estacionamento;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        estacionamento = new Estacionamento("Nome", 5, 10);
        cliente = new Cliente("Cliente1", "ID1", Modalidade.HORISTA);
        estacionamento.addCliente(cliente);
    }


    @Test
    void testArrecadacaoMediaClientesHoristas() {
        // Considerando que o método arrecadacaoMediaClientesHoristas está correto
        double arrecadacaoMedia = estacionamento.arrecadacaoMediaClientesHoristas(1);
        assertEquals(0.0, arrecadacaoMedia);
    }


    @Test
    void testEstacionar() {
        Veiculo veiculo = new Veiculo("ABC123");
        cliente.addVeiculo(veiculo);

        assertDoesNotThrow(() -> estacionamento.estacionar("ABC123"));
    }


    @Test
    void testTotalArrecadado() {
        // Considerando que o método totalArrecadado está correto
        double totalArrecadado = estacionamento.totalArrecadado();
        assertEquals(0.0, totalArrecadado);
    }

    @Test
    void testArrecadacaoNoMes() {
        // Considerando que o método arrecadacaoNoMes está correto
        double arrecadacaoNoMes = estacionamento.arrecadacaoNoMes(1);
        assertEquals(0.0, arrecadacaoNoMes);
    }

    @Test
    void testMediaClientesMensal() {
        // Considerando que o método mediaClientesMensal está correto
        double mediaClientesMensal = estacionamento.mediaClientesMensal();
        assertEquals(0.0, mediaClientesMensal);
    }


    @Test
    void testTop5Clientes() {
        // Considerando que o método top5Clientes está correto
        String top5Clientes = estacionamento.top5Clientes(1);
        assertEquals("", top5Clientes);
    }
}
