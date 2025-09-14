import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;



class Estorno {
    DigitalWallet digitalWallet;
    static Stream<Arguments> valoresEstorno() {
        return Stream.of(
            Arguments.of(100.0, 10.0, 110.0),
            Arguments.of(0.0,   5.0,   5.0),
            Arguments.of(50.0,  0.01, 50.01)
        );
    }

    @ParameterizedTest
    @MethodSource("valoresEstorno")
    void refundComCarteiraValida(double inicial, double valor, double saldoEsperado) {
        digitalWallet = new DigitalWallet("Bob", inicial);
        digitalWallet.verify();
        digitalWallet.unlock();

        assumeTrue(digitalWallet.isVerified());
        assumeFalse(digitalWallet.isLocked());

        digitalWallet.refund(valor);

        assertEquals(saldoEsperado, digitalWallet.getBalance());
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, 0})
    void deveLancarExcecaoParaRefundInvalido(double valor) {
        digitalWallet = new DigitalWallet("Bob", 600);
        digitalWallet.verify();
        digitalWallet.unlock();

        assumeTrue(digitalWallet.isVerified());
        assumeFalse(digitalWallet.isLocked());

        assertThrows(IllegalArgumentException.class, () -> digitalWallet.refund(valor));
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        digitalWallet = new DigitalWallet("Bob", 60);

        assertThrows(IllegalStateException.class, () -> digitalWallet.refund(6));
        
    }
}