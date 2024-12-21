package assignment4;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a non-terminal expression interpreter (case-insensitive). This class interprets
 * non-terminal expressions in a grammar context.
 */
public class RandomExpressionInterpreter implements ExpressionInterpreterInterface {


  /**
   * The opening symbol for non-terminal expressions.
   */
  private static final String NON_TERMINAL_OPEN = "<";

  /**
   * The closing symbol for non-terminal expressions.
   */
  private static final String NON_TERMINAL_CLOSE = ">";

  /**
   * Regular expression pattern to find non-terminal expressions.
   */
  private static final String REGEX_TO_FIND_NON_TERMINAL =
      NON_TERMINAL_OPEN + "([^" + NON_TERMINAL_CLOSE + "]+)" + NON_TERMINAL_CLOSE;

  /**
   * Error message for when we have undefined non-terminal in grammar.
   */
  public static final String UNDEFINED_NON_TERMINAL_IN_GRAMMAR_ERROR_MESSAGE_PREFIX = "Undefined non-terminal in grammar: ";

  private final String nonTerminal;

  /**
   * Constructs a RandomExpressionInterpreter instance.
   *
   * @param nonTerminal a non-terminal string
   */
  public RandomExpressionInterpreter(String nonTerminal) {
    this.nonTerminal = nonTerminal.toLowerCase();  // Case-insensitive
  }

  /**
   * Interprets the non-terminal expression in the given grammar context.
   *
   * @param context input grammar context
   * @return a string after interpretation
   * @throws IllegalArgumentException if the non-terminal is not defined in the grammar
   */
  @Override
  public String interpret(GrammarContext context) throws UndefinedProductionException {
    String chosenProduction = context.getRandomProduction(nonTerminal);

    if (chosenProduction == null) {
      throw new UndefinedProductionException(
          UNDEFINED_NON_TERMINAL_IN_GRAMMAR_ERROR_MESSAGE_PREFIX + NON_TERMINAL_OPEN + nonTerminal
              + NON_TERMINAL_CLOSE);
    }

    StringBuilder result = new StringBuilder();
    Pattern pattern = Pattern.compile(REGEX_TO_FIND_NON_TERMINAL);
    Matcher matcher = pattern.matcher(chosenProduction);
    int lastIndex = 0;

    while (matcher.find()) {
      result.append(chosenProduction, lastIndex, matcher.start());

      String innerNonTerminal = matcher.group(1);
      ExpressionInterpreterInterface randomSentenceInterpreter =
          new RandomExpressionInterpreter(innerNonTerminal);
      result.append(randomSentenceInterpreter.interpret(context));

      lastIndex = matcher.end();
    }

    result.append(chosenProduction.substring(lastIndex));

    return result.toString().trim();
  }

  @Override
  public String toString() {
    return nonTerminal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RandomExpressionInterpreter that = (RandomExpressionInterpreter) o;
    return Objects.equals(nonTerminal, that.nonTerminal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nonTerminal);
  }
}