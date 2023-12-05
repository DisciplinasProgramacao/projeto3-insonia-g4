import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VagaTest {

    private Vaga vaga;

    @BeforeEach
    public void setUp() {
        vaga = new Vaga(1, 1);
    }

    @Test
    public void testGetHistorico() {
        assertNotNull(vaga.getHistorico());
        assertTrue(vaga.getHistorico().isEmpty());
    }

    @Test
    public void testGetUsuario() {
        assertNull(vaga.getUsuario());
    }

    @Test
    public void testGetId() {
        assertEquals("Vaga 11", vaga.getId());
    }

    @Test
    public void testGetFila() {
        assertEquals(1, vaga.getFila());
    }

    @Test
    public void testIsDisponivel() {
        assertTrue(vaga.isDisponivel());
        vaga.estacionar(new Cliente("Cliente", "123", Modalidade.HORISTA, null));
        assertFalse(vaga.isDisponivel());
        vaga.sair();
        assertTrue(vaga.isDisponivel());
    }

    @Test
    public void testEstacionar() {
        Cliente cliente = new Cliente("Cliente", "123", Modalidade.HORISTA, null);
        assertTrue(vaga.estacionar(cliente));
        assertFalse(vaga.isDisponivel());
        assertEquals(cliente, vaga.getUsuario());
    }

    @Test
    public void testSair() {
        Cliente cliente = new Cliente("Cliente", "123", Modalidade.HORISTA, null);
        vaga.estacionar(cliente);
        assertTrue(vaga.sair());
        assertTrue(vaga.isDisponivel());
        assertNull(vaga.getUsuario());
        assertEquals(1, vaga.getHistorico().size());
    }

    @Test
    public void testGetEntrada() {
        assertNotNull(vaga.getEntrada());
    }
}
