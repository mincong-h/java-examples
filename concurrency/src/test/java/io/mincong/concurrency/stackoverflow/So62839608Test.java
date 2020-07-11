package io.mincong.concurrency.stackoverflow;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

/** CompletableFuture - Run multiple rest calls in parallel and get different result */
class So62839608Test {

  static class Account {
    String fieldA;
    String fieldB;
    String fieldC;

    Account() {}

    Account(String fieldA, String fieldB, String fieldC) {
      this.fieldA = fieldA;
      this.fieldB = fieldB;
      this.fieldC = fieldC;
    }
  }

  @Test
  void name() {
    CompletableFuture<String> cfA = new CompletableFuture<>();
    CompletableFuture<String> cfB = new CompletableFuture<>();
    CompletableFuture<String> cfC = new CompletableFuture<>();

    CompletableFuture<Account> account =
        CompletableFuture.allOf(cfA, cfB, cfC)
            .thenApply(
                ignored -> {
                  String a = cfA.join();
                  String b = cfB.join();
                  String c = cfC.join();
                  return new Account(a, b, c);
                });

    cfA.complete("A");
    cfB.complete("B");
    cfC.complete("C");

    assertThat(account.join().fieldA).isEqualTo("A");
    assertThat(account.join().fieldB).isEqualTo("B");
    assertThat(account.join().fieldC).isEqualTo("C");
  }

  @Test
  void name2() {
    CompletableFuture<String> cfA = new CompletableFuture<>();
    CompletableFuture<String> cfB = new CompletableFuture<>();
    CompletableFuture<String> cfC = new CompletableFuture<>();

    Account account = new Account();
    CompletableFuture.allOf(cfA, cfB, cfC)
        .thenRun(
            () -> {
              if (!cfA.isCompletedExceptionally()) {
                account.fieldA = cfA.join();
              }
              if (!cfB.isCompletedExceptionally()) {
                account.fieldB = cfB.join();
              }
              if (!cfC.isCompletedExceptionally()) {
                account.fieldC = cfC.join();
              }
            });

    cfA.complete("A");
    cfB.complete("B");
    cfC.complete("C");

    assertThat(account.fieldA).isEqualTo("A");
    assertThat(account.fieldB).isEqualTo("B");
    assertThat(account.fieldC).isEqualTo("C");
  }
}
