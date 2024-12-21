package assignment4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The type File folder reader.
 */
public class FileFolderReader implements FileFolderReaderInterface {

  /**
   * Error message when file not found.
   */
  public static final String FILE_NOT_FOUND_ERROR_PREFIX = "File not found: ";
  /**
   * Error message when directory not found.
   */
  public static final String DIRECTORY_NOT_FOUND_ERROR_PREFIX = "Directory not found or is not a directory: ";

  @Override
  public String readFile(String path) throws IOException {
    Path filePath = Paths.get(path);
    if (Files.exists(filePath)) {
      return Files.readString(filePath);
    } else {
      throw new IOException(FILE_NOT_FOUND_ERROR_PREFIX + path);
    }
  }

  @Override
  public List<String> readFolder(String directoryPath) throws IOException {
    Path folder = Paths.get(directoryPath);
    if (!Files.exists(folder) || !Files.isDirectory(folder)) {
      throw new IOException(DIRECTORY_NOT_FOUND_ERROR_PREFIX + directoryPath);
    }

    try (var stream = Files.list(folder)) {
      return stream
          .filter(Files::isRegularFile)
          .map(Path::toString)
          .sorted()
          .toList();
    }
  }
}
