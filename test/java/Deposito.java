import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.DigitalWallet;


class Deposito {
        @ParameterizedTest
        @ValueSource(doubles = {0.01, 10.0, 20.5, 999.99})
        void deveDepositarValoresValidos(double amount) {
            DigitalWallet digitalWallet = new DigitalWallet("Bob", 0);
            digitalWallet.deposit(amount);
            assertEquals(digitalWallet.getBalance(), amount, 0.0001, "O valor para depositar tem que ser válido");
        }

        @ParameterizedTest
        @ValueSource(doubles = {0, -10, -20})
        void deveLancarExcecaoParaDepositoInvalido(double amount) {
            DigitalWallet digitalWallet = new DigitalWallet("Bob", 0.0);
            assertThrows(IllegalArgumentException.class, () -> digitalWallet.deposit(amount), "Depósito com valor menor igual a zero deve lançar exceção");
        }
    }