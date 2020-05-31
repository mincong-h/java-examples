package io.mincongh.mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/** @author Mincong Huang */
public class MockitoArgumentCaptor2Test {

  @Captor private ArgumentCaptor<String> stringCaptor;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void createCaptorWithInitMocks() {
    List<String> strings = Mockito.spy(new ArrayList<>());
    strings.add("Foo");
    Mockito.verify(strings).add(stringCaptor.capture());
    assertThat(stringCaptor.getValue()).isEqualTo("Foo");
    assertThat(stringCaptor.getAllValues()).containsExactly("Foo");
  }
}
