package assignment4;

/**
 * An interface for generating random numbers.
 * Implementations of this interface should provide a method to generate
 * random integers within a specified range.
 */
public interface RandomizerInterface {

  /**
   * Generates a random integer within the range [MIN_VALUE, range).
   *
   * @param range The upper bound (exclusive) of the random number range.
   * @return A random integer between 0 (inclusive) and range (exclusive).
   * @throws IllegalArgumentException if range is less than or equal to 0.
   */
  int getRandomNumber(int range);
}