import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class UsoDeVagaTest {
    private Vaga vaga;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        vaga = new Vaga(1, 1);
        cliente = new Cliente("Cliente1", "ID1", Modalidade.HORISTA);
    }

    @Test
    void testGetEntrada() {
        LocalDateTime entrada = LocalDateTime.now();
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada, 1, cliente);
        assertEquals(entrada, uso.getEntrada());
    }

    @Test
    void testGetSaida() {
        LocalDateTime entrada = LocalDateTime.now();
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada, 1, cliente);
        // Simulando uma saída após 30 minutos
        LocalDateTime saida = entrada.plusMinutes(30);
        uso.sair(saida);
        assertEquals(saida, uso.getSaida());
    }

    @Test
    void testGetValorPago() {
        UsoDeVaga uso = new UsoDeVaga(vaga, LocalDateTime.now(), 1, cliente);

        // Simulando uma estadia de 30 minutos
        LocalDateTime saida = LocalDateTime.now().plusMinutes(30);
        uso.sair(saida);
        double valorEsperado = 9.0;
        assertEquals(valorEsperado, uso.getValorPago(), 0.01);

        // Simulando uma estadia de 10 horas
        saida = LocalDateTime.now().plusHours(10);
        uso.sair(saida);
        valorEsperado = 55.0;
        assertEquals(valorEsperado, uso.getValorPago(), 0.01);

        // Simulando uma estadia de menos de 15 minutos
        saida = LocalDateTime.now().plusMinutes(10);
        uso.sair(saida);
        valorEsperado = 5.0;
        assertEquals(valorEsperado, uso.getValorPago(), 0.01);
    }

    @Test
    void testGetMes() {
        LocalDateTime entrada = LocalDateTime.now();
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada, 1, cliente);
        assertEquals(entrada.getMonthValue(), uso.getMes());
    }

    @Test
    void testGetData() {
        LocalDateTime entrada = LocalDateTime.now();
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada, 1, cliente);
        assertNotNull(uso.getData());
    }

    @Test
    void testGetVaga() {
        UsoDeVaga uso = new UsoDeVaga(vaga, LocalDateTime.now(), 1, cliente);
        assertEquals(vaga, uso.getVaga());
    }

    @Test
    void testPermissaoSaida() {
        UsoDeVaga uso = new UsoDeVaga(vaga, LocalDateTime.now(), 2, cliente);

        // Simulando uma saída após 10 minutos (não atende ao tempo mínimo)
        LocalDateTime saida = LocalDateTime.now().plusMinutes(10);
        assertFalse(uso.permissaoSaida(saida));

        // Simulando uma saída após 30 minutos (atende ao tempo mínimo)
        saida = LocalDateTime.now().plusMinutes(60);
        assertTrue(uso.permissaoSaida(saida));
    }
}
