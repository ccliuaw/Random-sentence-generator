package assignment4;

import java.util.Objects;
import java.util.Random;

/**
 * Randomizer generates a random number, with a fix seed to give a steady output
 */
public class Randomizer implements RandomizerInterface {

  private static int SEED = 42;
  private Random random;

  /**
   * Construct a Randomizer
   */
  public Randomizer() {
    random = new Random();
  }

  private Randomizer(int SEED) {
    random = new Random(SEED);
  }

  /**
   * Construct a Randomizer with Seed
   *
   * @return the randomizer
   */
  public static Randomizer createSeededRandomizer() {
    return new Randomizer(SEED);
  }

  /**
   * generate a random integer within 0 to size
   *
   * @param size upper bound
   * @return a random integer
   */
  @Override
  public int getRandomNumber(int size) {
    return random.nextInt(size);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Randomizer that = (Randomizer) o;
    return Objects.equals(random, that.random);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(random);
  }

  @Override
  public String toString() {
    return "Randomizer{" +
        "random=" + random +
        '}';
  }
}
