import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VeiculoTest {
    private Veiculo veiculo;
    private Vaga vaga;

    @BeforeEach
    public void setUp() {
        vaga = new Vaga(1, 1);
        veiculo = new Veiculo("1234567");
        Cliente cliente = new Cliente("Joao", "1", Modalidade.MENSALISTA);
        veiculo.estacionar(vaga, 1, cliente );
    }

    @Test
    public void testSair() {
        assertEquals(0.0, veiculo.sair());
    }

    @Test
    public void testTotalArrecadado() {
        assertEquals(0, veiculo.totalArrecadado());
    }

    @Test
    public void testArrecadadoNoMes() {
        int mes = 10;
        assertEquals(0, veiculo.arrecadadoNoMes(mes));
    }

    @Test
    public void testTotalDeUsos() {
        assertEquals(1, veiculo.totalDeUsos());
    }
}