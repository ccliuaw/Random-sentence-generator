package assignment4;

/**
 * An interface for generating sentences or other text-based content. Implementations of this
 * interface should define a method to generate text according to specific rules or algorithms.
 */
public interface GeneratorInterface {

  /**
   * Generates a sentence or other text-based content.
   *
   * @return A string representing the generated content.
   * @throws UndefinedProductionException the undefined production exception
   */
  String generate() throws UndefinedProductionException;
}