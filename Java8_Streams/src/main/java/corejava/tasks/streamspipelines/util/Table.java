package corejava.tasks.streamspipelines.util;

import corejava.tasks.streamspipelines.Collecting;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.lang.Math.max;

public class Table {
    private final List<CourseResult> list = new ArrayList<>();
    private boolean prog = false;
    private int maxLenOfName = 7;

    public List<CourseResult> getTable() { return list; }

    public void add(CourseResult cr) {
        progUpdate(cr);
        updateMaxLen(cr);
        list.add(cr);
    }

    public void addAll(Collection<CourseResult> col) {
        col.forEach(this::add);
    }

    public String render() {
        list.forEach(cr -> {
            Collecting.programming.stream()
                    .filter(task -> prog && !cr.getTaskResults().containsKey(task))
                    .forEach(task -> cr.getTaskResults().put(task, 0));
            Collecting.history.stream()
                    .filter(task -> !prog && !cr.getTaskResults().containsKey(task))
                    .forEach(task -> cr.getTaskResults().put(task, 0));
        });

        StringJoiner head = new StringJoiner(" | ", "", " |\n");
        head.add("Student" + " ".repeat(maxLenOfName - 7));
        Collecting.programming.stream()
                .filter(task -> prog)
                .sorted()
                .forEach(head::add);
        Collecting.history.stream()
                .filter(task -> !prog)
                .sorted()
                .forEach(head::add);
        head.add("Total").add("Mark");

        List<String> table = new ArrayList<>();
        Map<Person, Double> avgTotSc = new Collecting().totalScores(list.stream());
        Map<Person, String> marks = new Collecting().defineMarks(list.stream());

        list.forEach(cr -> {
            StringBuilder sj = new StringBuilder();
            Person p = cr.getPerson();
            Map<String, Integer> tasks = cr.getTaskResults();
            String name = p.getLastName() + " " + p.getFirstName();
            sj.append(name).append(" ".repeat(maxLenOfName - name.length())).append(" | ");
            Collecting.programming.forEach((task) -> {
                String score = tasks.containsKey(task) ? Integer.toString(tasks.get(task)) : "";
                sj.append(tasks.containsKey(task) ? " ".repeat(task.length() - score.length()) + score + " | " : "");
            });
            Collecting.history.forEach((task) -> {
                String score = tasks.containsKey(task) ? Integer.toString(tasks.get(task)) : "";
                sj.append(tasks.containsKey(task) ? " ".repeat(task.length() - score.length()) + score + " | " : "");
            });
            String avg = BigDecimal.valueOf(avgTotSc.get(p))
                    .setScale(2, RoundingMode.HALF_UP)
                    .toString();
            sj.append(" ".repeat(5 - avg.length())).append(avg).append(" | ").append("   ").append(marks.get(p)).append(" |\n");
            table.add(sj.toString());
        });

        StringBuilder result = new StringBuilder();
        result.append(head);
        table.sort(String::compareTo);
        table.forEach(result::append);
        result.append(makeDown(list));

        return result.toString();
    }

    private void progUpdate(CourseResult cr) {
        cr.getTaskResults().keySet().forEach((task) -> prog |= task.contains("Lab"));
    }

    private void updateMaxLen(CourseResult cr) {
        Person p = cr.getPerson();
        maxLenOfName = max(maxLenOfName, p.getFirstName().length() + p.getLastName().length() + 1);
    }

    private String makeDown(List<CourseResult> results) {
        StringBuilder s = new StringBuilder();
        Map<String, Double> averages = new Collecting().averageScoresPerTask(results.stream());
        s.append("Average").append(" ".repeat(maxLenOfName - 7)).append(" | ");
        Collecting.programming.forEach(task -> {
            String score = averages.containsKey(task) ?
                    BigDecimal.valueOf(averages.get(task)).setScale(2, RoundingMode.HALF_UP).toString() : "";
            s.append(averages.containsKey(task) ? " ".repeat(task.length() - score.length()) + score + " | " : "");
        });
        Collecting.history.forEach(task -> {
            String score = averages.containsKey(task) ?
                    BigDecimal.valueOf(averages.get(task)).setScale(2, RoundingMode.HALF_UP).toString() : "";
            s.append(averages.containsKey(task) ? " ".repeat(task.length() - score.length()) + score + " | " : "");
        });
        double total = new Collecting().averageTotalScore(results.stream());
        String score = BigDecimal.valueOf(total)
                .setScale(2, RoundingMode.HALF_UP)
                .toString();
        s.append(" ".repeat(5 - score.length())).append(score).append(" | ");
        s.append("   ").append(total < 60 ? "F" :
                total < 68 ? "E" :
                        total < 75 ? "D" :
                                total < 83 ? "C" :
                                        total <= 90 ? "B" : "A").append(" |");
        return s.toString();
    }
}
