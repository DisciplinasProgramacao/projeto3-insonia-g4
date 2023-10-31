import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ClienteTest{
    private Cliente cliente;
    private Veiculo veiculo1;
    private Veiculo veiculo2;

    @Before
    public void setUp(){
        cliente = new Cliente("Cliente Test", "123");
        veiculo1 = new Veiculo("ABC123");
        veiculo2 = new Veiculo("XYZ789");

        cliente.addVeiculo(veiculo1);
        cliente.addVeiculo(veiculo2);
    }

    //Testa a função addVeiculo();
    @Test
    public void testAddVeiculo(){
        assertTrue(cliente.getVeiculos().containsKey(veiculo1.getPlaca()));
        assertEquals(2, cliente.getVeiculos().size());

        Veiculo veiculo3 = new Veiculo("DEF456");
        cliente.addVeiculo(veiculo3);
        assertEquals(3, cliente.getVeiculos().size());
    }

    //Testa a função possuiVeiculo();
    @Test
    public void testPossuiVeiculo(){
        Veiculo foundVeiculo = cliente.possuiVeiculo("ABC123");
        assertEquals(veiculo1, foundVeiculo);
    }

    //Testa a função totalDeUsos();
    @Test
    public void testTotalDeUsos(){
        //Supondo que o método totalDeUsos em Veiculo retorne um valor específico;
        assertEquals(veiculo1.totalDeUsos() + veiculo2.totalDeUsos(), cliente.totalDeUsos());
    }

    //Testa a função arrecadadoPorVeiculo();
    @Test
    public void testArrecadadoPorVeiculo() {
        // Supondo que o método totalArrecadado em Veiculo retorne um valor específico;
        assertEquals(veiculo1.totalArrecadado(), cliente.arrecadadoPorVeiculo("ABC123"), 0.001);
    }

    //Testa a função arrecadadoTotal();
    @Test
    public void testArrecadadoTotal() {
        // Supondo que o método totalArrecadado em Veiculo retorne um valor específico
        assertEquals(veiculo1.totalArrecadado() + veiculo2.totalArrecadado(), cliente.arrecadadoTotal(), 0.001);
    }

    //Testa a função arrecadadoNoMes();
    @Test
    public void testArrecadadoNoMes() {
        // Supondo que o método arrecadadoNoMes em Veiculo retorne um valor específico
        assertEquals(veiculo1.arrecadadoNoMes(5) + veiculo2.arrecadadoNoMes(5), cliente.arrecadadoNoMes(5), 0.001);
    }
}