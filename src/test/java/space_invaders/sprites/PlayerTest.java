package space_invaders.sprites;

import main.Commons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void shouldCreatePlayerInCorrectPosition() {
        Player player = new Player();
        assertEquals(Commons.BOARD_WIDTH, player.x);
        assertEquals(Commons.GROUND + 10, player.y);
    }

    @Nested
    class PlayerActTest {
        private Player player;

        @BeforeEach
        void setUp() {
            player = new Player();
            player.x = Commons.BOARD_WIDTH;
            player.y = Commons.GROUND + 10;
        }

        @Test
        void shouldActToRight() {
            var x = player.getX();
            var y = player.getY();
            player.dx = 2;
            player.act();
            assertEquals(x + 2, player.getX());
            assertEquals(y, player.getY());
        }

        @Test
        void shouldActToLeft() {
            var x = player.getX();
            var y = player.getY();
            player.dx = -2;
            player.act();
            assertEquals(x - 2, player.getX());
            assertEquals(y, player.getY());
        }

        @Test
        void shouldBeInTheLimitOfTheBoardToTheRight() {
            player.setX(Commons.BOARD_WIDTH * 2);
            var y = player.getY();
            player.dx = 2;
            player.act();
            assertEquals(Commons.BOARD_WIDTH * 2, player.getX());
            assertEquals(y, player.getY());
        }

        @Test
        void shouldBeInTheLimitOfTheBoardToTheLeft() {
            player.setX(0);
            var y = player.getY();
            player.dx = -2;
            player.act();
            assertEquals(0, player.getX());
            assertEquals(y, player.getY());
        }
    }

    @Nested
    class PlayerKeyPressedTest {

        Player player;

        @BeforeEach
        void setUp() {
            player = new Player();
            player.dx = 0;
        }

        @Test
        void shouldUpdatePositionToRight() {
            KeyEvent rightKeyEvent = getKeyEvent(KeyEvent.VK_RIGHT);
            player.keyPressed(rightKeyEvent);
            assertEquals(2, player.dx);
        }

        @Test
        void shouldUpdatePositionToLeft() {
            KeyEvent leftKeyEvent = getKeyEvent(KeyEvent.VK_LEFT);
            player.keyPressed(leftKeyEvent);
            assertEquals(-2, player.dx);
        }

        @Test
        void shouldKeepLeftPosition() {
            player.dx = -2;
            KeyEvent leftKeyEvent = getKeyEvent(KeyEvent.VK_LEFT);
            player.keyPressed(leftKeyEvent);
            assertEquals(-2, player.dx);
        }

        @Test
        void shouldKeepRightPosition() {
            player.dx = 2;
            KeyEvent rightKeyEvent = getKeyEvent(KeyEvent.VK_RIGHT);
            player.keyPressed(rightKeyEvent);
            assertEquals(2, player.dx);
        }

        @Test
        void shouldNotUpdateIfKeyIsNotRightNorLeft() {
            var previous = player.dx;
            player.keyPressed(getKeyEvent(KeyEvent.VK_S));
            assertEquals(previous, player.dx);
        }

        private static KeyEvent getKeyEvent(int vkRight) {
            return new KeyEvent(
                    new java.awt.Canvas(),
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    0,
                    vkRight,
                    KeyEvent.CHAR_UNDEFINED
            );
        }
    }

    @Nested
    class PlayerKeyReleasedTest{

        Player player;

        @Test
        void shouldUpdatePositionWhenRightIsReleased() {
            player.dx = 2;
            KeyEvent rightKeyEvent = getKeyEvent(KeyEvent.VK_RIGHT);
            player.keyReleased(rightKeyEvent);
            assertEquals(0, player.dx);
        }

        @Test
        void shouldUpdatePositionWhenLeftIsReleased() {
            player.dx = -2;
            KeyEvent rightKeyEvent = getKeyEvent(KeyEvent.VK_LEFT);
            player.keyReleased(rightKeyEvent);
            assertEquals(0, player.dx);
        }

        @Test
        void shouldNotUpdateIfKeyIsNotRightNorLeft() {
            player.dx = 20;
            KeyEvent rightKeyEvent = getKeyEvent(KeyEvent.VK_R);
            player.keyReleased(rightKeyEvent);
            assertEquals(20, player.dx);
        }


        private static KeyEvent getKeyEvent(int vkRight) {
            return new KeyEvent(
                    new java.awt.Canvas(),
                    KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(),
                    0,
                    vkRight,
                    KeyEvent.CHAR_UNDEFINED
            );
        }
    }

}