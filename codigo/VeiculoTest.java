import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VeiculoTest {

    private Veiculo veiculo;
    private Vaga vaga;

    @Before
    public void setUp() {
        veiculo = new Veiculo("ABC123");
        vaga = new Vaga(1); // Supondo que você também tenha uma classe Vaga
    }

    @Test
    public void testEstacionar() {
        veiculo.estacionar(vaga);
        assertEquals(1, veiculo.totalDeUsos());
    }

    @Test
    public void testSair() {
        veiculo.estacionar(vaga);
        double valorPago = veiculo.sair();
        // Faça os testes com o valor pago esperado
        // Exemplo: assertEquals(10.0, valorPago, 0.01); // Valor esperado com tolerância de 0.01
    }

    @Test
    public void testTotalArrecadado() {
        veiculo.estacionar(vaga);
        veiculo.estacionar(vaga);
        double totalArrecadado = veiculo.totalArrecadado();
        // Faça os testes com o valor total arrecadado esperado
        // Exemplo: assertEquals(20.0, totalArrecadado, 0.01);
    }

    @Test
    public void testArrecadadoNoMes() {
        veiculo.estacionar(vaga);
        veiculo.estacionar(vaga);
        double totalArrecadadoNoMes = veiculo.arrecadadoNoMes(10); // Supondo o mês 10
        // Faça os testes com o valor total arrecadado no mês esperado
        // Exemplo: assertEquals(20.0, totalArrecadadoNoMes, 0.01);
    }
}
