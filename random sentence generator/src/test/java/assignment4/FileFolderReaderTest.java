package assignment4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileFolderReaderTest {

  private FileFolderReader fileFolderReader;
  @TempDir
  Path tempDir;

  @BeforeEach
  void setUp() {
    fileFolderReader = new FileFolderReader();
  }

  @Test
  void readFile_ExistingFile_ReturnsContent() throws IOException {
    Path file = tempDir.resolve("test.txt");
    Files.writeString(file, "Test content");

    String content = fileFolderReader.readFile(file.toString());

    assertEquals("Test content", content);
  }

  @Test
  void readFile_NonExistentFile_ThrowsIOException() {
    Path nonExistentFile = tempDir.resolve("non-existent.txt");

    IOException exception = assertThrows(IOException.class,
        () -> fileFolderReader.readFile(nonExistentFile.toString()));

    assertTrue(exception.getMessage().startsWith(FileFolderReader.FILE_NOT_FOUND_ERROR_PREFIX));
  }

  @Test
  void readFolder_ExistingFolder_ReturnsFileList() throws IOException {
    Path file1 = tempDir.resolve("file1.txt");
    Path file2 = tempDir.resolve("file2.txt");
    Files.createFile(file1);
    Files.createFile(file2);

    List<String> files = fileFolderReader.readFolder(tempDir.toString());

    assertEquals(2, files.size());
    assertTrue(files.contains(file1.toString()));
    assertTrue(files.contains(file2.toString()));
  }

  @Test
  void readFolder_EmptyFolder_ReturnsEmptyList() throws IOException {
    List<String> files = fileFolderReader.readFolder(tempDir.toString());

    assertTrue(files.isEmpty());
  }

  @Test
  void readFolder_NonExistentFolder_ThrowsIOException() {
    Path nonExistentFolder = tempDir.resolve("non-existent-folder");

    IOException exception = assertThrows(IOException.class,
        () -> fileFolderReader.readFolder(nonExistentFolder.toString()));

    assertTrue(exception.getMessage().startsWith(FileFolderReader.DIRECTORY_NOT_FOUND_ERROR_PREFIX));
  }

  @Test
  void readFolder_FileInsteadOfFolder_ThrowsIOException() throws IOException {
    Path file = tempDir.resolve("file.txt");
    Files.createFile(file);

    IOException exception = assertThrows(IOException.class,
        () -> fileFolderReader.readFolder(file.toString()));

    assertTrue(exception.getMessage().startsWith(FileFolderReader.DIRECTORY_NOT_FOUND_ERROR_PREFIX));
  }

  @Test
  void readFolder_IgnoresSubdirectories() throws IOException {
    Path file = tempDir.resolve("file.txt");
    Path subdir = tempDir.resolve("subdir");
    Files.createFile(file);
    Files.createDirectory(subdir);

    List<String> files = fileFolderReader.readFolder(tempDir.toString());

    assertEquals(1, files.size());
    assertTrue(files.contains(file.toString()));
  }

  @Test
  void readFile_LargeFile_ReadsCorrectly() throws IOException {
    Path largeFile = tempDir.resolve("large.txt");
    StringBuilder largeContent = new StringBuilder();
    for (int i = 0; i < 100000; i++) {
      largeContent.append("Line ").append(i).append("\n");
    }
    Files.writeString(largeFile, largeContent.toString());

    String content = fileFolderReader.readFile(largeFile.toString());

    assertEquals(largeContent.toString(), content);
  }

  @Test
  void readFolder_ManyFiles_ListsAllFiles() throws IOException {
    for (int i = 0; i < 1000; i++) {
      Files.createFile(tempDir.resolve("file" + i + ".txt"));
    }

    List<String> files = fileFolderReader.readFolder(tempDir.toString());

    assertEquals(1000, files.size());
  }
}