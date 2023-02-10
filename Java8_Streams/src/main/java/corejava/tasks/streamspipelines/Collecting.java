package corejava.tasks.streamspipelines;

import corejava.tasks.streamspipelines.util.CourseResult;
import corejava.tasks.streamspipelines.util.Person;
import corejava.tasks.streamspipelines.util.Table;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.lang.Math.max;

public class Collecting {
    public static final Set<String> programming = new TreeSet<>(Set.of("Lab 1. Figures", "Lab 2. War and Peace", "Lab 3. File Tree"));
    public static final Set<String> history = new TreeSet<>(Set.of("Shieldwalling", "Phalanxing", "Wedging", "Tercioing"));
    private boolean prog = true;

    public int sum(IntStream stream) {
        return stream.sum();
    }

    public int production(IntStream stream) {
        return stream.reduce(1, (acc, i) -> acc * i);
    }

    public int oddSum(IntStream stream) {
        return stream.filter((x) -> x % 2 != 0).sum();
    }

    public Map<Integer, Integer> sumByRemainder(int n, IntStream stream) {
        Map<Integer, Integer> map = new TreeMap<>();
        stream.forEach((i) -> {
            try {
                map.put(i % n, map.get(i % n) + i);
            } catch (NullPointerException e) {
                map.put(i % n, i);
            }
        });
        return map;
    }

    public Map<Person, Double> totalScores(Stream<CourseResult> stream) {
        Map<Person, Double> map = new HashMap<>();
        stream.forEach((cr) -> {
            map.put(cr.getPerson(), (double) cr.getTaskResults().values().stream().reduce(0, Integer::sum)
                            / (((String) cr.getTaskResults().keySet().toArray()[0]).startsWith("Lab") ? 3 : 4));
        });
        return map;
    }

    public double averageTotalScore(Stream<CourseResult> stream) {
        Collection<Double> vals = new Collecting().totalScores(stream).values();
        return vals.stream().reduce(0.0, Double::sum) / vals.size();
    }

    public Map<String, Double> averageScoresPerTask(Stream<CourseResult> stream) {
        Map<String, Double> map = new HashMap<>();
        AtomicInteger persons = new AtomicInteger();
        stream.map(CourseResult::getTaskResults).forEach((el) -> {
            prog = el.keySet().size() != 0 && ((String) el.keySet().toArray()[0]).startsWith("Lab");
            persons.incrementAndGet();
            el.forEach((key, value) -> {
                try {
                    map.put(key, map.get(key) + value);
                } catch (NullPointerException e) {
                    map.put(key, (double) value);
                }
            });
        });
        programming.stream().filter(task -> prog && !map.containsKey(task)).forEach((el) -> {
            map.put(el, 0.0);
        });
        history.stream().filter(task -> !prog && !map.containsKey(task)).forEach((el) -> {
            map.put(el, 0.0);
        });
        map.forEach((k, v) -> {
            map.put(k, v / persons.get());
        });
        return map;
    }

    public Map<Person, String> defineMarks(Stream<CourseResult> stream) {
        Map<Person, String> result = new HashMap<>();
        new Collecting().totalScores(stream)
                .forEach((key, value) -> {
                    double sc = value;
                    result.put(key, sc < 60 ? "F" :
                            sc < 68 ? "E" :
                                    sc < 75 ? "D" :
                                            sc < 83 ? "C" :
                                                    sc <= 90 ? "B" : "A");
                });
        return result;
    }

    public String easiestTask(Stream<CourseResult> stream) {
        SortedSet<Map.Entry<String, Double>> set
                = new TreeSet<>((o1, o2) -> -Double.compare(o1.getValue(), o2.getValue()));
        set.addAll(new Collecting().averageScoresPerTask(stream).entrySet());
        return ((Map.Entry<String, Double>) set.toArray()[0]).getKey();
    }

    public Collector<CourseResult, Table, String> printableStringCollector() {
        return new Collector<>() {
            @Override
            public Supplier<Table> supplier() {
                return Table::new;
            }

            @Override
            public BiConsumer<Table, CourseResult> accumulator() {
                return Table::add;
            }

            @Override
            public BinaryOperator<Table> combiner() {
                return (table, table2) -> {
                    Table res = new Table();
                    res.addAll(table.getTable());
                    res.addAll(table2.getTable());
                    return res;
                };
            }

            @Override
            public Function<Table, String> finisher() {
                return Table::render;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.UNORDERED);
            }
        };
    }
}
