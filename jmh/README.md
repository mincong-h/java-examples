# JMH

Java Microbenchmark Harness

## Annotation

`@Benchmark` annotates the benchmark method. JMH will produce the generated benchmark code for
this method during compilation, register this method as the benchmark in the benchmark list, read
out the default values from the annotations, and generally prepare the environment for the
benchmark to run.

## Results

    Benchmark                 Mode  Cnt  Score   Error  Units
    BenchmarkTest.benchmark1  avgt   10  4.724 ± 0.206  us/op

The benchmark name is composed by the simple class name and the method name: `BenchmarkTest` and
`benchmark1`. The benchmark mode is set to "avgt", it means the average time per operation. The
benchmark runs by continuously calling `Benchmark` methods, counting the average time to call over
all worker threads. This is the inverse of mode `Throughput`, but with different aggregation policy.
This mode is time-based, and it will run until the iteration time expires. In the report above,
there were 10 measurement iterations (after 2 warm-up iterations). Here the scope of the benchmark
is 4.725 microseconds per operation in average. Here are more details printed during execution:

    # Run progress: 0.00% complete, ETA 00:00:12
    # Fork: 1 of 1
    # Warmup Iteration   1: 7.215 us/op
    # Warmup Iteration   2: 5.102 us/op
    Iteration   1: 4.854 us/op
    Iteration   2: 4.646 us/op
    Iteration   3: 4.553 us/op
    Iteration   4: 4.634 us/op
    Iteration   5: 4.831 us/op
    Iteration   6: 4.888 us/op
    Iteration   7: 4.690 us/op
    Iteration   8: 4.612 us/op
    Iteration   9: 4.609 us/op
    Iteration  10: 4.927 us/op

Before the benchmark `BencharkTest` gets started, JMH calculates the Estimated Time of Arrival (ETA)
to 12 seconds because there are 2 warm-up iterations (1 second per iteration) and 10 measurement
iterations (1 second per iteration). Then, iterations started one-after-another, a score is
displayed to show the speed of each iteration.

## References

- Baeldung, "Microbenchmarking with Java", _Baeldung_, 2019.
  <https://www.baeldung.com/java-microbenchmark-harness>
- Jakob Jenkov, "JMH - Java Microbenchmark Harness", _jenkov.com_, 2015.
  <http://tutorials.jenkov.com/java-performance/jmh.html>
