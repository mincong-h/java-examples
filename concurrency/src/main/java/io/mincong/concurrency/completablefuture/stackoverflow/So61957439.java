package io.mincong.concurrency.completablefuture.stackoverflow;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Call a WebService and a REST API using JDK8 Streams and CompletableFuture
 *
 * <p>https://stackoverflow.com/questions/61957439/call-a-webservice-and-a-rest-api-using-jdk8-streams-and-completablefuture
 */
public class So61957439 {

  public static void main(String[] args) throws Exception {
    new So61957439().run();
  }

  private void run() throws Exception {
    List<CompletableFuture<Stream<Result>>> futures =
        Stream.of("A", "B", "C")
            .map(
                s -> {
                  Set<String> criteria = new HashSet<>(Arrays.asList(s + "1", s + "2", s + "3"));
                  return getProducts(criteria).thenCompose(this::validateProducts);
                })
            .collect(Collectors.toList());
    print("Waiting for results");
    CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
    print("All completable futures done");

    for (CompletableFuture<Stream<Result>> cf : futures) {
      List<Result> results = cf.get().collect(Collectors.toList());
      print(results);
    }
  }

  CompletableFuture<Stream<Product>> getProducts(final Set<String> criteria) {
    return CompletableFuture.supplyAsync(
        () -> {
          print("Getting products for " + criteria + " ...");
          try {
            Thread.sleep(3_000);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          print("Products received");
          return criteria.stream().map(Product::new);
        });
  }

  CompletableFuture<Stream<Result>> validateProducts(final Stream<Product> products) {
    return CompletableFuture.supplyAsync(
        () -> {
          print("Validating products: " + products);
          try {
            Thread.sleep(3_000);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          print("Products " + products + " validated");
          return products.map(p -> new Result(p.name));
        });
  }

  private void print(Object object) {
    System.out.println(LocalTime.now() + " - " + object);
  }

  private static class Product {
    private final String name;

    public Product(String name) {
      this.name = name;
    }
  }

  private static class Result {
    private final String name;

    public Result(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "Result{name='" + name + "'}";
    }
  }
}
