# Mocking Frameworks

## Mockito: Object Creation

Creating mock objects using JUnit annotation `@RunWith` and Mockito JUnit
Runner. But only one runner is allowed, so you cannot use another runner
anymore:

```java
@RunWith(MockitoJUnitRunner.class)
public class MyTest {

  @Mock Book mockBook;

  @Test
  public void testSomething() { ... }
}
```

Create mock objects using `MockitoAnnotations.initMocks()` on the target test
suite `MyTest`:

```java
public class MyTest {

  @Mock Book mockBook;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testSomething() { ... }
}
```

Create mock objects using `Mockito.mock()` on each objects to mock:

```java
public class MyTest {
  Book mockBook;

  @Before
  public void setUp() {
    mockBook = Mockito.mock(Book.class);
  }

  @Test
  public void testSomething() { ... }
}
```

## References

- Baeldung, "Mockito Tutorial", _Baeldung_, 2019.
  <https://www.baeldung.com/mockito-series>
