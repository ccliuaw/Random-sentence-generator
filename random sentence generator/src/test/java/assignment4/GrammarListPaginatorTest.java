package assignment4;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static assignment4.GrammarListPaginator.DEFAULT_GRAMMAR_PER_PAGE;
import static org.junit.jupiter.api.Assertions.*;

class GrammarListPaginatorTest {

  @TempDir
  Path tempDir;

  private GrammarListPaginator paginator;

  @BeforeEach
  void setUp() throws IOException {
    createTestGrammarFiles();
    GrammarLoader loader = new GrammarLoader(new FileFolderReader(), tempDir.toString());
    paginator = new GrammarListPaginator(loader, 1); // 1 grammar per page for easier testing
  }

  private void createTestGrammarFiles() throws IOException {
    String grammar1 = "{\n  \"grammarTitle\": \"Test Grammar 1\",\n  \"grammarDesc\": \"Description 1\",\n  \"noun\": [\n    \"cat\",\n    \"dog\"\n  ]\n}";
    String grammar2 = "{\n  \"grammarTitle\": \"Test Grammar 2\",\n  \"grammarDesc\": \"Description 2\",\n  \"verb\": [\n    \"runs\",\n    \"jumps\"\n  ]\n}";
    String grammar3 = "{\n  \"grammarTitle\": \"Test Grammar 3\",\n  \"grammarDesc\": \"Description 3\",\n  \"adjective\": [\n    \"happy\",\n    \"sad\"\n  ]\n}";
    String grammar4 = "{\n  \"grammarTitle\": \"Test Grammar 4\",\n  \"grammarDesc\": \"Description 4\",\n  \"adjective\": [\n    \"happy\",\n    \"sad\"\n  ]\n}";
    String grammar5 = "{\n  \"grammarTitle\": \"Test Grammar 5\",\n  \"grammarDesc\": \"Description 5\",\n  \"adjective\": [\n    \"happy\",\n    \"sad\"\n  ]\n}";
    String grammar6 = "{\n  \"grammarTitle\": \"Test Grammar 6\",\n  \"grammarDesc\": \"Description 6 d\",\n  \"adjective\": [\n    \"happy\",\n    \"sad\"\n  ]\n}";

    Files.writeString(tempDir.resolve("grammar1.json"), grammar1);
    Files.writeString(tempDir.resolve("grammar2.json"), grammar2);
    Files.writeString(tempDir.resolve("grammar3.json"), grammar3);
    Files.writeString(tempDir.resolve("grammar4.json"), grammar4);
    Files.writeString(tempDir.resolve("grammar5.json"), grammar5);
    Files.writeString(tempDir.resolve("grammar6.json"), grammar6);
  }

  @Test
  void constructor_withOnlyLoader() throws IOException {
    GrammarLoader loader = new GrammarLoader(new FileFolderReader(), tempDir.toString());
    GrammarListPaginator anotherPaginator = new GrammarListPaginator(loader);
    assertEquals(0, anotherPaginator.getCurrentPageIndex());
    assertEquals(DEFAULT_GRAMMAR_PER_PAGE, anotherPaginator.getCurrentPage().getGrammarContexts().size());
  }
  @Test
  void getCurrentPage_ReturnsCorrectPage() throws IOException {
    GrammarListPage page = paginator.getCurrentPage();
    assertEquals(1, page.getGrammarContexts().size());
    assertEquals(5, page.getRemainingFiles());
    assertTrue(page.getGrammarContexts().get(0).getGrammarTitle().contains("Test Grammar "));
  }

  @Test
  void nextPage_MovesToNextPage() throws IOException {
    GrammarListPage currentPage = paginator.getCurrentPage();
    paginator.nextPage();
    GrammarListPage nextPage = paginator.getCurrentPage();
    assertNotEquals(nextPage.getGrammarContexts().get(0).getGrammarTitle(), currentPage.getGrammarContexts().get(0).getGrammarTitle());
    assertEquals(1, paginator.getCurrentPageIndex());
  }

  @Test
  void previousPage_MovesToPreviousPage() throws IOException {
    GrammarListPage currentPage = paginator.getCurrentPage();
    paginator.nextPage();
    paginator.previousPage();
    GrammarListPage samePage = paginator.getCurrentPage();
    assertEquals(samePage.getGrammarContexts().get(0).getGrammarTitle(), currentPage.getGrammarContexts().get(0).getGrammarTitle());
    assertEquals(0, paginator.getCurrentPageIndex());
  }

