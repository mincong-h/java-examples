package io.mincong.ocpjp.nio;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 *
 *
 * <pre>
 *        Root
 *      /  |   \
 *    d1   f1   d2
 *   /  \      /  \
 * f2    f3  f4    f5
 * </pre>
 *
 * @author Mincong Huang
 */
public class DirectoryTreeTest {

  @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private Path source;

  private Path target;

  private static final int MAX_DEPTH = 2;

  @Before
  public void setUp() throws Exception {
    Path r = temporaryFolder.getRoot().toPath();
    source = Files.createDirectory(r.resolve("source"));
    target = Files.createDirectory(r.resolve("target"));

    Files.createDirectory(source.resolve("d1"));
    Files.createDirectory(source.resolve("d2"));

    Files.createFile(source.resolve("f1"));
    Files.createFile(source.resolve("d1/f2"));
    Files.createFile(source.resolve("d1/f3"));
    Files.createFile(source.resolve("d2/f4"));
    Files.createFile(source.resolve("d2/f5"));
  }

  @Test
  public void deleteRecursively() throws Exception {
    Files.walkFileTree(
        source,
        new SimpleFileVisitor<Path>() {
          /*
           * Exam tip:
           *
           * Methods `preVisitDirectory` and `visitFile` are passed
           * `BasicFileAttributes` of the path that they operate on. You
           * can use these methods to query file or directory attributes.
           */
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
              throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
            if (e == null) {
              Files.delete(dir);
              return FileVisitResult.CONTINUE;
            } else {
              // Directory iteration failed
              throw e;
            }
          }
        });

    assertThat(Files.notExists(source)).isTrue();
  }

  @Test
  public void copyRecursively() throws Exception {
    Set<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

    Files.walkFileTree(
        source,
        options,
        MAX_DEPTH,
        new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
              throws IOException {
            /*
             * Copy directory `dir` to the same relative path in
             * target directory. For example:
             *
             * relative: ./d1
             * source: source/d1
             * target: target/d1
             */
            Path targetDir = target.resolve(source.relativize(dir));
            try {
              Files.copy(dir, targetDir);
            } catch (FileAlreadyExistsException e) {
              /*
               * Ignore if the target directory exists already.
               * Otherwise, re-throw exception (name is used as
               * regular file or symbolic link).
               */
              if (!Files.isDirectory(targetDir)) {
                throw e;
              }
            }
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
              throws IOException {
            Files.copy(file, target.resolve(source.relativize(file)));
            return FileVisitResult.CONTINUE;
          }
        });

    AtomicInteger dirCount = new AtomicInteger(0);
    AtomicInteger fileCount = new AtomicInteger(0);
    Files.walkFileTree(
        target,
        options,
        MAX_DEPTH,
        new SimpleFileVisitor<Path>() {

          @Override
          public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            dirCount.incrementAndGet();
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            fileCount.incrementAndGet();
            return FileVisitResult.CONTINUE;
          }
        });
    assertThat(dirCount.get()).isEqualTo(3);
    assertThat(fileCount.get()).isEqualTo(5);

    Files.newDirectoryStream(target).forEach(System.out::println);
  }

  @Test
  public void newDirectoryStream_directory() throws Exception {
    Set<String> filenames = new HashSet<>();
    Files.newDirectoryStream(source).forEach(p -> filenames.add(p.getFileName().toString()));
    assertThat(filenames).containsExactlyInAnyOrder("f1", "d1", "d2");
  }

  @Test
  public void newDirectoryStream_filtered() throws Exception {
    Set<String> filenames = new HashSet<>();
    // The glob pattern, see https://en.wikipedia.org/wiki/Glob_(programming)
    Files.newDirectoryStream(source, "f*").forEach(p -> filenames.add(p.getFileName().toString()));
    assertThat(filenames).containsExactly("f1");
  }

  @Test(expected = NotDirectoryException.class)
  public void newDirectoryStream_regularFile() throws Exception {
    Files.newDirectoryStream(source.resolve("f1"));
  }
}
