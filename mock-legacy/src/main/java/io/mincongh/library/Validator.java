package io.mincongh.library;

/**
 * A simple validator.
 *
 * @author Mincong Huang
 * @since 0.1.0
 */
public class Validator {
  private final Context context;

  public Validator(Context context) {
    this.context = context;
  }

  public void validate(String input) {
    if (input.contains(" ")) {
      context.addError("No space allowed.");
    }
  }

  public void doSomethingElse() {
    // no interaction with Context
  }

  public interface Context {
    void addError(String error);

    @SuppressWarnings("unused")
    void neverCalled();
  }
}
