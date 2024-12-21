package assignment4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static assignment4.GrammarContext.START_KEY;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GrammarContextTest {

  private GrammarContext grammarContext;
  private Map<String, List<String>> grammarRules;
  private List<String> starts = Arrays.asList("The <noun> <verb>", "A <adjective> <noun>");
  private List<String> nouns = Arrays.asList("cat", "dog", "bird");
  private List<String> verbs = Arrays.asList("runs", "jumps", "flies");
  private List<String> adjectives = Arrays.asList("happy", "sad", "angry");
  @BeforeEach
  void setUp() {
    grammarRules = new HashMap<>();
    grammarRules.put("start",starts );
    grammarRules.put("noun", nouns);
    grammarRules.put("verb", verbs);
    grammarRules.put("adjective", adjectives);
    RandomizerInterface seededRandomizer = Randomizer.createSeededRandomizer();
    grammarContext = new GrammarContext(grammarRules, "Test Grammar", "A test grammar", seededRandomizer);
  }

  @Test
  void getRandomProduction_ReturnsExpectedSequence() {
    RandomizerInterface newSeededRandomizer = Randomizer.createSeededRandomizer();
    assertEquals(nouns.get(newSeededRandomizer.getRandomNumber(3)), grammarContext.getRandomProduction("noun"));
    assertEquals(nouns.get(newSeededRandomizer.getRandomNumber(3)), grammarContext.getRandomProduction("noun"));
    assertEquals(nouns.get(newSeededRandomizer.getRandomNumber(3)), grammarContext.getRandomProduction("noun"));
    assertEquals(nouns.get(newSeededRandomizer.getRandomNumber(3)), grammarContext.getRandomProduction("noun"));
    assertEquals(nouns.get(newSeededRandomizer.getRandomNumber(3)), grammarContext.getRandomProduction("noun"));
    assertEquals(nouns.get(newSeededRandomizer.getRandomNumber(3)), grammarContext.getRandomProduction("noun"));
  }

  @Test
  void getRandomProduction_ReturnsNullForNonExistentKey() {
    assertEquals(null, grammarContext.getRandomProduction("nonexistent"));
  }

  @Test
  void getGrammarTitle_ReturnsCorrectTitle() {
    assertEquals("Test Grammar", grammarContext.getGrammarTitle());
  }

  @Test
  void constructor_InitializesAllFields() {
    GrammarContext newContext = new GrammarContext(grammarRules, "New Grammar", "New Description", Randomizer.createSeededRandomizer());
    assertEquals("New Grammar", newContext.getGrammarTitle());
    assertEquals("New Description", newContext.getGrammarDescription());
    assertEquals(START_KEY, newContext.getStartKey());
  }

  @Test
  void constructor_WithoutRandomizer() {
    grammarContext = new GrammarContext(grammarRules, "Test Grammar", "A test grammar");
    assertNotNull(grammarContext);
  }

  @Test
  void getRandomProduction_UsesRandomizer() {
    RandomizerInterface newSeededRandomizer = Randomizer.createSeededRandomizer();
    // This test assumes that with seed 42, the first three calls to getRandomNumber(3) return 2, 0, 0, 2, 0, 1
    assertEquals(nouns.get(newSeededRandomizer.getRandomNumber(3)), grammarContext.getRandomProduction("noun"));
    assertEquals(nouns.get(newSeededRandomizer.getRandomNumber(3)), grammarContext.getRandomProduction("noun"));
    assertEquals(nouns.get(newSeededRandomizer.getRandomNumber(3)), grammarContext.getRandomProduction("noun"));
  }

  @Test
  void equals_SameObject_ReturnsTrue() {
    assertTrue(grammarContext.equals(grammarContext));
  }

  @Test
  void equals_NullObject_ReturnsFalse() {
    assertFalse(grammarContext.equals(null));
  }

  @Test
  void equals_DifferentClass_ReturnsFalse() {
    assertFalse(grammarContext.equals("Not a GrammarContext"));
  }

  @Test
  void equals_SameContents_ReturnsTrue() {
    RandomizerInterface randomizer = Randomizer.createSeededRandomizer();
    GrammarContext one = new GrammarContext(grammarRules, "Test Grammar", "A test grammar", randomizer);
    GrammarContext other = new GrammarContext(grammarRules, "Test Grammar", "A test grammar", randomizer);
    assertEquals(one, other);
  }

  @Test
  void equals_DifferentTitle_ReturnsFalse() {
    GrammarContext other = new GrammarContext(grammarRules, "Different Title", "A test grammar", Randomizer.createSeededRandomizer());
    assertFalse(grammarContext.equals(other));
  }

  @Test
  void equals_DifferentDescription_ReturnsFalse() {
    GrammarContext other = new GrammarContext(grammarRules, "Test Grammar", "Different description", Randomizer.createSeededRandomizer());
    assertFalse(grammarContext.equals(other));
  }

  @Test
  void equals_DifferentRules_ReturnsFalse() {
    Map<String, List<String>> differentRules = new HashMap<>(grammarRules);
    differentRules.put("newRule", Arrays.asList("new", "rule"));
    GrammarContext other = new GrammarContext(differentRules, "Test Grammar", "A test grammar", Randomizer.createSeededRandomizer());
    assertFalse(grammarContext.equals(other));
  }

  @Test
  void hashCode_ConsistentWithEquals() {
    RandomizerInterface newRandomizer = Randomizer.createSeededRandomizer();
    GrammarContext other1 = new GrammarContext(grammarRules, "Test Grammar", "A test grammar", newRandomizer);
    GrammarContext other2 = new GrammarContext(grammarRules, "Test Grammar", "A test grammar", newRandomizer);

    // If two objects are equal, their hash codes should be equal
    assertEquals(other1,other2);
    assertEquals(other1.hashCode(), other2.hashCode());
    assertEquals(other1.hashCode(), other2.hashCode());
  }

  @Test
  void hashCode_ConsistencyAcrossInvocations() {
    int initialHashCode = grammarContext.hashCode();
    assertEquals(initialHashCode, grammarContext.hashCode());
    assertEquals(initialHashCode, grammarContext.hashCode());
  }
}