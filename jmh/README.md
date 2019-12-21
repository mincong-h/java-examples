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

## Options

Options can be configured from JMH command line, JMH runner (`org.openjdk.jmh.runner.Runner`) or JMH
annotations (`org.openjdk.jmh.annotations.*`). More precisely, the options of JMH runner are
configured via Options Builder (`org.openjdk.jmh.runner.options.OptionsBuilder`). Here is a
comparison of different options from these three sources:

CLI | Options Builder | Annotations | Description
:---: | :---- | :---- | :---
`-i` | `#measurementIterations` | `@Measurement#interations` | Number of measurement to do.
`-bs` | `#measurementBatchSize` | `@Measurement#batchSize` | Batch size: number of benchmark method calls per operation.
`-r` | `#measurementTime` | `@Measurement#{time,timeUnit}` | Minimum time to spend at each measurement iteration.
`-wi` | `#warmupIterations` | `@Warmup#iterations` | Number of warmup iterations to do.
`-wbs` | `#warmupBatchSize` | `@Warmup#batchSize` | Warmup batch size: number of benchmark method calls per operation.
`-w` | `#warmupTime` | `@Warmup#{time,timeUnit}` | Minimum time to spend at each warmup iteration.
`-to` | `#timeout` | `@Timeout` | Timeout for benchmark iteration.
`-t` | `#threads` | `@Threads` | Number of worker threads to run with.
`-bm` | `#mode` | `@Mode` | Benchmark mode.
`-si` | `#syncIterations` | - | Should JMH synchronize iterations?
`-gc` | `#shouldDoGC` | - | Should JMH force GC between iterations?
`-foe` | `#shouldFailOnError` || Should JMH fail immediately if any benchmark had experienced an unrecoverable error?
`-v` | `#verbosity` | - | Verbosity mode.
_(none)_ | - | `@Benchmark` | Benchmarks to run (regexp+).
`-f` | `#forks` | `@Fork#value` | How many times to fork a single benchmark.
`-wf` | `#warmupForks` | `@Foke#warmups` | How many warmup forks to make for a single benchmark.
`-o` | `#output` | - | Redirect human-readable output to a given file.
`-rff` | `#result` | - | Write machine-readable results to a given file.
`-prof` | `#addProfiler` || Use profilers to collect additional benchmark data.
`-tg` | `#threadGroups` | `GroupThreads` | Override thread group distribution for asymmetric benchmarks.
`-jvm` | `#jvm` | `@Fork#jvm` | Use given JVM for runs. This option only affects forked runs.
`-jvmArgs` | `#jvmArgs` | `@Fork#jvmArgs` | Use given JVM arguments. Most options are inherited from the host VM options, but in some cases you want to pass the options only to a forked VM.
`-jvmArgsAppend` | `#jvmArgsAppend` | `@Fork#jvmAppend` | Same as jvmArgs, but append these options after the already given JVM args.
`-jvmArgsPrepend` | `#jvmArgsPrepend` | `@Fork#jvmPrepend` | Same as jvmArgs, but prepend these options before the already given JVM arg.

This table may not be up-to-date. To check the latest options available, you can use the following
classes from JMH Core:

- `org.openjdk.jmh.runner.options.CommandLineOptions` for CLI options
- `org.openjdk.jmh.runner.options.OptionsBuilder` for Options Builder
- `org.openjdk.jmh.annotations.*` for annotations

## References

- Baeldung, "Microbenchmarking with Java", _Baeldung_, 2019.
  <https://www.baeldung.com/java-microbenchmark-harness>
- Jakob Jenkov, "JMH - Java Microbenchmark Harness", _jenkov.com_, 2015.
  <http://tutorials.jenkov.com/java-performance/jmh.html>
