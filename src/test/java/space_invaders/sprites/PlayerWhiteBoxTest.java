package space_invaders.sprites;

import main.Commons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class PlayerWhiteBoxTest {
    private Player player;

    private static final KeyEvent RIGHT_KEY_PRESSED = new KeyEvent(new Component(){}, 0, 0, 0, KeyEvent.VK_RIGHT, ' ');;
    private static final KeyEvent LEFT_KEY_PRESSED = new KeyEvent(new Component(){}, 0, 0, 0, KeyEvent.VK_LEFT, ' ');;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void constructorSetsInitialX() {
        assertEquals(Commons.BOARD_WIDTH / 2, player.x);
    }

    @Test
    void constructorSetsInitialY() {
        assertEquals(Commons.GROUND + 10, player.y);
    }

    @Test
    void constructorSetsImage() {
        assertNotNull(player.getImage());
    }

    @Test
    void actMovesLeftBy2WhenDxIsNegative() {
        player.setX(179);
        player.dx = -2;
        player.act();
        assertEquals(177, player.getX());
    }

    @Test
    void actMovesRightBy2WhenDxIsPositive() {
        player.setX(179);
        player.dx = 2;
        player.act();
        assertEquals(181, player.getX());
    }

    @Test
    void actDoesNotMoveWhenDxIsZero() {
        player.setX(179);
        player.dx = 0;
        player.act();
        assertEquals(179, player.getX());
    }

    @Test
    void actStopsAtLeftLimit() {
        player.setX(0);
        player.dx = -2;
        player.act();
        assertEquals(0, player.getX());
    }

    @Test
    void actInRightLimit() {
        player.setX(Commons.BOARD_WIDTH);
        player.dx = 2;
        player.act();
        assertEquals(
                Commons.BOARD_WIDTH,
                player.getX()
        );
    }

    @Test
    void keyPressedSetsDxToMinus2WhenLeftArrow() {
        player.keyPressed(LEFT_KEY_PRESSED);
        assertEquals(-2, player.dx);
    }

    @Test
    void keyPressedSetsDxToPlus2WhenRightArrow() {
        player.keyPressed(RIGHT_KEY_PRESSED);
        assertEquals(2, player.dx);
    }

    @Test
    void keyPressedIgnoresOtherKeys() {
        player.dx = 99;
        KeyEvent e = new KeyEvent(new Component(){}, 0, 0, 0, KeyEvent.VK_UP, ' ');
        player.keyPressed(e);
        assertEquals(99, player.dx);
    }

    @Test
    void keyReleasedSetsDxZeroWhenLeftArrowReleased() {
        player.dx = -2;
        player.keyReleased(LEFT_KEY_PRESSED);
        assertEquals(0, player.dx);
    }

    @Test
    void keyReleasedSetsDxZeroWhenRightArrowReleased() {
        player.dx = 2;
        player.keyReleased(RIGHT_KEY_PRESSED);
        assertEquals(0, player.dx);
    }

    @Test
    void keyReleasedIgnoresOtherKeys() {
        player.dx = 5;
        KeyEvent e = new KeyEvent(new Component(){}, 0, 0, 0, KeyEvent.VK_SPACE, ' ');
        player.keyReleased(e);
        assertEquals(5, player.dx);
    }
}