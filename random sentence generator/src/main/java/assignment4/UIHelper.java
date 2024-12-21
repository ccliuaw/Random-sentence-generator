package assignment4;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a user interface for generating sentences from grammar files. Manages user
 * interactions and displays grammar choices.
 */
public class UIHelper {

  /**
   * Represents a new line character.
   */
  public static final String NEW_LINE = "\n";
  /**
   * Represents a horizontal ruler for visual separation.
   */
  public static final String HORIZONTAL_RULER =
      NEW_LINE + "----------------------------------------" + NEW_LINE;
  /**
   * The welcome message displayed to users.
   */
  public static final String WELCOME_LINE = "Welcome to the Random Sentence Generator";
  /**
   * The complete program title including welcome message and horizontal rulers.
   */
  public static final String PROGRAM_TITLE = HORIZONTAL_RULER + WELCOME_LINE + HORIZONTAL_RULER;
  /**
   * Text instructing the user how to quit the program.
   */
  public static final String QUIT_CHOICE_TEXT = "q to quit";
  /**
   * Text instructing the user how to move to the next page.
   */
  public static final String NEXT_PAGE_CHOICE_TEXT = "n for next page";
  /**
   * Text instructing the user how to move to the previous page.
   */
  public static final String PREVIOUS_PAGE_CHOICE_TEXT = "p for previous page";
  /**
   * Separator used in output strings.
   */
  public static final String COMMA_OUTPUT_SEPARATOR = ", ";
  /**
   * Represents an empty string.
   */
  public static final String EMPTY_STRING = "";
  /**
   * Opening parenthesis character.
   */
  public static final String OPENING_PARENTHESES = "(";
  /**
   * Closing parenthesis character.
   */
  public static final String CLOSING_PARENTHESIS = ")";
  /**
   * Text prompting the user to enter a grammar choice.
   */
  public static final String ENTER_CHOICE_TEXT = "Enter grammar number to generate:";
  /**
   * Value representing a 'yes' choice.
   */
  public static final String YES_CHOICE_VALUE = "y";
  /**
   * Value representing a back choice.
   */
  public static final String BACK_CHOICE_VALUE = "anything else";
  /**
   * Text asking if the user wants to generate another sentence.
   */
  public static final String GENERATE_ANOTHER_INSTRUCTIONS = "Would you like another? ";
  /**
   * Text explaining how to choose 'yes'.
   */
  public static final String YES_CHOICE_TEXT = YES_CHOICE_VALUE + " for yes";
  /**
   * Text explaining how to go back.
   */
  public static final String BACK_CHOICE_TEXT = BACK_CHOICE_VALUE + " to go back";
  /**
   * Choice for moving to the next page.
   */
  public static final String NEXT_PAGE_CHOICE = "n";
  /**
   * Choice for quitting the program.
   */
  public static final String QUIT_CHOICE = "q";
  /**
   * Choice for moving to the previous page.
   */
  public static final String PREVIOUS_PAGE_CHOICE = "p";
  /**
   * Message displayed when there's no next page available.
   */
  private static final String NO_NEXT_PAGE_MESSAGE = "No next page available.";

  /**
   * Message displayed when there's no previous page available.
   */
  private static final String NO_PREVIOUS_PAGE_MESSAGE = "No previous page available.";
  /**
   * Message displayed for invalid input.
   */
  private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please enter a valid choice.";
  /**
   * Value returned when the user chooses to quit.
   */
  private static final int QUIT_CHOICE_VALUE = -1;

  /**
   * Dash separator.
   */
  public static final String DASH_SEPARATOR = "-";

  private final GrammarListPaginator grammarListPaginator;
  private final Scanner scanner;

  /**
   * Constructs a UIHelper instance
   *
   * @param grammarLoader the grammar loader
   * @throws IOException the io exception
   */
  public UIHelper(GrammarLoader grammarLoader) throws IOException {
    grammarListPaginator = new GrammarListPaginator(grammarLoader);
    scanner = new Scanner(System.in);
  }

  /**
   * Constructs a UIHelper instance with a grammar loader and the count per page.
   *
   * @param grammarLoader the grammar loader
   * @param countPerPage  count per page
   * @throws IOException the io exception
   */
  public UIHelper(GrammarLoader grammarLoader, int countPerPage) throws IOException {
    grammarListPaginator = new GrammarListPaginator(grammarLoader, countPerPage);
    scanner = new Scanner(System.in);
  }

  /**
   * Constructs a UIHelper instance with a grammar list paginator and a scanner.
   *
   * @param grammarListPaginator the paginator for grammar lists
   * @param countPerPage         count per page
   * @param scanner              the scanner for user input
   */
  public UIHelper(GrammarListPaginator grammarListPaginator, int countPerPage, Scanner scanner) {
    this.grammarListPaginator = grammarListPaginator;
    this.scanner = scanner;
  }

  /**
   * Displays the program title.
   */
  public void displayTitle() {
    System.out.println(PROGRAM_TITLE);
  }

  /**
   * Displays the list of available grammars.
   *
   * @throws IOException if there's an error accessing the grammar files
   */
  public void displayGrammarChoices() throws IOException {
    System.out.println("The following grammars are available:\n");
    displayGrammarList();
  }

  /**
   * Displays the current page of grammar choices.
   *
   * @throws IOException if there's an error accessing the grammar files
   */
  private void displayGrammarList() throws IOException {
    GrammarListPage grammarListPage = grammarListPaginator.getCurrentPage();
    List<GrammarContext> grammarContexts = grammarListPage.getGrammarContexts();
    int countPerPage = grammarListPaginator.getCountPerPage();
    int totalPages = (grammarListPaginator.getTotalFileCount() + countPerPage - 1) / countPerPage;
    int pageIndex = grammarListPaginator.getCurrentPageIndex();
    for (int i = 0; i < grammarContexts.size(); i++) {
      int grammarNumber = i + pageIndex * countPerPage + 1;
      System.out.println((grammarNumber) + ". " + grammarContexts.get(i).getGrammarTitle());
    }
    System.out.println("Showing page " + (pageIndex + 1) + " out of " + totalPages);
  }

