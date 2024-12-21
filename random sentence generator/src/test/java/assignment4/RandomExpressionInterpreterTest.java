package assignment4;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomExpressionInterpreterTest {

  private RandomExpressionInterpreter randomExpressionInterpreter;
  private GrammarContext grammarContext;
  private Randomizer randomizer;

  @BeforeEach
  void setUp() {
    grammarContext = new GrammarContext(
        Map.of("start", List.of("Hello, <name>!"), "name", List.of("Harris", "Tamir", "John")),
        "Greetings", "", Randomizer.createSeededRandomizer());
    randomExpressionInterpreter = new RandomExpressionInterpreter("start");
  }

  @Test
  void interpret() throws UndefinedProductionException {
    String s = randomExpressionInterpreter.interpret(grammarContext);
    System.out.println(s);
  }

  @Test
  void toStringTest() {
    assertEquals(randomExpressionInterpreter.toString(), "start");
  }

  @Test
  void equalsTest() {
    assertEquals(randomExpressionInterpreter, randomExpressionInterpreter);
    assertEquals(randomExpressionInterpreter, new RandomExpressionInterpreter("start"));
    assertNotEquals(randomExpressionInterpreter, new RandomExpressionInterpreter("adj"));
    assertNotEquals(randomExpressionInterpreter, null);
  }

  @Test
  void hashCodeTest() {
    assertEquals(randomExpressionInterpreter.hashCode(),
        new RandomExpressionInterpreter("start").hashCode());
    assertNotEquals(randomExpressionInterpreter.hashCode(),
        new RandomExpressionInterpreter("adj").hashCode());
  }
}