import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class ClienteTest {
    private Cliente cliente;
    private Veiculo veiculo1;
    private Veiculo veiculo2;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente("Cliente Test", "123");
        veiculo1 = new Veiculo("ABC123");
        veiculo2 = new Veiculo("XYZ789");

        cliente.addVeiculo(veiculo1);
        cliente.addVeiculo(veiculo2);
    }

    @Test
    public void testPossuiVeiculo() {
        assertEquals(veiculo1, cliente.possuiVeiculo("ABC123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPossuiVeiculoNaoEncontrado() {
        cliente.possuiVeiculo("PLACA_INVALIDA");
    }

    @Test
    public void testTotalDeUsos() {
        // Supondo que o método totalDeUsos em Veiculo retorne um valor específico
        assertEquals(veiculo1.totalDeUsos() + veiculo2.totalDeUsos(), cliente.totalDeUsos());
    }

    @Test
    public void testArrecadadoPorVeiculo() {
        // Supondo que o método totalArrecadado em Veiculo retorne um valor específico
        assertEquals(veiculo1.totalArrecadado(), cliente.arrecadadoPorVeiculo("ABC123"), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArrecadadoPorVeiculoNaoEncontrado() {
        cliente.arrecadadoPorVeiculo("PLACA_INVALIDA");
    }

    @Test
    public void testArrecadadoTotal() {
        // Supondo que o método totalArrecadado em Veiculo retorne um valor específico
        assertEquals(veiculo1.totalArrecadado() + veiculo2.totalArrecadado(), cliente.arrecadadoTotal(), 0.001);
    }

    @Test
    public void testArrecadadoNoMes() {
        // Supondo que o método arrecadadoNoMes em Veiculo retorne um valor específico
        assertEquals(veiculo1.arrecadadoNoMes(5) + veiculo2.arrecadadoNoMes(5), cliente.arrecadadoNoMes(5), 0.001);
    }
}
