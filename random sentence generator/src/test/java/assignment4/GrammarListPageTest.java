package assignment4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GrammarListPageTest {

  private GrammarListPage grammarListPage;
  private List<GrammarContext> grammarContexts;

  @BeforeEach
  void setUp() {
    Map<String, List<String>> rules = new HashMap<>();
    rules.put("start", Arrays.asList("The <noun> <verb>"));
    rules.put("noun", Arrays.asList("cat", "dog"));
    rules.put("verb", Arrays.asList("runs", "jumps"));

    GrammarContext context1 = new GrammarContext(rules, "Grammar1", "Description1", new Randomizer());
    GrammarContext context2 = new GrammarContext(rules, "Grammar2", "Description2", new Randomizer());

    grammarContexts = Arrays.asList(context1, context2);
    grammarListPage = new GrammarListPage(grammarContexts, 5);
  }

  @Test
  void constructor_InitializesFieldsCorrectly() {
    assertEquals(grammarContexts, grammarListPage.getGrammarContexts());
    assertEquals(5, grammarListPage.getRemainingFiles());
  }

  @Test
  void getGrammarContexts_ReturnsUnmodifiableList() {
    List<GrammarContext> returnedList = grammarListPage.getGrammarContexts();
    assertThrows(UnsupportedOperationException.class, () -> returnedList.add(null));
  }

  @Test
  void hasNext_ReturnsTrueWhenRemainingFilesPositive() {
    assertTrue(grammarListPage.hasNext());
  }

  @Test
  void hasNext_ReturnsNullWhenRemainingFilesPositive() {
    assertTrue(grammarListPage.hasNext());
  }

  @Test
  void hasNext_ReturnsFalseWhenRemainingFilesZero() {
    GrammarListPage noRemainingFiles = new GrammarListPage(grammarContexts, 0);
    assertFalse(noRemainingFiles.hasNext());
  }

  @Test
  void getRemainingFiles_ReturnsCorrectNumber() {
    assertEquals(5, grammarListPage.getRemainingFiles());
  }

  @Test
  void equals_SameObject_ReturnsTrue() {
    assertTrue(grammarListPage.equals(grammarListPage));
  }

  @Test
  void equals_NullObject_ReturnsFalse() {
    assertFalse(grammarListPage.equals(null));
  }

  @Test
  void equals_DifferentClass_ReturnsFalse() {
    assertFalse(grammarListPage.equals("Not a GrammarListPage"));
  }

  @Test
  void equals_SameContents_ReturnsTrue() {
    GrammarListPage other = new GrammarListPage(grammarContexts, 5);
    assertTrue(grammarListPage.equals(other));
  }

  @Test
  void equals_DifferentRemainingFiles_ReturnsFalse() {
    GrammarListPage other = new GrammarListPage(grammarContexts, 4);
    assertFalse(grammarListPage.equals(other));
  }

  @Test
  void equals_DifferentContexts_ReturnsFalse() {
    GrammarListPage other = new GrammarListPage(new ArrayList<>(), 5);
    assertFalse(grammarListPage.equals(other));
  }

  @Test
  void hashCode_ConsistentWithEquals() {
    GrammarListPage other = new GrammarListPage(grammarContexts, 5);
    assertEquals(grammarListPage.hashCode(), other.hashCode());
  }

  @Test
  void hashCode_DifferentForDifferentObjects() {
    GrammarListPage other = new GrammarListPage(grammarContexts, 4);
    assertNotEquals(grammarListPage.hashCode(), other.hashCode());
  }

  @Test
  void toString_ContainsAllRelevantInformation() {
    String result = grammarListPage.toString();
    assertTrue(result.contains("remainingFiles=5"));
    assertTrue(result.contains("pageGrammarContexts="));
    assertTrue(result.contains("Grammar1"));
    assertTrue(result.contains("Grammar2"));
  }
}