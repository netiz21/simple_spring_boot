package com.thanos.springboot.common.demo.hash;

import com.google.common.collect.ImmutableSet;
import com.google.common.hash.Hashing;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * @author peiheng.zph created on 18/3/6 下午7:45
 * @version 1.0
 */
@Slf4j
public class ConsistentHashingDemo {

  private static final int DATA_SET_SIZE = 1 << 18;

  private static final int BUCKET_SIZE = 8;

  private static final Set<String> DATA_SET = ImmutableSet.copyOf(EnhancedRandom.randomSetOf(DATA_SET_SIZE, String.class));

  private final List<Bucket> buckets = new ArrayList<Bucket>(BUCKET_SIZE) {{
    IntStream.range(0, BUCKET_SIZE).forEach(it -> add(new Bucket()));
  }};

  class Bucket extends HashSet<String> {
  }

  /**
   * Given a hash key and total buckets size, return the target bucket index
   */
  interface Dispatcher extends BiFunction<String, Integer, Integer> {
  }

  public void run() {
    log.info("Total data count {}", DATA_SET.size());

    log.info("Start benchmark modulo hash");
    benchmark(this::moduloHash);

    log.info("Start benchmark ring hash");
    benchmark(this::ringHash);

    log.info("Start benchmark ring hash with virtual node");
    benchmark(this::ringHashWithVirtualNode);
  }

  private void benchmark(Dispatcher func) {
    log.info("==============  Benchmark start  ==============");

    arrange(DATA_SET, buckets, func);
    evaluate(DATA_SET, buckets, func);
    clear(buckets);

    log.info("==============  Benchmark stop  ==============");
  }

  private void arrange(Set<String> dataSet, List<Bucket> buckets, Dispatcher func) {
    dataSet.forEach(it -> buckets.get(func.apply(it, buckets.size())).add(it));
  }

  private void evaluate(Set<String> dataSet, List<Bucket> buckets, Dispatcher function) {
    Map<String, Integer> originIdxMap = new HashMap<>();
    dataSet.forEach(it -> originIdxMap.put(it, function.apply(it, buckets.size())));

    // correctness
    dataSet.forEach(it -> {
      int idx = originIdxMap.get(it);
      if (idx < 0 || idx >= buckets.size() || !buckets.get(idx).contains(it)) {
        throw new IllegalStateException("Hash function not correct!");
      }
    });

    // equality
    DescriptiveStatistics stats = new DescriptiveStatistics();
    buckets.forEach(it -> stats.addValue(it.size()));
    log.info("Variance {} StandardDeviation {}", stats.getVariance(), stats.getStandardDeviation());

    // Rates of change when buckets size increase
    Map<String, Integer> incrIdxMap = new HashMap<>();
    dataSet.forEach(it -> incrIdxMap.put(it, function.apply(it, buckets.size() + 1)));

    log.info("Calculate change when bucket size increase");
    calculateChangeRate(originIdxMap, incrIdxMap);

    // Rates of change when buckets size decrease
    Map<String, Integer> decrIdxMap = new HashMap<>();
    dataSet.forEach(it -> decrIdxMap.put(it, function.apply(it, buckets.size() - 1)));

    log.info("Calculate change when bucket size decrease");
    calculateChangeRate(originIdxMap, decrIdxMap);
  }

  private void calculateChangeRate(Map<String, Integer> originIdxMap, Map<String, Integer> incrIdxMap) {
    int size = originIdxMap.size();
    if (size != incrIdxMap.size()) {
      throw new IllegalStateException("Data count not match");
    }

    int count = originIdxMap.keySet()
        .stream()
        .filter(it -> !originIdxMap.get(it).equals(incrIdxMap.get(it)))
        .collect(Collectors.toSet())
        .size();

    log.info("Change count {}, rate is {}%", count, Math.floorDiv(100 * count, size));
  }

  private void clear(List<Bucket> buckets) {
    buckets.forEach(HashSet::clear);
  }

  private int moduloHash(String key, int bucketSize) {
    return Math.abs(Hashing.md5().hashString(key, StandardCharsets.UTF_8).asInt()) % bucketSize;
  }

  private int ringHash(String key, int bucketSize) {
    int ringEnd = 37;
    Function<String, Double> posProvider = it -> (double) (Math.abs(Hashing.md5().hashString(it, StandardCharsets.UTF_8).asInt()) % ringEnd) / ringEnd;

    TreeMap<Double, Integer> ringNode = new TreeMap<Double, Integer>() {{
      IntStream.range(0, bucketSize).forEach(it -> put(posProvider.apply("RN" + it), it));
    }};

    double position = posProvider.apply(key);
    return ringNode.keySet()
        .stream()
        .filter(it -> it >= position)
        .findFirst()
        .map(ringNode::get)
        .orElse(ringNode.firstEntry().getValue());
  }

  private int ringHashWithVirtualNode(String key, int bucketSize) {
    int ringEnd = 237;
    int virtualNodeCount = 10;
    Function<String, Double> posProvider = it -> (double) (Math.abs(Hashing.md5().hashString(it, StandardCharsets.UTF_8).asInt()) % ringEnd) / ringEnd;

    TreeMap<Double, Integer> ringNode = new TreeMap<Double, Integer>() {{
      IntStream.range(0, bucketSize).forEach(it -> {
        IntStream.range(0, virtualNodeCount).forEach(idx -> put(posProvider.apply("RN" + it + "-" + idx), it));
      });
    }};

    double position = posProvider.apply(key);
    return ringNode.keySet()
        .stream()
        .filter(it -> it >= position)
        .findFirst()
        .map(ringNode::get)
        .orElse(ringNode.firstEntry().getValue());
  }

  public static void main(String[] args) {
    ConsistentHashingDemo demo = new ConsistentHashingDemo();
    demo.run();
  }

}
