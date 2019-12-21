package io.mincongh.library.mockito;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;

/** @author Mincong Huang */
@RunWith(MockitoJUnitRunner.class)
public class MockitoArgumentCaptorTest {

  @Captor private ArgumentCaptor<String> stringCaptor;

  @Test
  public void createCaptorWithAnnotationAndJUnitRunner() {
    List<String> strings = Mockito.spy(new ArrayList<>());
    strings.add("Foo");
    Mockito.verify(strings).add(stringCaptor.capture());
    assertThat(stringCaptor.getValue()).isEqualTo("Foo");
    assertThat(stringCaptor.getAllValues()).containsExactly("Foo");
  }

  @Test
  public void captureOneValue() {
    List<String> strings = Mockito.spy(new ArrayList<>());
    strings.add("Foo");
    Mockito.verify(strings).add(stringCaptor.capture());
    assertThat(stringCaptor.getValue()).isEqualTo("Foo");
    assertThat(stringCaptor.getAllValues()).containsExactly("Foo");
  }

  @Test
  public void captureTwoValues() {
    List<String> strings = Mockito.spy(new ArrayList<>());
    strings.add("Foo");
    strings.add("Bar");
    Mockito.verify(strings, times(2)).add(stringCaptor.capture());
    assertThat(stringCaptor.getValue()).isEqualTo("Bar");
    assertThat(stringCaptor.getAllValues()).containsExactly("Foo", "Bar");
  }

  @Test
  public void captureOnlyOneParam() {
    List<String> strings = Mockito.spy(new ArrayList<>());
    strings.add(0, "Foo");
    /*
     org.mockito.exceptions.misusing.InvalidUseOfMatchersException:
     Invalid use of argument matchers!
     2 matchers expected, 1 recorded:
     -> at io.mincongh.library.mockito.verify.MockitoArgumentCaptorTest.captureOneParam(MockitoArgumentCaptorTest.java:54)

     This exception may occur if matchers are combined with raw values:
         //incorrect:
         someMethod(anyObject(), "raw String");
     When using matchers, all arguments have to be provided by matchers.
     For example:
         //correct:
         someMethod(anyObject(), eq("String by matcher"));
    */
    // Mockito.verify(strings).add(0, stringCaptor.capture());
    Mockito.verify(strings).add(anyInt(), stringCaptor.capture());
    assertThat(stringCaptor.getValue()).isEqualTo("Foo");
    assertThat(stringCaptor.getAllValues()).containsExactly("Foo");
  }

  @Test
  public void createCaptorWithStaticMethod() {
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    List<String> strings = Mockito.spy(new ArrayList<>());
    strings.add("Foo");
    Mockito.verify(strings).add(captor.capture());
    assertThat(captor.getValue()).isEqualTo("Foo");
    assertThat(captor.getAllValues()).containsExactly("Foo");
  }

  @Test
  public void withoutArgumentCaptor() {
    List<String> strings = Mockito.spy(new ArrayList<>());
    strings.add("Foo");
    Mockito.verify(strings).add("Foo"); // You don't have to use ArgumentCaptor
  }
}
