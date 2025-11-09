package space_invaders.sprites;

import main.Board;
import main.Commons;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @org.junit.jupiter.api.Nested
    class GameInitTests {

        @BeforeEach
        public void setUp() {
        }

        @Test
        void testGameInit_CreatesCorrectNumberOfAliens() {

            // Verificar que aliens.size() = ALIEN_ROWS * ALIEN_COLUMNS
            int numAliens = Commons.ALIEN_ROWS * Commons.ALIEN_COLUMNS;
            Assertions.assertEquals(numAliens, board.getAliens().size());

        }

        @Test
        void testGameInit_PlayerInitializedCorrectly() {
            // Verificar que player != null
            assertNotEquals(null, board.getPlayer());
        }

    }

    @org.junit.jupiter.api.Nested
    class UpdateTests {

        @Test
        void testUpdate_GameWon_WhenAllAliensDestroyed() throws Exception {
            // Configurar deaths = NUMBER_OF_ALIENS_TO_DESTROY
            // Verificar que inGame = false y message = "Game won!"
            TestReflectionHelper.setPrivateField(board, "deaths", Commons.NUMBER_OF_ALIENS_TO_DESTROY);
            TestReflectionHelper.callPrivateMethod(board, "update");

            assertFalse(board.isInGame());
        }

        @Test
        void testUpdate_GameContinues_WhenAliensRemain() throws Exception {
            // Configurar deaths < NUMBER_OF_ALIENS_TO_DESTROY, usando Commons.ALIEN_ROWS se asegura que siempre sea menos
            // Verificar que inGame = true

            TestReflectionHelper.setPrivateField(board, "deaths", Commons.ALIEN_ROWS);
            TestReflectionHelper.callPrivateMethod(board, "update");

            assertTrue(board.isInGame());

        }

    }

    @org.junit.jupiter.api.Nested
    class UpdateShotsTests {

        @Test
        void testUpdateShots_AlienHit_WhenShotCollides() throws Exception {
            // Posicionar shot y alien para colisión
            // Verificar que alien.isDying() = true y deaths aumenta
            board.setShot(new Shot(Commons.ALIEN_ROWS - 1, Commons.ALIEN_COLUMNS - 1));
            TestReflectionHelper.callPrivateMethod(board, "update_shots");
            assertEquals(Commons.NUMBER_OF_ALIENS_TO_DESTROY - 1, board.getAliens().size());

        }

        @Test
        void testUpdateShots_ShotDisappears_WhenOffScreen() throws Exception {
            // Posicionar shot en Y < 0
            // Verificar que !shot.isVisible()

            board.setShot(new Shot(Commons.BOARD_WIDTH + 1, Commons.BOARD_HEIGHT + 1));
            TestReflectionHelper.callPrivateMethod(board, "update_shots");
            assertFalse(board.getShot().isVisible());

        }

        @Test
        void testUpdateShots_NoCollision_WhenPositionsDontMatch() throws Exception {
            // Posicionar shot y alien sin superposición
            // Verificar que numero de aliens es igual (no ha matado ninguno)

            board.setShot(new Shot(Commons.ALIEN_ROWS, Commons.ALIEN_COLUMNS + 1));
            int numAliens = Commons.ALIEN_ROWS * Commons.ALIEN_COLUMNS;
            TestReflectionHelper.callPrivateMethod(board, "update_shots");
            assertEquals(numAliens, board.getAliens().size());

        }

    }

    @org.junit.jupiter.api.Nested
    class UpdateAliensTests {

        @Test
        void testUpdateAliens_ChangeDirection_WhenHitRightBorder() throws Exception {
            // Posicionar alien en BORDER_RIGHT
            // Verificar que direction cambia

            Alien edgeAlien = new Alien(
                    Commons.BOARD_WIDTH - Commons.BORDER_RIGHT,  // X en borde derecho
                    Commons.ALIEN_INIT_Y
            );
            board.getAliens().add(edgeAlien);
            board.setDirection(-1);
            int initialY = edgeAlien.getY();
            TestReflectionHelper.callPrivateMethod(board, "update_aliens");
            assertEquals(1, board.getDirection());
            assertEquals(initialY + Commons.GO_DOWN, edgeAlien.getY());

        }

        @Test
        void testUpdateAliens_ChangeDirection_WhenHitLeftBorder() throws Exception {
            // Posicionar alien en BORDER_LEFT
            // Verificar que direction cambia

            Alien leftEdgeAlien = new Alien(
                    Commons.BORDER_LEFT,  // X en borde izquierdo
                    Commons.ALIEN_INIT_Y
            );
            board.getAliens().add(leftEdgeAlien);
            board.setDirection(1);
            int initialY = leftEdgeAlien.getY();
            TestReflectionHelper.callPrivateMethod(board, "update_aliens");
            assertEquals(-1, board.getDirection());
            assertEquals(initialY + Commons.GO_DOWN, leftEdgeAlien.getY());

        }

        @Test
        void testUpdateAliens_GameOver_WhenReachGround() throws Exception {
            // Posicionar alien Y > GROUND + ALIEN_HEIGHT
            // Verificar que inGame = false y message = "Invasion!"

            Alien groundAlien = new Alien(
                    Commons.ALIEN_INIT_X,
                    Commons.GROUND + Commons.ALIEN_HEIGHT + 1  // Por debajo del límite
            );
            board.getAliens().add(groundAlien);
            board.setInGame(true);
            board.setMessage("");
            TestReflectionHelper.callPrivateMethod(board, "update_aliens");
            assertFalse(board.isInGame());

        }

    }

    @org.junit.jupiter.api.Nested
    class UpdateBombTests {

        @Test
        void testUpdateBomb_PlayerHit_WhenBombCollides() throws Exception {
            // Configurar bomba y jugador para colisión
            // Verificar que player.isDying() = true

            Alien alien = new Alien(Commons.ALIEN_INIT_X, Commons.ALIEN_INIT_Y);
            board.getAliens().add(alien);
            Alien.Bomb bomb = alien.getBomb();
            bomb.setDestroyed(false);
            bomb.setX(board.getPlayer().getX());
            bomb.setY(board.getPlayer().getY());
            TestReflectionHelper.callPrivateMethod(board, "update_bomb");
            assertTrue(board.getPlayer().isDying());

        }

        @Test
        void testUpdateBomb_BombDestroyed_WhenReachesGround() throws Exception {
            // Posicionar bomba en Y >= GROUND - BOMB_HEIGHT
            // Verificar que bomb.isDestroyed() = true

            Alien alien = new Alien(Commons.ALIEN_INIT_X, Commons.GROUND - Commons.BOMB_HEIGHT);
            board.getAliens().add(alien);
            Alien.Bomb bomb = alien.getBomb();
            bomb.setDestroyed(false);
            bomb.setY(Commons.GROUND - Commons.BOMB_HEIGHT);
            TestReflectionHelper.callPrivateMethod(board, "update_bomb");
            assertTrue(bomb.isDestroyed());

        }

    }

    //Necesario porque varios metodos a probar/usar son privados
    public static class TestReflectionHelper {

        public static void callPrivateMethod(Object obj, String methodName) throws Exception {
            Method method = obj.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(obj);
        }

        public static void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        }

    }
}