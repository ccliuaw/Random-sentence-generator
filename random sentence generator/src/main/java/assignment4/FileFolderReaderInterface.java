package assignment4;

import java.io.IOException;
import java.util.List;

/**
 * An interface for reading files and folders. Implementations of this interface should provide
 * methods to read the contents of individual files and list files within a directory.
 */
public interface FileFolderReaderInterface {

  /**
   * Reads the contents of a file at the specified path.
   *
   * @param filePath The path to the file to be read.
   * @return A string containing the contents of the file.
   * @throws IOException If an I/O error occurs while reading the file.
   */
  String readFile(String filePath) throws IOException;

  /**
   * Lists all files in the specified directory.
   *
   * @param directoryPath The path to the directory to be read.
   * @return A list of strings, each representing the path of a file in the directory.
   * @throws IOException If an I/O error occurs while reading the directory.
   */
  List<String> readFolder(String directoryPath) throws IOException;
}