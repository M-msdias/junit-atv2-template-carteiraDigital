import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;


public class Pagamento {
    DigitalWallet digitalWallet;

    @ParameterizedTest
    @CsvSource({ "100.0, 30.0, true", "50.0, 80.0, false", "10.0, 10.0, true" })
    void pagamentoComCarteiraVerificadaENaoBloqueada(double inicial, double valor, boolean esperado) {
        digitalWallet = new DigitalWallet("Bob", inicial);
        digitalWallet.unlock();
        digitalWallet.verify();

        assumeFalse(digitalWallet.isLocked());
        assumeTrue(digitalWallet.isVerified());

        boolean result = digitalWallet.pay(valor);
        Assertions.assertEquals(esperado, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -100})
    void deveLancarExcecaoParaPagamentoInvalido(double valor) {
        digitalWallet = new DigitalWallet("Bob", 60);
        digitalWallet.unlock();
        digitalWallet.verify();

        assumeFalse(digitalWallet.isLocked());
        assumeTrue(digitalWallet.isVerified());


        assertThrows(IllegalArgumentException.class, () -> digitalWallet.pay(valor), "O pagamento deve ser válido.");
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        digitalWallet = new DigitalWallet("Bob", 60);
        assertThrows(IllegalStateException.class, () -> digitalWallet.pay(120));
    }
}