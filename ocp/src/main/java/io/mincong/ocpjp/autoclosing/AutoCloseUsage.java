package io.mincong.ocpjp.autoclosing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Mincong Huang
 */
@SuppressWarnings({"unused"})
public class AutoCloseUsage {

  static void readFileContents(String fileName) {
    File file = new File(fileName);
    /*
     * 1. Code to initialize `in` can throw `FileNotFoundException`;
     *    calling close on `in` can throw `IOException`.
     * 2. The variable(s) defined in a try-with-resources statement
     *    are implicitly final.
     * 3. You can initialize multiple resources in a try-with-
     *    resources statement, separated by a semicolon (;).
     * 4. It isn't obligatory for a semicolon (;) to follow the
     *    declaration of the last resource.
     * 5. Resources must implement `java.io.AutoClosable` or its
     *    sub-interfaces (directly or indirectly).
     * 6. The resources declared with try-with-resources are closed
     *    in the reverse order of their declaration. E.g. if the
     *    declaration order is "A; B", then the close order is
     *    `B; A`.
     */
    try (FileInputStream in = new FileInputStream(file)) {
      // Do something...
      /*
       * 1. If you assign a new value to `in`, the code will not
       *    compile. It is implicitly final.
       * 2. The scope of `in` is limited to `try` block. Using it
       *    outside the `try` block will not compile.
       */
    }
    /*
     * Catch `IOException`, which is superclass of
     * `FileNotFoundException`.
     */
    catch (IOException e) {
      e.printStackTrace();
    }
    /*
     * Unlike a regular `try` block, which must be followed by either
     * a `catch` or a `finally` block, the `try` block defined in the
     * preceding code wasn't followed by either a `catch` or a
     * `finally` block.
     */
  }
}
