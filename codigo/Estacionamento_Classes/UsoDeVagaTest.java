import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class UsoDeVagaTest {

    @Test
    void testValorPago() {
        Vaga vaga = new Vaga(1, 1);
        UsoDeVaga uso = new UsoDeVaga(vaga, LocalDateTime.now());

        // Simulando uma estadia de 30 minutos
        LocalDateTime saida = LocalDateTime.now().plusMinutes(30);
        uso.sair(saida);
        double valorEsperado = 4.0;
        assertEquals(valorEsperado, uso.getValorPago(), 0.01);

        // Simulando uma estadia de 10 horas
        saida = LocalDateTime.now().plusHours(10);
        uso.sair(saida);
        valorEsperado = 50.0;
        assertEquals(valorEsperado, uso.getValorPago(), 0.01);

        // Simulando uma estadia de menos de 15 minutos
        saida = LocalDateTime.now().plusMinutes(10);
        uso.sair(saida);
        valorEsperado = 0.0;
        assertEquals(valorEsperado, uso.getValorPago(), 0.01);
    }
}
