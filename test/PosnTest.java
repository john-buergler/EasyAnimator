import org.junit.Test;

import model.Posn;

import static org.junit.Assert.assertEquals;

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
  public void testEquals() {
    assertEquals(zeroOne, zerOne1);
  }

}
