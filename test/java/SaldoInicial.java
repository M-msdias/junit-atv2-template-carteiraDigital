import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;

class SaldoInicial {
        DigitalWallet digitalWallet;
    @Test
    void deveConfigurarSaldoInicialCorreto() {
        digitalWallet = new DigitalWallet("Bob",50);
        assertEquals ( 50, digitalWallet.getBalance());
    }

     @Test
        void deveLancarExcecaoParaSaldoInicialNegativo() {
            assertThrows(IllegalArgumentException.class, () -> new DigitalWallet("Bob", -30));
    
           
        }
    }