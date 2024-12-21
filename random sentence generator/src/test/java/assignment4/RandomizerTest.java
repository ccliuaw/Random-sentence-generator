package assignment4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomizerTest {
  private Randomizer randomizer;
  private Randomizer seededRandomizer;
  private Randomizer seededRandomizer2;

  @BeforeEach
  void setUp() {
    randomizer = new Randomizer();
    seededRandomizer = Randomizer.createSeededRandomizer();
    seededRandomizer2 = Randomizer.createSeededRandomizer();
  }

  @Test
  void getRandomNumber() {
    try {
      Integer.valueOf(randomizer.getRandomNumber(2));
    } catch (NumberFormatException e) {
      fail("Doesn't generate a valid random integer");
    }
    assertEquals(seededRandomizer.getRandomNumber(100), seededRandomizer2.getRandomNumber(100));
  }

  @Test
  public void testToStringFormat() {
    Randomizer randomizer = new Randomizer();
    String result = randomizer.toString();
    assertTrue(result.matches("Randomizer\\{random=java\\.util\\.Random@[a-f0-9]+\\}"));
  }

  @Test
  public void testToStringConsistency() {
    Randomizer randomizer = new Randomizer();
    String result1 = randomizer.toString();
    String result2 = randomizer.toString();
    assertEquals(result1, result2);
  }

  @Test
  void equals(){
    assertEquals(randomizer, randomizer);
    assertNotEquals(randomizer, seededRandomizer);
    assertNotEquals(randomizer, null);
    assertNotEquals(randomizer, 1);
  }

  @Test
  void hashCodeTest(){
    assertEquals(randomizer.hashCode(), randomizer.hashCode());
  }
}