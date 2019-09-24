package io.mincongh.library.mockito.verify;

import io.mincongh.library.Validator;
import io.mincongh.library.Validator.Context;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public class MockitoVerifyTest {

  @Test
  public void testArgumentCaptor() {
    Context mockContext = mock(Context.class);
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");

    verify(mockContext).addError(captor.capture());
    assertThat(captor.getValue()).isEqualTo("No space allowed.");
  }

  @Test
  public void testVerify_modeTimesDefault() {
    Context mockContext = mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");

    verify(mockContext).addError("No space allowed.");
  }

  @Test
  public void testVerify_modeTimes1() {
    Context mockContext = mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");

    verify(mockContext, times(1)).addError("No space allowed.");
  }

  @Test
  public void testVerify_modeTimes2() {
    Context mockContext = mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");
    validator.validate("Hello Java!");
    validator.validate("Hello!");

    verify(mockContext, times(2)).addError("No space allowed.");
  }

  @Test
  public void testVerify_modeAtLeastOnce() {
    Context mockContext = mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");
    validator.validate("Hello Java!");

    verify(mockContext, atLeastOnce()).addError("No space allowed.");
  }

  @Test
  public void testVerify_modeAtLeast2() {
    Context mockContext = mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");
    validator.validate("Hello Java!");

    verify(mockContext, atLeast(2)).addError("No space allowed.");
  }

  @Test
  public void testVerify_modeAtMost2() {
    Context mockContext = mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");
    validator.validate("Hello Java!");

    verify(mockContext, atMost(2)).addError("No space allowed.");
  }

  @Test
  public void testVerify_modeNever() {
    Context mockContext = mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello!");

    verify(mockContext, never()).addError("No space allowed.");
  }

  @Test
  public void testVerify_modeOnly() {
    Context mockContext = mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world!");

    // verify happen only once and no more interactions
    verify(mockContext, only()).addError("No space allowed.");
  }

  @Test
  public void testVerifyZeroInteractions() {
    Context mockContext = mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.doSomethingElse();

    verifyZeroInteractions(mockContext);
  }

  @Test
  public void testVerifyNoMoreInteractions() {
    Context mockContext = mock(Context.class);

    Validator validator = new Validator(mockContext);
    validator.validate("Hello world");

    verify(mockContext).addError("No space allowed.");
    // Ensure there is no more interaction with mock,
    // such as `Context#neverCalled()`
    verifyNoMoreInteractions(mockContext);
  }

  @Test
  public void testInOrder() {
    Context ctx1 = mock(Context.class);
    Context ctx2 = mock(Context.class);

    ctx1.addError("A");
    ctx2.addError("B");
    ctx2.addError("C");

    // all verifications done in same InOrder instance
    InOrder inOrder = inOrder(ctx1, ctx2);
    inOrder.verify(ctx1).addError("A");
    // you don't have to verify all interactions, but only
    // mocks that are relevant for in-order verification
    inOrder.verify(ctx2).addError("C");
  }
}
