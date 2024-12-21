package assignment4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the context of a grammar, including its rules, title, description, and a randomizer.
 * This class provides methods to interact with the grammar rules and generate random productions.
 */
public class GrammarContext {

  /**
   * The key used to retrieve the start productions from the grammar rules.
   */
  public static final String START_KEY = "start";

  /**
   * The default value returned when a production is not found.
   */
  public static final String EMPTY_PRODUCTION = "";

  private final Map<String, List<String>> grammarRules;
  private final String grammarTitle;
  private final String grammarDesc;
  private final RandomizerInterface randomizer;
  private final String startKey;

  /**
   * Constructs a GrammarContext with the specified rules, title, and description. Uses a default
   * Randomizer.
   *
   * @param grammarRules The rules of the grammar.
   * @param grammarTitle The title of the grammar.
   * @param grammarDesc  The description of the grammar.
   */
  public GrammarContext(Map<String, List<String>> grammarRules, String grammarTitle,
      String grammarDesc) {
    this(grammarRules, grammarTitle, grammarDesc, new Randomizer());
  }

  /**
   * Constructs a GrammarContext with the specified rules, title, description, and randomizer.
   *
   * @param grammarRules The rules of the grammar.
   * @param grammarTitle The title of the grammar.
   * @param grammarDesc  The description of the grammar.
   * @param randomizer   The randomizer to use for selecting productions.
   */
  public GrammarContext(Map<String, List<String>> grammarRules, String grammarTitle,
      String grammarDesc, RandomizerInterface randomizer) {
    this.grammarRules = new HashMap<>(grammarRules);
    this.grammarTitle = grammarTitle;
    this.grammarDesc = grammarDesc;
    this.randomizer = randomizer;
    this.startKey = START_KEY;
  }

  /**
   * Gets a random production for the given non-terminal expression.
   *
   * @param nonTerminalExpressionValue The non-terminal expression to get a production for.
   * @return A randomly selected production, or an empty string if no productions exist.
   */
  public String getRandomProduction(String nonTerminalExpressionValue) {
    List<String> productions = grammarRules.get(nonTerminalExpressionValue);
    if (productions == null) {
      return null;
    }

    if (productions.isEmpty()) {
      return EMPTY_PRODUCTION;
    }

    return productions.get(randomizer.getRandomNumber(productions.size()));
  }

  /**
   * Gets the start key.
   *
   * @return the start key
   */
  public String getStartKey() {
    return startKey;
  }

  /**
   * Gets the title of this grammar.
   *
   * @return The grammar title.
   */
  public String getGrammarTitle() {
    return grammarTitle;
  }

  /**
   * Gets the description of this grammar.
   *
   * @return The grammar description.
   */
  public String getGrammarDescription() {
    return grammarDesc;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GrammarContext that = (GrammarContext) o;
    return Objects.equals(grammarRules, that.grammarRules) &&
        Objects.equals(grammarTitle, that.grammarTitle) &&
        Objects.equals(grammarDesc, that.grammarDesc) &&
        Objects.equals(startKey, that.startKey) &&
        Objects.equals(randomizer, that.randomizer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(grammarRules, grammarTitle, grammarDesc, startKey, randomizer);
  }

  @Override
  public String toString() {
    return "GrammarContext{" +
        "grammarRules=" + grammarRules +
        ", grammarTitle='" + grammarTitle + '\'' +
        ", grammarDesc='" + grammarDesc + '\'' +
        ", startKey='" + startKey + '\'' +
        ", randomizer=" + randomizer +
        '}';
  }
}