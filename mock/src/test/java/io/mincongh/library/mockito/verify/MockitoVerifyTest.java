package io.mincongh.library.mockito.verify;

import io.mincongh.library.Validator;
import io.mincongh.library.Validator.Context;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public class MockitoVerifyTest {

  @Test
  public void testArgumentCaptor() {
    Context mockContext = Mockito.mock(Context.class);
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");

    verify(mockContext).addError(captor.capture());
    assertThat(captor.getValue()).isEqualTo("No space allowed.");
  }

  @Test
  public void testVerifyTimesDefault() {
    Context mockContext = Mockito.mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");

    verify(mockContext).addError(anyString());
  }

  @Test
  public void testVerifyTimes1() {
    Context mockContext = Mockito.mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");

    verify(mockContext, times(1)).addError(anyString());
  }

  @Test
  public void testVerifyTimes2() {
    Context mockContext = Mockito.mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");
    validator.validate("Hello Java!");

    verify(mockContext, times(2)).addError(anyString());
  }

  @Test
  public void testVerifyZeroInteractions() {
    Context mockContext = Mockito.mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.doSomethingElse();

    verifyZeroInteractions(mockContext);
  }

  @Test
  public void testVerifyNoMoreInteractions() {
    Context mockContext = Mockito.mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world");

    verify(mockContext).addError("No space allowed.");
    // Ensure there is no more interaction with other
    // methods, such as `validator#doSomethingElse()`
    verifyNoMoreInteractions(mockContext);
  }
}