  @Test
  void hasNextPage_ReturnsCorrectValue() throws IOException {
    assertTrue(paginator.hasNextPage());
    paginator.nextPage();
    paginator.nextPage();
    paginator.nextPage();
    paginator.nextPage();
    paginator.nextPage();
    assertFalse(paginator.hasNextPage());
  }

  @Test
  void nextPage_ReturnsNullWhenNoMorePages() throws IOException {
    assertTrue(paginator.hasNextPage());
    paginator.nextPage();
    paginator.nextPage();
    paginator.nextPage();
    paginator.nextPage();
    paginator.nextPage();
    paginator.nextPage();
    assertFalse(paginator.hasNextPage());
    assertNull(paginator.nextPage());
  }

  @Test
  void previousPage_ReturnsNullWhenNoMorePages() throws IOException {
    assertFalse(paginator.hasPreviousPage());
    assertNull(paginator.previousPage());
  }

  @Test
  void hasPreviousPage_ReturnsCorrectValue() throws IOException {
    assertFalse(paginator.hasPreviousPage());
    paginator.nextPage();
    assertTrue(paginator.hasPreviousPage());
  }

  @Test
  void loadGrammarContexts_LoadsCorrectContexts() throws IOException {
    List<GrammarContext> contexts = paginator.loadGrammarContexts(0, 2);
    assertEquals(2, contexts.size());
    assertTrue(contexts.get(0).getGrammarTitle().contains("Test Grammar "));
    assertTrue(contexts.get(1).getGrammarTitle().contains("Test Grammar "));
  }

  @Test
  void equals_SameObject_ReturnsTrue() throws IOException {
    assertTrue(paginator.equals(paginator));
  }

  @Test
  void equals_DifferentObject_ReturnsFalse() throws IOException {
    GrammarLoader loader = new GrammarLoader(new FileFolderReader(), tempDir.toString());
    GrammarListPaginator anotherPaginator = new GrammarListPaginator(loader, 2);
    assertFalse(paginator.equals(anotherPaginator));
    assertFalse(paginator.equals(null));
    assertFalse(paginator.equals("Test"));
  }

  @Test
  void equals_DifferentFields_ReturnsFalse() throws IOException {
    RandomizerInterface randomizer = Randomizer.createSeededRandomizer();
    GrammarLoader loader1 = new GrammarLoader(new FileFolderReader(), tempDir.toString(), randomizer);
    GrammarLoader loader2 = new GrammarLoader(new FileFolderReader(), tempDir.toString(), randomizer);
    GrammarLoader loader3 = new GrammarLoader(new FileFolderReader(), tempDir.getParent().toString(), randomizer);
    GrammarListPaginator paginator1 = new GrammarListPaginator(loader1, 2);
    GrammarListPaginator paginator2 = new GrammarListPaginator(loader2, 2);
    GrammarListPaginator paginator3 = new GrammarListPaginator(loader1, 3);
    GrammarListPaginator paginator4 = new GrammarListPaginator(loader3, 2);
    assertNotEquals(paginator1, paginator2);
    assertNotEquals(paginator1, paginator3);
    assertNotEquals(paginator1, paginator4);
    assertNotEquals(paginator2, paginator3);
    assertNotEquals(paginator2, paginator4);
    assertNotEquals(paginator3, paginator4);
  }

  @Test
  void hashCode_ConsistentWithEquals() throws IOException {
    GrammarLoader loader = new GrammarLoader(new FileFolderReader(), tempDir.toString());
    GrammarListPaginator paginator1 = new GrammarListPaginator(loader, 1);
    GrammarListPaginator paginator2 = new GrammarListPaginator(loader, 1);
    assertEquals(paginator1.hashCode(), paginator2.hashCode());
  }

  @Test
  void toString_ContainsRelevantInformation() {
    String toString = paginator.toString();
    assertTrue(toString.contains("GrammarListPaginator"));
    assertTrue(toString.contains("countPerPage=1"));
    assertTrue(toString.contains("currentPageIndex=0"));
  }
}