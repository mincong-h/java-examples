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

## Mockito: Verify

### Verify Simple Invocation

Useful when having to ensure the interactions with target mock instance,
without having the possibility the refactor the existing source code.

Defaults to once (times=1).

```java
Context mockContext = Mockito.mock(Context.class);

Validator validator = new Validator(mockContext);
validator.validate("Hello world!");

verify(mockContext).addError("No space allowed.");
```

N times:

```java
Context mockContext = Mockito.mock(Context.class);

Validator validator = new Validator(mockContext);
validator.validate("Hello world!");
validator.validate("Hello Java!");

verify(mockContext, times(2)).addError("No space allowed.");
```

At least once (times=1):

```java
Context mockContext = Mockito.mock(Context.class);

Validator validator = new Validator(mockContext);
validator.validate("Hello world!");
validator.validate("Hello Java!");

verify(mockContext, atLeastOnce()).addError("No space allowed.");
```

At least N times:

```java
Context mockContext = Mockito.mock(Context.class);

Validator validator = new Validator(mockContext);
validator.validate("Hello world!");
validator.validate("Hello Java!");

verify(mockContext, atLeast(2)).addError("No space allowed.");
```

At most N times:

```java
Context mockContext = Mockito.mock(Context.class);

Validator validator = new Validator(mockContext);
validator.validate("Hello world!");
validator.validate("Hello Java!");

verify(mockContext, atMost(2)).addError("No space allowed.");
```

Never (times=0):

```java
Context mockContext = Mockito.mock(Context.class);

new Validator(mockContext);

verify(mockContext, never()).addError("No space allowed.");
```

Only (times=1) and no more interactions:

```java
Context mockContext = Mockito.mock(Context.class);

Validator validator = new Validator(mockContext);
validator.validate("Hello world!");

// verify happen only once and no more interactions
verify(mockContext, only()).addError("No space allowed.");
```

### Verify Zero Interactions

```java
Context mockContext = Mockito.mock(Context.class);

Validator validator = new Validator(mockContext);
validator.doSomethingElse();

verifyZeroInteractions(mockContext);
```

### Verify No More Interactions

```java
Context mockContext = Mockito.mock(Context.class);

Validator validator = new Validator(mockContext);
validator.validate("Hello world");

verify(mockContext).addError("No space allowed.");
// Ensure there is no more interaction with other
// methods, such as `validator#doSomethingElse()`
verifyNoMoreInteractions(mockContext);
```

### Verify With Argument Captor

Create an "Argument Captor" instance so that
whenever target method of mock instance is called, the input argument will be
captured by the captor. It allows verification of that argument.

```java
Context mockContext = Mockito.mock(Context.class);
ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

Validator validator = new Validator(mockContext);
validator.validate("Hello world!");

verify(mockContext).addError(captor.capture());
assertThat(captor.getValue()).isEqualTo("No space allowed.");
```

This is useful when:

- The mock instance is difficult to create (instantiate)
- The argument is important, which must be asserted
- The source code cannot be refactored (too much work, not sure how it works, tightly couples, too much usage, ...)

## References

- Baeldung, "Mockito Tutorial", _Baeldung_, 2019.
  <https://www.baeldung.com/mockito-series>
- Baeldung, "Mockito Verify Cookbook", _Baeldung_, 2018.
  <https://www.baeldung.com/mockito-verify>
- Baeldung, "Mockito ArgumentMatchers", _Baeldung_, 2018.
  <https://www.baeldung.com/mockito-argument-matchers>
