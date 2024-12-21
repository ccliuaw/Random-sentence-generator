package assignment4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

  @TempDir
  Path tempDir;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  void setUp() throws IOException {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
    prepareGrammarFiles();
  }

  @AfterEach
  void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  private void prepareGrammarFiles() throws IOException {
    // Creating temporary JSON grammar files for tests
    String grammar1 = "{ \"grammarTitle\": \"Test Grammar 1\", \"grammarDesc\": \"Description 1\", \"start\": [\"<noun>\"], \"noun\": [\"cat\", \"dog\"], \"verb\": [\"runs\", \"jumps\"] }";
    String grammar2 = "{ \"grammarTitle\": \"Test Grammar 2\", \"grammarDesc\": \"Description 2\", \"start\": [\"<adjective>\"],\"adjective\": [\"happy\", \"sad\"] }";
    String grammar3 = "{ \"grammarTitle\": \"Test Grammar 3\", \"grammarDesc\": \"Description 3\", \"start\": [\"<interjection>\"], \"interjection\": [\"wow\", \"yay\"] }";
    String grammar4 = "{ \"grammarTitle\": \"Test Grammar 3\", \"grammarDesc\": \"Description 3\", \"start\": [\"<interjection>\"], \"interjection\": [\"wow\", \"yay\"] }";
    String grammar5 = "{ \"grammarTitle\": \"Test Grammar 3\", \"grammarDesc\": \"Description 3\", \"start\": [\"<interjection>\"], \"interjection\": [\"wow\", \"yay\"] }";
    String grammar6 = "{ \"grammarTitle\": \"Test Grammar 3\", \"grammarDesc\": \"Description 3\", \"start\": [\"<interjection>\"], \"interjection\": [\"wow\", \"yay\"] }";

    Files.writeString(tempDir.resolve("grammar1.json"), grammar1);
    Files.writeString(tempDir.resolve("grammar2.json"), grammar2);
    Files.writeString(tempDir.resolve("grammar3.json"), grammar3);
    Files.writeString(tempDir.resolve("grammar4.json"), grammar3);
    Files.writeString(tempDir.resolve("grammar5.json"), grammar3);
    Files.writeString(tempDir.resolve("grammar6.json"), grammar3);
  }

  @Test
  void main_NoArgumentsProvided_PrintsErrorMessage() {
    Main.main(new String[]{});  // No arguments provided
    assertTrue(errContent.toString().contains("No grammar path provided"));
  }

  @Test
  void main_ValidArgumentsProvided_RunsSuccessfully() {
    String input = "1\nq\n"; // Select first grammar, then quit
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    Main.main(new String[]{tempDir.toString()});

    String output = outContent.toString();
    assertTrue(output.contains("Welcome to the Random Sentence Generator"));
    assertTrue(output.contains("The following grammars are available:"));
    assertTrue(output.contains("Test Grammar"));
    assertTrue(output.contains("Showing page 1 out of 2"));
  }

  @Test
  void canThrowAndCatchUndefinedProductionException() {
    try {
      throw new UndefinedProductionException("yes");
    } catch (UndefinedProductionException e) {
      assertEquals(e.getMessage(), "yes");
    }
  }

  @Test
  void main_GenerateMultipleSentences_RunsSuccessfully() {
    String input = String.join(System.lineSeparator(), (new String[]{"1", "y", "y", "n",
        "q"})); // Select first grammar, generate multiple sentences, then quit
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    Main.main(new String[]{tempDir.toString()});

    String output = outContent.toString();
//    assertEquals(output, "");
    assertTrue(output.contains("Welcome to the Random Sentence Generator"));
    assertTrue(output.contains("Would you like another"));

    // Count occurrences of "Would you like another? (y/n)"
    int count = output.split("Would you like another").length - 1;
    assertEquals(3, count, "Should have asked for another sentence 3 times");
  }

  @Test
  void main_NavigateThroughPages_RunsSuccessfully() {
    String input = "n\n1\nq\n"; // Go to next page, select first grammar, then quit
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    Main.main(new String[]{tempDir.toString()});

    String output = outContent.toString();
    assertTrue(output.contains("Welcome to the Random Sentence Generator"));
    assertTrue(output.contains("The following grammars are available:"));
    assertTrue(output.contains("Test Grammar 3"));
  }

  @Test
  void main_NavigatePreviousPage_RunsSuccessfully() throws IOException {
    // Navigate to the next page first, then go back to the previous page and select a grammar
    String input = "n\np\n1\nq\n"; // Go to next page, back to previous page, select first grammar, then quit
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    Main.main(new String[]{tempDir.toString()});

    String output = outContent.toString();
    assertTrue(output.contains("Welcome to the Random Sentence Generator"));
    assertTrue(output.contains("The following grammars are available:"));
    assertTrue(output.contains("Test Grammar 1"));
  }
}
