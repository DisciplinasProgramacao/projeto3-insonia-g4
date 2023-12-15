import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente cliente;
    private Veiculo veiculo1;
    private Veiculo veiculo2;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente("Cliente Test", "123", Modalidade.HORISTA);
        veiculo1 = new Veiculo("ABC123");
        veiculo2 = new Veiculo("XYZ789");
        assertDoesNotThrow(() -> cliente.addVeiculo(veiculo1));
        assertDoesNotThrow(() -> cliente.addVeiculo(veiculo2));
    }

    @Test
    public void testPossuiVeiculo() {
        assertEquals(veiculo1, cliente.possuiVeiculo("ABC123"));
    }

    @Test
    public void testTotalDeUsos() {
        // Supondo que o método totalDeUsos em Veiculo retorne um valor específico;
        assertEquals(veiculo1.getTotalUsos() + veiculo2.getTotalUsos(), cliente.totalDeUsos());
    }

    @Test
    public void testArrecadadoPorVeiculo() {
        // Supondo que o método totalArrecadado em Veiculo retorne um valor específico;
        assertEquals(veiculo1.totalArrecadado(), cliente.arrecadadoPorVeiculo("ABC123"), 0.001);
    }

    @Test
    public void testArrecadadoTotal() {
        // Supondo que o método totalArrecadado em Veiculo retorne um valor específico;
        assertEquals(veiculo1.totalArrecadado() + veiculo2.totalArrecadado(), cliente.arrecadadoTotal(), 0.001);
    }
}
