package assignment4;

import java.util.Objects;

/**
 * Generates sentences based on a given grammar context.
 */
public class SentenceGenerator implements GeneratorInterface {

  private GrammarContext context;

  /**
   * Constructs a SentenceGenerator instance.
   *
   * @param context the context of a grammar file
   */
  public SentenceGenerator(GrammarContext context) {
    this.context = context;
  }

  /**
   * Generates a sentence by randomly selecting a "start" sentence from the grammar file.
   *
   * @return a generated sentence
   */
  @Override
  public String generate() throws UndefinedProductionException {
    ExpressionInterpreterInterface expressionInterpreter = new RandomExpressionInterpreter(
        context.getStartKey());
    return expressionInterpreter.interpret(this.context);
  }

  @Override
  public String toString() {
    return "Context of sentence generator is: " + this.context.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SentenceGenerator that = (SentenceGenerator) o;
    return Objects.equals(context, that.context);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(context);
  }
}
