package assignment4;

import java.io.IOException;

/**
 * Main class to run the Random Sentence Generator program.
 */
public class Main {

  private static final String NO_GRAMMAR_PATH_ERROR = "PathError: No grammar path provided";
  private static final String IO_ERROR_MESSAGE = "FileError: ";
  private static final String GENERAL_ERROR_MESSAGE = "An unknown error occurred";

  /**
   * The entry point of the program.
   *
   * @param args Command line arguments. The first argument should be the path to the grammar files
   *             directory.
   */
  public static void main(String[] args) {
    try {
      if (args.length == 0) {
        throw new IllegalArgumentException(NO_GRAMMAR_PATH_ERROR);
      }
      String grammarDirectory = args[0];

      GrammarLoader grammarLoader = new GrammarLoader(grammarDirectory);
      UIHelper uiHelper = new UIHelper(grammarLoader);

      runUILoop(uiHelper);

    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    } catch (IOException e) {
      System.err.println(IO_ERROR_MESSAGE + e.getMessage());
    } catch (Exception e) {
      System.err.println(GENERAL_ERROR_MESSAGE + e.getMessage());
    }
  }

  /**
   * Runs the main program loop, handling user interactions.
   *
   * @param uiHelper The UIHelper instance to manage user interactions.
   * @throws IOException If there's an error accessing grammar files.
   */
  private static void runUILoop(UIHelper uiHelper) throws IOException {
    while (true) {
      uiHelper.displayTitle();
      uiHelper.displayGrammarChoices();

      GrammarContext choiceGrammarContext = uiHelper.getUserGrammarSelection();

      if (choiceGrammarContext == null) { // User chose to quit
        break;
      }

      GeneratorInterface sentenceGenerator = new SentenceGenerator(choiceGrammarContext);
      try {
        uiHelper.generateAndDisplay(sentenceGenerator);
      } catch (UndefinedProductionException e) {
        System.err.println(e.getMessage());
        System.err.flush(); // Flush to ensure immediate display
      }
    }
  }
}