  /**
   * Prompts the user to choose a grammar and handles pagination.
   *
   * @return the chosen grammar number, or QUIT_CHOICE_VALUE if the user chooses to quit
   * @throws IOException if there's an error accessing the grammar files
   */
  private int askForGrammarChoice() throws IOException {
    while (true) {
      String nextPageChoice =
          grammarListPaginator.hasNextPage() ? COMMA_OUTPUT_SEPARATOR + NEXT_PAGE_CHOICE_TEXT
              : EMPTY_STRING;
      String previousPageChoice =
          grammarListPaginator.hasPreviousPage() ? COMMA_OUTPUT_SEPARATOR
              + PREVIOUS_PAGE_CHOICE_TEXT : EMPTY_STRING;
      int countPerPage = grammarListPaginator.getCountPerPage();
      int pageIndex = grammarListPaginator.getCurrentPageIndex();
      int startChoice = (pageIndex * countPerPage + 1);
      int endChoice = ((pageIndex * countPerPage) + grammarListPaginator.getCurrentPage()
          .getGrammarContexts().size());
      System.out.println(
          ENTER_CHOICE_TEXT + OPENING_PARENTHESES + startChoice + DASH_SEPARATOR
              + endChoice + CLOSING_PARENTHESIS + COMMA_OUTPUT_SEPARATOR
              + OPENING_PARENTHESES
              + QUIT_CHOICE_TEXT
              + nextPageChoice
              + previousPageChoice
              + CLOSING_PARENTHESIS);
      String input = scanner.nextLine();

      switch (input) {
        case QUIT_CHOICE:
          return QUIT_CHOICE_VALUE;
        case NEXT_PAGE_CHOICE:
          if (grammarListPaginator.hasNextPage()) {
            handleNextPage();
            break;
          }
        case PREVIOUS_PAGE_CHOICE:
          if (grammarListPaginator.hasPreviousPage()) {
            handlePreviousPage();
            break;
          }
        default:
          try {
            return Integer.parseInt(input);
          } catch (NumberFormatException e) {
            System.err.println(INVALID_INPUT_MESSAGE);
            displayGrammarChoices();
            break;
          }
      }
    }
  }

  /**
   * Handles the action of moving to the next page of grammars.
   *
   * @throws IOException if there's an error accessing the grammar files
   */
  private void handleNextPage() throws IOException {
    if (grammarListPaginator.hasNextPage()) {
      grammarListPaginator.nextPage();
      displayGrammarChoices();
    } else {
      System.out.println(NO_NEXT_PAGE_MESSAGE);
      displayGrammarChoices();
    }
  }

  /**
   * Handles the action of moving to the previous page of grammars.
   *
   * @throws IOException if there's an error accessing the grammar files
   */
  private void handlePreviousPage() throws IOException {
    if (grammarListPaginator.hasPreviousPage()) {
      grammarListPaginator.previousPage();
      displayGrammarChoices();
    } else {
      System.out.println(NO_PREVIOUS_PAGE_MESSAGE);
      displayGrammarChoices();
    }
  }

  /**
   * Gets the user's selected grammar context.
   *
   * @return the selected GrammarContext, or null if the user chose to quit
   * @throws IOException if there's an error accessing the grammar files
   */
  public GrammarContext getUserGrammarSelection() throws IOException {
    while (true) {
      int choice = askForGrammarChoice();
      if (choice == QUIT_CHOICE_VALUE) {
        return null;
      }

      int index = choice - (grammarListPaginator.getCountPerPage()
          * grammarListPaginator.getCurrentPageIndex()) - 1;
      List<GrammarContext> pageContexts = grammarListPaginator.getCurrentPage()
          .getGrammarContexts();
      if (index < pageContexts.size() && index >= 0) {
        return grammarListPaginator.getCurrentPage().getGrammarContexts().get(index);
      } else {
        System.err.println(INVALID_INPUT_MESSAGE);
        displayGrammarChoices();
      }
    }

  }

  /**
   * Asks the user if they want to generate another sentence.
   *
   * @return true if the user wants another sentence, false otherwise
   */
  private boolean askForAnotherSentence() {
    System.out.println(
        GENERATE_ANOTHER_INSTRUCTIONS + OPENING_PARENTHESES + YES_CHOICE_TEXT
            + COMMA_OUTPUT_SEPARATOR + BACK_CHOICE_TEXT
            + CLOSING_PARENTHESIS);
    String choice = scanner.nextLine();
    return choice.equalsIgnoreCase(YES_CHOICE_VALUE);
  }

  /**
   * Generates and displays sentences using the provided generator.
   *
   * @param generator the sentence generator to use
   * @throws UndefinedProductionException the undefined production exception
   */
  public void generateAndDisplay(GeneratorInterface generator) throws UndefinedProductionException {
    do {
      System.out.println(NEW_LINE + generator.generate() + NEW_LINE);
    } while (askForAnotherSentence());
  }

  @Override
  public String toString() {
    return "UIHelper{" +
        "grammarListPaginator=" + grammarListPaginator +
        ", scanner=" + scanner +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UIHelper uiHelper = (UIHelper) o;
    return Objects.equals(grammarListPaginator, uiHelper.grammarListPaginator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(grammarListPaginator);
  }
}