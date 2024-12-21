package assignment4;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SentenceGeneratorTest {

  private GrammarContext grammarContext;
  private GrammarContext grammarContext2;
  private SentenceGenerator sentenceGenerator;
  private SentenceGenerator sentenceGenerator2;

  @BeforeEach
  void setUp() {
    grammarContext = new GrammarContext(
        Map.of("start", List.of("Hello, <name>!"), "name", List.of("Harris", "Tamir", "John")),
        "Greetings", "", Randomizer.createSeededRandomizer());
    grammarContext2 = new GrammarContext(
        Map.of("start", List.of("Hello, <name>!"), "name", List.of("Harris", "Tamir", "John")),
        "Greetings", "", Randomizer.createSeededRandomizer());
    sentenceGenerator = new SentenceGenerator(grammarContext);
    sentenceGenerator2 = new SentenceGenerator(grammarContext2);
  }

  @Test
  void generateSentence() throws UndefinedProductionException {
    String sentence = sentenceGenerator.generate();
    String sentence2 = sentenceGenerator2.generate();
    assertEquals(sentence, sentence2);
  }

  @Test
  void testToString() {
    assertEquals(sentenceGenerator.toString(),
        "Context of sentence generator is: " + grammarContext.toString());
  }

  @Test
  void testEquals() {
    assertEquals(sentenceGenerator, sentenceGenerator);
    assertEquals(sentenceGenerator, new SentenceGenerator(grammarContext));
    assertNotEquals(sentenceGenerator, null);
    assertNotEquals(sentenceGenerator, new Object());
  }

  @Test
  void testHashCode() {
    assertEquals(sentenceGenerator.hashCode(), sentenceGenerator.hashCode());
  }
}