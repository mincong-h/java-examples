package io.mincongh.jmh;

import java.util.concurrent.TimeUnit;
import org.apache.commons.codec.digest.MurmurHash3;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class HashBenchmarkTest {
  private static String[] ARR;

  static {
    ARR = new String[10_000];
    for (int i = 0; i < ARR.length; i++) {
      ARR[i] = String.valueOf(i);
    }
  }

  @Test
  public void runBenchmark() throws Exception {
    Options opt =
        new OptionsBuilder()
            // Specify which benchmarks to run.
            // You can be more specific if you'd like to run only one benchmark per test.
            .include(this.getClass().getName() + ".*")
            // Set the following options as needed
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.MICROSECONDS)
            .warmupTime(TimeValue.seconds(1))
            .warmupIterations(2)
            .measurementTime(TimeValue.seconds(1))
            .measurementIterations(20)
            .threads(2)
            .forks(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            // .jvmArgs("-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining")
            // .addProfiler(WinPerfAsmProfiler.class)
            .build();
    new Runner(opt).run();
  }

  // The JMH samples are the best documentation for how to use it
  // http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
  @State(Scope.Thread)
  public static class BenchmarkState {
    String[] values;

    @Setup(Level.Trial)
    public void initialize() {
      values = ARR;
    }
  }

  @Benchmark
  public void murmur3(BenchmarkState state, Blackhole bh) {
    for (String v : state.values) {
      bh.consume(MurmurHash3.hash128(v));
    }
  }

  @Benchmark
  public void javaHashcode(BenchmarkState state, Blackhole bh) {
    for (String v : state.values) {
      bh.consume(v.hashCode());
    }
  }
}
