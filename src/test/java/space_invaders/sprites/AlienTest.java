package space_invaders.sprites;

import static org.junit.jupiter.api.Assertions.*;

import main.Commons;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

class AlienTest {
    //método act

    @Test
    void testMoverIzquierda() {
        Alien a = new Alien(100, 50);
        a.act(10); // direction > 0 → izquierda
        assertEquals(90, a.getX()); // x = 100 - 10
    }

    @Test
    void testSinMovimiento() {
        Alien a = new Alien(100, 50);
        a.act(0); // direction = 0 → no cambia
        assertEquals(100, a.getX());
    }

    @Test
    void testMoverDerecha() {
        Alien a = new Alien(100, 50);
        a.act(-20); // direction < 0 → derecha
        assertEquals(120, a.getX()); // x = 100 - (-20)
    }

    //método initAlien

    @Test
    void testXMenorQueCero() {
        Alien a = new Alien(-1, 100);
        assertEquals(0, a.getX());
        assertEquals(100, a.getY());
    }

    @Test
    void testXEnLimiteInferior() {
        Alien a = new Alien(0, 50);
        assertEquals(0, a.getX());
        assertEquals(50, a.getY());
    }

    @Test
    void testXDentroDeRango() {
        Alien a = new Alien(357, 120);
        assertEquals(357, a.getX());
        assertEquals(120, a.getY());
    }

    @Test
    void testXEnLimiteSuperior() {
        Alien a = new Alien(Commons.BOARD_WIDTH, 10);
        assertEquals(Commons.BOARD_WIDTH, a.getX());
        assertEquals(10, a.getY());
    }

    @Test
    void testXMayorQueLimite() {
        Alien a = new Alien(400, 80);
        assertEquals(Commons.BOARD_WIDTH, a.getX());
        assertEquals(80, a.getY());
    }

    @Test
    void testYMenorQueCero() {
        Alien a = new Alien(150, -5);
        assertEquals(150, a.getX());
        assertEquals(0, a.getY());
    }

    @Test
    void testYEnLimiteSuperior() {
        Alien a = new Alien(120, Commons.BOARD_HEIGHT);
        assertEquals(120, a.getX());
        assertEquals(Commons.BOARD_HEIGHT, a.getY());
    }

    @Test
    void testYMayorQueLimite() {
        Alien a = new Alien(200, 400);
        assertEquals(200, a.getX());
        assertEquals(Commons.BOARD_HEIGHT, a.getY());
    }

    //bomba
    @Test
    void testXNegativo() {
        Alien alien = new Alien(0,0);
        Alien.Bomb bomb = alien.new Bomb(-5, 100);
        assertEquals(0, bomb.getX());
        assertEquals(100, bomb.getY());
    }

    @Test
    void testValoresValidos() {
        Alien alien = new Alien(0,0);
        Alien.Bomb bomb = alien.new Bomb(100, 200);
        assertEquals(100, bomb.getX());
        assertEquals(200, bomb.getY());
    }

    @Test
    void testXMayorQueLimiteBomba() {
        Alien alien = new Alien(0,0);
        Alien.Bomb bomb = alien.new Bomb(400, 80);
        assertEquals(Commons.BOARD_WIDTH, bomb.getX());
        assertEquals(80, bomb.getY());
    }

    @Test
    void testYMenorQueCeroBomba() {
        Alien alien = new Alien(0,0);
        Alien.Bomb bomb = alien.new Bomb(120, -10);
        assertEquals(120, bomb.getX());
        assertEquals(0, bomb.getY());
    }

    @Test
    void testAmbosFueraDeLimite() {
        Alien alien = new Alien(0,0);
        Alien.Bomb bomb = alien.new Bomb(500, 500);
        assertEquals(Commons.BOARD_WIDTH, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT, bomb.getY());
    }

    /*
    PRUEBAS PARA CAJA BLANCA
     */

    //init alien
    @Test
    void camino1_todoFalse() {
        Alien a = new Alien(100, 100);
        assertEquals(100, a.getX());
        assertEquals(100, a.getY());
    }

    @Test
    void camino2_xMayorQueMax() {
        Alien a = new Alien(400, 100);
        assertEquals(Commons.BOARD_WIDTH, a.getX());
        assertEquals(100, a.getY());
    }

    @Test
    void camino3_xMenorQueCero() {
        Alien a = new Alien(-10, 100);
        assertEquals(0, a.getX());
        assertEquals(100, a.getY());
    }

    @Test
    void camino4_yMayorQueMax() {
        Alien a = new Alien(100, 500);
        assertEquals(100, a.getX());
        assertEquals(Commons.BOARD_HEIGHT, a.getY());
    }

    @Test
    void camino5_yMenorQueCero() {
        Alien a = new Alien(100, -20);
        assertEquals(100, a.getX());
        assertEquals(0, a.getY());
    }

    //act
    @Test
    void caminoUnico_act() {
        Alien a = new Alien(100, 50);
        a.act(10);
        assertEquals(90, a.getX());
    }

    //bomba
    @Test
    void camino1_trueBranch() {
        Alien.Bomb b = new Alien(0,0).new Bomb(10, 20);
        assertEquals(20, b.getX());
        assertEquals(40, b.getY());
        assertTrue(b.isDestroyed());
    }

    @Test
    void camino2_falseBranch() {
        Alien.Bomb b = new Alien(0,0).new Bomb(500, 600);
        assertEquals(Commons.BOARD_WIDTH, b.getX());
        assertEquals(Commons.BOARD_HEIGHT, b.getY());
    }

}