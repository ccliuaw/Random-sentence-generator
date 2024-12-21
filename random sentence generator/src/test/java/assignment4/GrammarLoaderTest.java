package assignment4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrammarLoaderTest {

  @TempDir
  Path tempDir;

  private GrammarLoader grammarLoader;

  @BeforeEach
  void setUp() throws IOException {
    createTestGrammarFiles();
    grammarLoader = new GrammarLoader(new FileFolderReader(), tempDir.toString());
  }

  private void createTestGrammarFiles() throws IOException {
    String grammar1 = "{\n  \"grammarTitle\": \"Test Grammar 1\",\n  \"grammarDesc\": \"Description 1\",\n  \"noun\": [\n    \"cat\",\n    \"dog\"\n  ],\n  \"verb\": [\n    \"runs\",\n    \"jumps\"\n  ]\n}";
    String grammar2 = "{\n  \"grammarTitle\": \"Test Grammar 2\",\n  \"grammarDesc\": \"Description 2\",\n  \"adjective\": [\n    \"happy\",\n    \"sad\"\n  ]\n}";

    Files.writeString(tempDir.resolve("grammar1.json"), grammar1);
    Files.writeString(tempDir.resolve("grammar2.json"), grammar2);
  }

  @Test
  void loadGrammar_InvalidFile_ThrowsIOException() {
    assertThrows(IOException.class, () ->
        grammarLoader.loadGrammar(tempDir.resolve("nonexistent.json").toString())
    );
  }

  @Test
  void getGrammarFilesList_ReturnsCorrectList() throws IOException {
    List<String> filesList = grammarLoader.getGrammarFilesList();

    assertEquals(2, filesList.size());
    assertTrue(filesList.stream().anyMatch(path -> path.endsWith("grammar1.json")));
    assertTrue(filesList.stream().anyMatch(path -> path.endsWith("grammar2.json")));
  }

  @Test
  void constructor_WithRandomizer_InitializesCorrectly() throws IOException {
    Randomizer randomizer = new Randomizer();
    GrammarLoader loaderWithRandomizer = new GrammarLoader(new FileFolderReader(), tempDir.toString(), randomizer);

    assertNotNull(loaderWithRandomizer);
    // You might want to add more assertions here to check if the randomizer is used correctly
  }

  @Test
  void constructor_WithDirectoryPath_InitializesCorrectly() throws IOException {
    GrammarLoader loaderWithPath = new GrammarLoader(tempDir.toString());

    assertNotNull(loaderWithPath);
    assertEquals(2, loaderWithPath.getGrammarFilesList().size());
  }

  @Test
  void equals_SameObject_ReturnsTrue() throws IOException {
    assertTrue(grammarLoader.equals(grammarLoader));
  }

  @Test
  void equals_DifferentObject_ReturnsFalse() throws IOException {
    GrammarLoader anotherLoader = new GrammarLoader(tempDir.toString());
    assertFalse(grammarLoader.equals(anotherLoader));
    assertFalse(grammarLoader.equals(null));
    assertFalse(grammarLoader.equals("Test"));
  }

  @Test
  void equals_DifferentDirectoryPath_ReturnsFalse() throws IOException {
    GrammarLoader anotherLoader = new GrammarLoader("testDir");
    assertFalse(grammarLoader.equals(anotherLoader));
  }

  @Test
  void hashCode_ConsistentWithEquals() throws IOException {
    FileFolderReaderInterface reader = new FileFolderReader();
    RandomizerInterface randomizer = new Randomizer();
    GrammarLoader anotherLoader = new GrammarLoader(reader, tempDir.toString(), randomizer);
    GrammarLoader loader = new GrammarLoader(reader, tempDir.toString(), randomizer);
    assertEquals(loader.hashCode(), anotherLoader.hashCode());
  }

  @Test
  void toString_ContainsRelevantInformation() {
    String toString = grammarLoader.toString();
    assertTrue(toString.contains("GrammarLoader"));
    assertTrue(toString.contains(tempDir.toString()));
  }
}