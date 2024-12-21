package assignment4;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a page of grammar contexts in a paginated list. This class holds a list of grammar
 * contexts for a single page and information about remaining files.
 */
public class GrammarListPage {

  private final int remainingFiles;
  private final List<GrammarContext> pageGrammarContexts;

  /**
   * Constructs a GrammarListPage with the specified grammar contexts and number of remaining
   * files.
   *
   * @param pageGrammarContexts The list of grammar contexts for this page.
   * @param remainingFiles      The number of files remaining after this page.
   */
  public GrammarListPage(List<GrammarContext> pageGrammarContexts, int remainingFiles) {
    this.remainingFiles = remainingFiles;
    this.pageGrammarContexts = pageGrammarContexts;
  }

  /**
   * Gets the list of grammar contexts for this page.
   *
   * @return An unmodifiable list of the grammar contexts.
   */
  public List<GrammarContext> getGrammarContexts() {
    return Collections.unmodifiableList(pageGrammarContexts);
  }

  /**
   * Checks if there are more files after this page.
   *
   * @return true if there are more files, false otherwise.
   */
  public boolean hasNext() {
    return remainingFiles > 0;
  }

  /**
   * Gets the number of files remaining after this page.
   *
   * @return The number of remaining files.
   */
  public int getRemainingFiles() {
    return remainingFiles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GrammarListPage that = (GrammarListPage) o;
    return remainingFiles == that.remainingFiles &&
        Objects.equals(pageGrammarContexts, that.pageGrammarContexts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(remainingFiles, pageGrammarContexts);
  }

  @Override
  public String toString() {
    return "GrammarListPage{" +
        "remainingFiles=" + remainingFiles +
        ", pageGrammarContexts=" + pageGrammarContexts +
        '}';
  }
}