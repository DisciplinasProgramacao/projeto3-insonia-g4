import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstacionamentoTest {

    Cliente cliente;
    Veiculo veiculo;
    Estacionamento estacionamento;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("123", "João");
        veiculo = new Veiculo("ABC-1234");
        estacionamento = new Estacionamento();
        estacionamento.addCliente(cliente);
        cliente.addVeiculo(veiculo);
    }

    @Test
    void testArrecadacaoNoMes() {
        double arrecadacaoEsperada = 100.0; // Valor fictício para o teste
        assertEquals(arrecadacaoEsperada, estacionamento.arrecadacaoNoMes(10), 0.01);
    }

    @Test
    void testTop5Clientes() {
        String top5Esperado = "Cliente 1 - R$ 100.0\nCliente 2 - R$ 90.0\nCliente 3 - R$ 80.0\nCliente 4 - R$ 70.0\nCliente 5 - R$ 60.0\n";
        assertEquals(top5Esperado, estacionamento.top5Clientes(10));
    }

    @Test
    void testTotalArrecadado() {

        try {
            estacionamento.estacionar("ABC-1234");
        } catch (Exception e) {
            fail("Exceção não deveria ter sido lançada.");
        }


        double valorEsperado = veiculo.getPrecoPorHora();

        assertEquals(valorEsperado, estacionamento.totalArrecadado(), 0.01);
    }

    @Test
    void testValorMedioPorUso() {

        try {
            estacionamento.estacionar("ABC-1234");
        } catch (Exception e) {
            fail("Exceção não deveria ter sido lançada.");
        }

        double valorEsperado = veiculo.getPrecoPorHora();

        assertEquals(valorEsperado, estacionamento.valorMedioPorUso(), 0.01);
    }
}
