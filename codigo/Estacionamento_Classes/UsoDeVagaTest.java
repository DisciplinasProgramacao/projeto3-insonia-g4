import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.Duration;

class UsoDeVagaTest {

    @Test
    void testValorPago() {
        Vaga vaga = new Vaga();
        UsoDeVaga uso = new UsoDeVaga(vaga);

        // Simulando uma estadia de 30 minutos
        LocalDateTime entrada = LocalDateTime.now().minusMinutes(30);
        uso.setEntrada(entrada);
        uso.sair();
        double valorEsperado = 4.0;
        assertEquals(valorEsperado, uso.getValorPago(), 0.01);

        // Simulando uma estadia de 10 horas
        LocalDateTime outraEntrada = LocalDateTime.now().minusHours(10);
        uso.setEntrada(outraEntrada);
        uso.sair();
        valorEsperado = 50.0;
        assertEquals(valorEsperado, uso.getValorPago(), 0.01);

        // Simulando uma estadia de menos de 15 minutos
        LocalDateTime terceiraEntrada = LocalDateTime.now().minusMinutes(10);
        uso.setEntrada(terceiraEntrada);
        uso.sair();
        valorEsperado = 0.0;
        assertEquals(valorEsperado, uso.getValorPago(), 0.01);
    }
}
