package assignment4;

import static assignment4.UIHelper.PROGRAM_TITLE;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

class UIHelperTest {

  @TempDir
  Path tempDir;

  private UIHelper uiHelper;
  private GrammarListPaginator paginator;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() throws IOException {
    System.setOut(new PrintStream(outContent));  // Capture console output
    createTestGrammarFiles();
    GrammarLoader loader = new GrammarLoader(new FileFolderReader(), tempDir.toString());
    uiHelper = new UIHelper(loader);
  }

  @AfterEach
  void restoreSystemInputOutput() {
    System.setOut(originalOut);  // Restore original System.out
  }

  private void createTestGrammarFiles() throws IOException {
    String grammar1 = "{\n  \"grammarTitle\": \"Test Grammar 1\",\n  \"grammarDesc\": \"Description 1\",\n  \"noun\": [\n    \"cat\",\n    \"dog\"\n  ],\n  \"verb\": [\n    \"runs\",\n    \"jumps\"\n  ]\n}";
    String grammar2 = "{\n  \"grammarTitle\": \"Test Grammar 2\",\n  \"grammarDesc\": \"Description 2\",\n  \"adjective\": [\n    \"happy\",\n    \"sad\"\n  ]\n}";
    String grammar3 = "{\n  \"grammarTitle\": \"Test Grammar 3\",\n  \"grammarDesc\": \"Description 3\",\n  \"interjection\": [\n    \"wow\",\n    \"yay\"\n  ]\n}";

    Files.writeString(tempDir.resolve("a_grammar1.json"), grammar1);
    Files.writeString(tempDir.resolve("b_grammar2.json"), grammar2);
    Files.writeString(tempDir.resolve("c_grammar3.json"), grammar3);
  }

  @Test
  void displayTitle_PrintsCorrectTitle() {
    uiHelper.displayTitle();
    assertEquals(PROGRAM_TITLE.trim(), outContent.toString().trim());
  }

  @Test
  void displayGrammarChoices_PrintsCorrectChoices() throws IOException {
    uiHelper.displayGrammarChoices();
    String output = outContent.toString();

    assertTrue(output.contains("The following grammars are available"));
    assertTrue(output.contains("1. Test Grammar"));
    assertTrue(output.contains("2. Test Grammar"));
  }

//  @Test
//  void askForGrammarChoice_ReturnsCorrectChoice() throws IOException {
//    uiHelper.clear();
//    String input = String.join(System.lineSeparator(), (new String[]{"1", "y", "y", "n", "q"})); // Select first grammar, generate multiple sentences, then quit
//    System.setIn(new ByteArrayInputStream(input.getBytes()));
//    String output = outContent.toString();
//    System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//    int choice = uiHelper.askForGrammarChoice();
//    assertEquals(2, choice);
//  }
//
//  @Test
//  void askForGrammarChoice_HandlesQuit() throws IOException {
//    String input = "q\n";
//    System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//    int choice = uiHelper.askForGrammarChoice();
//    assertEquals(-1, choice);
//  }
//
//  @Test
//  void askForGrammarChoice_HandlesNextPage() throws IOException {
//    // Simulate next page navigation
//    String input = "n\n3\nq\n";
//    System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//    int choice = uiHelper.askForGrammarChoice();
//    assertEquals(3, choice);
//    assertTrue(outContent.toString().contains("3. Test Grammar"));
//  }
//
//  @Test
//  void askForGrammarChoice_HandlesPreviousPage() throws IOException {
//    // Navigate to the next page, then test previous page navigation
//    String input = "n\np\n1\nq\n";
//    System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//    int choice = uiHelper.askForGrammarChoice();
//    assertEquals(1, choice);
//    assertTrue(outContent.toString().contains("1. Test Grammar"));
//  }
//
//  @Test
//  void askForGrammarChoice_HandlesInvalidInput() throws IOException {
//    // Simulate invalid input followed by a valid selection
//    String input = "invalid\n1\n";
//    System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//    int choice = uiHelper.askForGrammarChoice();
//    assertEquals(1, choice);  // Should eventually return valid choice 1
//    assertTrue(outContent.toString().contains("Invalid input"));
//  }

//  @Test
//  void askForAnotherSentence_ReturnsTrue() {
//    String input = "y\n";
//    System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//    boolean result = uiHelper.askForAnotherSentence(scanner);
//    assertTrue(result);
//  }
//
//  @Test
//  void askForAnotherSentence_ReturnsFalse() {
//    String input = "1\nn\n";
//    System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//    boolean result = uiHelper.askForAnotherSentence(scanner);
//    assertFalse(result);
//  }

  @Test
  void generateAndDisplay_PrintsGeneratedSentence() throws UndefinedProductionException {
    String input = "n\nq\n";
    UIHelper uiHelper1 = new UIHelper(paginator, 5,
        new Scanner(new ByteArrayInputStream(input.getBytes())));
    GeneratorInterface generator = () -> "This is a generated sentence.";

    String output = outContent.toString();
    assertEquals(output, "");

    uiHelper1.generateAndDisplay(generator);
    assertTrue(outContent.toString().contains("This is a generated sentence."));
  }

  @Test
  void testToString() {
    String toString = uiHelper.toString();
    assertTrue(toString.contains("GrammarListPaginator"));
    assertTrue(toString.contains("scanner"));
  }

  @Test
  void testEquals() throws IOException {
    GrammarLoader loader = new GrammarLoader(new FileFolderReader(), tempDir.toString());
    UIHelper uiHelper1 = new UIHelper(loader, 2);
    UIHelper uiHelper2 = new UIHelper(loader, 2);
    UIHelper uiHelper3 = new UIHelper(loader, 3);

    // Check equality with the same instance
    assertEquals(uiHelper1, uiHelper1);

    // Check equality with another instance having the same paginator
    assertEquals(uiHelper1, uiHelper2);

    // Check inequality with a different paginator
    assertNotEquals(uiHelper1, uiHelper3);

    // Check inequality with null and a different type
    assertNotEquals(uiHelper1, null);
    assertNotEquals(uiHelper1, new Object());
  }

  @Test
  void testHashCode() throws IOException {
    GrammarLoader loader = new GrammarLoader(new FileFolderReader(), tempDir.toString());
    UIHelper uiHelper1 = new UIHelper(loader, 2);
    UIHelper uiHelper2 = new UIHelper(loader, 2);
    assertEquals(uiHelper1.hashCode(), uiHelper2.hashCode());
  }
}
