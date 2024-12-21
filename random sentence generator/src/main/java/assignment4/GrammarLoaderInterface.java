package assignment4;

import java.io.IOException;
import java.util.List;

/**
 * Interface for loading grammar files and retrieving grammar file lists.
 */
public interface GrammarLoaderInterface {

  /**
   * Loads a grammar from a specified file path.
   *
   * @param filePath the path to the grammar file
   * @return a GrammarContext object representing the loaded grammar
   * @throws IOException if there's an error reading the file
   */
  GrammarContext loadGrammar(String filePath) throws IOException;

  /**
   * Retrieves a list of grammar file paths.
   *
   * @return a List of Strings, each representing a path to a grammar file
   * @throws IOException if there's an error accessing the grammar files
   */
  List<String> getGrammarFilesList() throws IOException;
}