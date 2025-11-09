package space_invaders.sprites;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShotTest {
    @Test
    void testConstructorPosicionMedia() {
        // Caso 1: Posición (100, 200)
        Shot shot = new Shot(100, 200);

        // x_final = 100 + H_SPACE (6)
        assertEquals(106, shot.getX());
        // y_final = 200 - V_SPACE (1)
        assertEquals(199, shot.getY());
    }

    @Test
    void testConstructorBordeIzquierdo() {
        // Caso 2: Posición (0, 300)
        Shot shot = new Shot(0, 300);

        // x_final = 0 + H_SPACE (6)
        assertEquals(6, shot.getX());
        // y_final = 300 - V_SPACE (1)
        assertEquals(299, shot.getY());
    }


    @Test
    void testActMovimientoNormal() {
        // Caso 1: Posición inicial y=100
        Shot shot = new Shot(50, 100); // x no importa, y=99 (por el constructor)
        shot.setY(100); // Forzamos y=100 para la prueba

        shot.act(); // y = 100 - 4

        assertEquals(96, shot.getY());
        // assertEquals(true, shot.isVisible()); // Deberías comprobar que sigue visible
    }

    @Test
    void testActCruceLimite() {
        // Caso 3: Posición inicial y=3
        Shot shot = new Shot(50, 100);
        shot.setY(3); // Forzamos y=3

        shot.act(); // y = 3 - 4

        assertEquals(-1, shot.getY());
        // assertEquals(false, shot.isVisible()); // Deberías comprobar que desaparece
    }

    @Test
    void testActEnLimite() {
        // Caso 4: Posición inicial y=0
        Shot shot = new Shot(50, 100);
        shot.setY(0); // Forzamos y=0

        shot.act(); // y = 0 - 4

        assertEquals(-4, shot.getY());
        // assertEquals(false, shot.isVisible()); // Deberías comprobar que desaparece
    }
}
