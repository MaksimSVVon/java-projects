package corejava.tasks.words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        if (words == null || words.length == 0 || sample == null) { return 0; }
        int count = 0;
        for (String w : words) { if (w.trim().equalsIgnoreCase(sample.trim())) { ++count; } }
        return count;
    }

    public static String[] splitWords(String text) {
        if (text == null || text.matches("[,.;: ?!]*")) { return null; }
        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile("([^,.;: ?!])+(?=[,.;: ?!])*");
        Matcher m = p.matcher(text);
        while (m.find()) {
            list.add(m.group());
        }
        if (list.size() == 0) { return null; }
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); ++i) { result[i] = list.get(i); }
        return result;
    }

    public static String convertPath(String path, boolean toWin) {
        if (path == null || path.isBlank()) { return null; }
        if (path.matches("(~$|~/|/)?([^~\\\\/:]+/?)*")) {
            if (toWin) {
                path = path.replaceAll("~", "C:\\\\User")
                            .replaceAll("^/", "C:\\\\")
                            .replaceAll("/", "\\\\");
            }
            return path;
        }
        if (path.matches("(C:|C:\\\\|\\\\)?([^~\\\\/:]+\\\\?)*")) {
            if (!toWin) {
                path = path.replaceAll("C:\\\\User", "~")
                            .replaceAll("C:\\\\", "/")
                            .replaceAll("\\\\", "/");
            }
            return path;
        }
        return null;
    }

    public static String joinWords(String[] words) {
        if (words == null || words.length == 0) { return null; }
        int count = 0;
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (String w : words) {
            if (!w.isBlank()) {
                sj.add(w);
                ++count;
            }
        }
        if (count == 0) { return null; }
        return sj.toString();
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS", };
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

         System.out.println("Test 2: splitWords");
         String text = "";
         String[] splitResult = splitWords(text);
         System.out.println("Result : " + Arrays.toString(splitResult));
         String[] expectedSplit = new String[]{"first", "second", "third"};
         System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "~";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

         System.out.println("Test 4: joinWords");
         String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
         String joinResult = joinWords(toJoin);
         System.out.println("Result: " + joinResult);
         String expectedJoin = "[go, with, the, FLOW]";
         System.out.println("Must be: " + expectedJoin);
    }
}