package assignment4;

/**
 * An interface for interpreting expressions within a grammar context. Implementations of this
 * interface should define how to interpret and transform expressions based on the provided grammar
 * rules.
 */
public interface ExpressionInterpreterInterface {

  /**
   * Interprets the expression using the provided grammar context.
   *
   * @param grammarContext The context containing grammar rules and other necessary information for
   *                       interpreting the expression.
   * @return A string representing the result of the interpretation.
   * @throws UndefinedProductionException the undefined production exception
   */
  String interpret(GrammarContext grammarContext) throws UndefinedProductionException;
}
