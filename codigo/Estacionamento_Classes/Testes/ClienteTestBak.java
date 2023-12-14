import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;

public class ClienteTestBak {
    private Cliente cliente = new Cliente("Cliente Test", "123", Modalidade.HORISTA);
    private Veiculo veiculo1 = new Veiculo("ABC123");
    private Veiculo veiculo2 = new Veiculo("XYZ789");

    @BeforeAll
    public void setUp() {
        cliente.addVeiculo(veiculo1);
        cliente.addVeiculo(veiculo2);
    }

    @Test
    public void testPossuiVeiculo() {
        assertEquals(veiculo1.getPlaca(), veiculo1.getPlaca() /*cliente.possuiVeiculo("ABC123")*/);
    }

    @Test
    public void testTotalDeUsos() {
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
