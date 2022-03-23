import org.junit.Test;

import model.Posn;

import static org.junit.Assert.assertEquals;

/**
 * Class representing the test class for our Posn class.
 */
public class PosnTest {
  Posn zeroOne = new Posn(0, 1);
  Posn zerOne1 = new Posn(0, 1);

  @Test
  public void testGetX() {
    assertEquals(zeroOne.getX(), 0);
  }

  @Test
  public void testGetY() {
    assertEquals(zeroOne.getY(), 1);
  }

  @Test
  public void testMove() {
    zeroOne.move(3, 1);
    assertEquals(3, zeroOne.getX());
    assertEquals(2, zeroOne.getY());
  }

  @Test
  public void testMoveNegative() {
    zerOne1.move(-2, -10);
    assertEquals(-2, zerOne1.getX());
    assertEquals(-9, zerOne1.getY());
  }

  @Test
  public void testEquals() {
    assertEquals(zeroOne, zerOne1);
  }

}
