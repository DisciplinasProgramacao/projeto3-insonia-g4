   import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VagaTest {
        private Vaga vaga;


        @BeforeEach
        public void setUp() {
        vaga = new Vaga(1, 1);
        Cliente cliente = new Cliente("Nome do Cliente", "123456");
        vaga.estacionar(cliente);
    }

        @Test
        public void testCalcularValorCobranca() {
            // Valor esperado com base na lógica de cálculo, 40.0 para representar 40 minutos
            assertEquals(12, vaga.calcularValorCobranca(40), 0.01); // (0.01) para números de ponto flutuante
        }

        @Test
        public void testEstacionar(){
            assertEquals(false,vaga.estacionar(null)); 
        }

        @Test
        public void testDisponivel(){
            assertEquals(false,vaga.isDisponivel());
        }
}
