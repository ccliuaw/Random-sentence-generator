package assignment4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Loads and parses grammar files from a specified directory.
 */
public class GrammarLoader implements GrammarLoaderInterface {

  /**
   * Default title for grammars when not specified in the JSON.
   */
  private static final String DEFAULT_GRAMMAR_TITLE = "Untitled Grammar";

  /**
   * Default description for grammars when not specified in the JSON.
   */
  private static final String DEFAULT_GRAMMAR_DESC = "";

  /**
   * JSON key for the grammar title.
   */
  private static final String GRAMMAR_TITLE_KEY = "grammarTitle";

  /**
   * JSON key for the grammar description.
   */
  private static final String GRAMMAR_DESC_KEY = "grammarDesc";

  /**
   * Error Message when no files in directory
   */
  public static final String NO_FILES_IN_DIRECTORY_ERROR_MESSAGE = "No files found in the given directory";

  private final FileFolderReaderInterface reader;
  private final String directoryPath;
  private final RandomizerInterface randomizer;

  /**
   * Constructs a GrammarLoader with a reader and directory path.
   *
   * @param reader        The file/folder reader to use.
   * @param directoryPath The path to the directory containing grammar files.
   */
  public GrammarLoader(FileFolderReaderInterface reader, String directoryPath) {
    this(reader, directoryPath, new Randomizer());
  }

  /**
   * Constructs a GrammarLoader with a reader, directory path, and randomizer.
   *
   * @param reader        The file/folder reader to use.
   * @param directoryPath The path to the directory containing grammar files.
   * @param randomizer    The randomizer to use for grammar generation.
   */
  public GrammarLoader(
      FileFolderReaderInterface reader, String directoryPath, RandomizerInterface randomizer) {
    this.reader = reader;
    this.directoryPath = directoryPath;
    this.randomizer = randomizer;
  }

  /**
   * Constructs a GrammarLoader with just a directory path.
   *
   * @param directoryPath The path to the directory containing grammar files.
   */
  public GrammarLoader(String directoryPath) {
    this(new FileFolderReader(), directoryPath);
  }

  @Override
  public GrammarContext loadGrammar(String filePath) throws IOException {
    String grammarFileContent = reader.readFile(filePath);
    JSONObject json = new JSONObject(grammarFileContent);
    return parseGrammarContext(json);
  }

  @Override
  public List<String> getGrammarFilesList() throws IOException {
    List<String> filesList = reader.readFolder(directoryPath);
    if (filesList.isEmpty()) {
      throw new IOException(NO_FILES_IN_DIRECTORY_ERROR_MESSAGE);
    }
    return filesList;
  }

  private GrammarContext parseGrammarContext(JSONObject json) {
    Map<String, List<String>> grammarRules = new HashMap<>();
    String grammarTitle = json.optString(GRAMMAR_TITLE_KEY, DEFAULT_GRAMMAR_TITLE);
    String grammarDesc = json.optString(GRAMMAR_DESC_KEY, DEFAULT_GRAMMAR_DESC);

    for (String key : json.keySet()) {
      if (key.equalsIgnoreCase(GRAMMAR_TITLE_KEY) || key.equalsIgnoreCase(GRAMMAR_DESC_KEY)) {
        continue;
      }

      JSONArray expansions = json.getJSONArray(key);
      List<String> expansionList = new ArrayList<>();

      for (int i = 0; i < expansions.length(); i++) {
        expansionList.add(expansions.getString(i).trim());
      }

      grammarRules.put(key.toLowerCase(), expansionList);
    }
    return new GrammarContext(grammarRules, grammarTitle, grammarDesc, randomizer);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GrammarLoader that = (GrammarLoader) o;
    return Objects.equals(directoryPath, that.directoryPath)
        && Objects.equals(reader, that.reader)
        && Objects.equals(randomizer, that.randomizer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reader, directoryPath, randomizer);
  }

  @Override
  public String toString() {
    return String.format(
        "GrammarLoader{reader=%s, directoryPath='%s', randomizer=%s}",
        reader, directoryPath, randomizer);
  }
}