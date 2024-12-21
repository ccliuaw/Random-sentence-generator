package assignment4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Manages pagination for a list of grammar files. This class provides methods to navigate through
 * pages of grammar contexts.
 */
public class GrammarListPaginator {

  /**
   * The default number of grammar contexts to display per page.
   */
  public static final int DEFAULT_GRAMMAR_PER_PAGE = 5;
  /**
   * The initial index for the first page of grammar contexts.
   */
  private static final int INITIAL_PAGE_INDEX = 0;

  private final GrammarLoaderInterface loader;
  private final int countPerPage;
  private int currentPageIndex = INITIAL_PAGE_INDEX;
  private List<String> grammarFilesList;

  /**
   * Constructs a GrammarListPaginator with a specified loader and count per page.
   *
   * @param loader       The grammar loader to use.
   * @param countPerPage The number of grammars per page.
   * @throws IOException If there's an error initializing the file list.
   */
  public GrammarListPaginator(GrammarLoader loader, int countPerPage) throws IOException {
    this.loader = loader;
    this.countPerPage = countPerPage;
    this.initializeFileList();
  }

  /**
   * Constructs a GrammarListPaginator with a specified loader and default count per page.
   *
   * @param loader The grammar loader to use.
   * @throws IOException If there's an error initializing the file list.
   */
  public GrammarListPaginator(GrammarLoader loader) throws IOException {
    this(loader, DEFAULT_GRAMMAR_PER_PAGE);
  }

  private void initializeFileList() throws IOException {
    grammarFilesList = loader.getGrammarFilesList();
    currentPageIndex = INITIAL_PAGE_INDEX;
  }

  /**
   * Gets the current page of grammar contexts.
   *
   * @return The current GrammarListPage.
   * @throws IOException If there's an error loading the grammar contexts.
   */
  public GrammarListPage getCurrentPage() throws IOException {
    return createPage(currentPageIndex);
  }

  /**
   * Moves to the next page if available.
   *
   * @return The next GrammarListPage, or null if there is no next page.
   * @throws IOException If there's an error loading the grammar contexts.
   */
  public GrammarListPage nextPage() throws IOException {
    if (hasNextPage()) {
      currentPageIndex++;
      return createPage(currentPageIndex);
    }
    return null;
  }

  /**
   * Moves to the previous page if available.
   *
   * @return The previous GrammarListPage, or null if there is no previous page.
   * @throws IOException If there's an error loading the grammar contexts.
   */
  public GrammarListPage previousPage() throws IOException {
    if (hasPreviousPage()) {
      currentPageIndex--;
      return createPage(currentPageIndex);
    }
    return null;
  }

  private GrammarListPage createPage(int pageIndex) throws IOException {
    int startIndex = pageIndex * countPerPage;
    List<GrammarContext> pageGrammarContexts = loadGrammarContexts(startIndex, countPerPage);
    int remainingFiles = getTotalFileCount() - (startIndex + pageGrammarContexts.size());
    return new GrammarListPage(pageGrammarContexts, remainingFiles);
  }

  /**
   * Checks if there is a next page available.
   *
   * @return true if there is a next page, false otherwise.
   */
  public boolean hasNextPage() {
    return (currentPageIndex + 1) * countPerPage < getTotalFileCount();
  }

  /**
   * Checks if there is a previous page available.
   *
   * @return true if there is a previous page, false otherwise.
   */
  public boolean hasPreviousPage() {
    return currentPageIndex > INITIAL_PAGE_INDEX;
  }

  /**
   * Loads grammar contexts for a specific range of files.
   *
   * @param startIndex The starting index of the files to load.
   * @param count      The number of files to load.
   * @return A list of loaded GrammarContext objects.
   * @throws IOException If there's an error loading the grammar contexts.
   */
  public List<GrammarContext> loadGrammarContexts(int startIndex, int count) throws IOException {
    List<GrammarContext> pageGrammarContexts = new ArrayList<>();
    int endIndex = Math.min(startIndex + count, grammarFilesList.size());

    for (int i = startIndex; i < endIndex; i++) {
      String filePath = grammarFilesList.get(i);
      GrammarContext grammarContext = loader.loadGrammar(filePath);
      pageGrammarContexts.add(grammarContext);
    }

    return pageGrammarContexts;
  }

  /**
   * Gets the current page index.
   *
   * @return The current page index.
   */
  public int getCurrentPageIndex() {
    return currentPageIndex;
  }

  /**
   * Gets the number of grammars per page.
   *
   * @return The count of grammars per page.
   */
  public int getCountPerPage() {
    return countPerPage;
  }

  /**
   * Gets the total number of grammar files.
   *
   * @return The total count of grammar files.
   */
  public int getTotalFileCount() {
    return grammarFilesList.size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GrammarListPaginator that = (GrammarListPaginator) o;
    return countPerPage == that.countPerPage &&
        currentPageIndex == that.currentPageIndex &&
        Objects.equals(loader, that.loader) &&
        Objects.equals(grammarFilesList, that.grammarFilesList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(loader, countPerPage, currentPageIndex, grammarFilesList);
  }

  @Override
  public String toString() {
    return "GrammarListPaginator{" +
        "loader=" + loader +
        ", countPerPage=" + countPerPage +
        ", currentPageIndex=" + currentPageIndex +
        ", grammarFilesList=" + grammarFilesList +
        '}';
  }
}