# Common Errors in Completable Future

## Missing exception handling

Missing exception handling in Completable Future, thus the error failed silently.

Solution: add an error in `whenComplete` so that the exception is logged without changing the result of the result of the future.

```java
future.whenComplete((ok, ex) -> {
  if (ex != null) {
    logger.error("Failed to execute action (...)", ex);
  }
});
```
