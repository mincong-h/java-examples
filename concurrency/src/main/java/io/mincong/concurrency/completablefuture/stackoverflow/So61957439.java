package io.mincong.concurrency.completablefuture.stackoverflow;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Call a WebService and a REST API using JDK8 Streams and CompletableFuture.
 * https://stackoverflow.com/questions/61957439/
 *
 * <p>Example output of the program:
 *
 * <pre>
 * 08:20:04.113561 - Getting products for [A1, A2, A3] ...
 * 08:20:04.108563 - Getting products for [C3, C1, C2] ...
 * 08:20:04.115345 - Getting products for [B2, B3, B1] ...
 * 08:20:04.119002 - Waiting for results
 * 08:20:07.144373 - Products received
 * 08:20:07.144373 - Products received
 * 08:20:07.144373 - Products received
 * 08:20:07.157554 - Validating products: java.util.stream.ReferencePipeline$3@4dd50673
 * 08:20:07.157750 - Validating products: java.util.stream.ReferencePipeline$3@4f6bfabe
 * 08:20:07.158084 - Validating products: java.util.stream.ReferencePipeline$3@42eeb195
 * 08:20:10.160118 - Products java.util.stream.ReferencePipeline$3@4dd50673 validated
 * 08:20:10.160114 - Products java.util.stream.ReferencePipeline$3@42eeb195 validated
 * 08:20:10.160145 - Products java.util.stream.ReferencePipeline$3@4f6bfabe validated
 * 08:20:10.163970 - All completable futures done
 * 08:20:10.164412 - [Result{name='A1'}, Result{name='A2'}, Result{name='A3'}]
 * 08:20:10.165237 - [Result{name='B2'}, Result{name='B3'}, Result{name='B1'}]
 * 08:20:10.165436 - [Result{name='C3'}, Result{name='C1'}, Result{name='C2'}]
 * </pre>
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
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
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
