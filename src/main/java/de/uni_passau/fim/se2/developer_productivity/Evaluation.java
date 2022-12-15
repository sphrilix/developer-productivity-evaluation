package de.uni_passau.fim.se2.developer_productivity;


import de.uni_passau.fim.se2.developer_productivity.utils.SystemPropertyUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static java.util.function.Predicate.not;

public class Evaluation {

    // #################################################################################################################
    // Example 1
    // #################################################################################################################

    public static int abs(int number) {
        return number < 0 ? -number : number;
    }

    // #################################################################################################################
    // Example 2
    // #################################################################################################################

    public static <T> void swap(T[] array, int idx, int idy) {
        T swap = array[idx];
        array[idx] = array[idy];
        array[idy] = swap;
    }

    // #################################################################################################################
    // Example 3
    // #################################################################################################################

    public static double getExpectedValue(double[] numbers) {
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        return sum / numbers.length;
    }

    // #################################################################################################################
    // Example 4
    // #################################################################################################################

    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("number is negative");
        }
        long factorial = 1;
        for (int i = 1; i <= n; factorial *= i, ++i);
        return factorial;
    }

    // #################################################################################################################
    // Example 5
    // #################################################################################################################

    public boolean isPalindrome(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        int length = clean.length();
        int forward = 0;
        int backward = length - 1;
        while (backward > forward) {
            char forwardChar = clean.charAt(forward++);
            char backwardChar = clean.charAt(backward--);
            if (forwardChar != backwardChar)
                return false;
        }
        return true;
    }

    // #################################################################################################################
    // Example 6
    // #################################################################################################################

    public <T extends Comparable<T>> T[] sortAscending(T[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j > 0 && less(array[j], array[j - 1]); j--) {
                swap(array, j, j - 1);
            }
        }
        return array;
    }

    static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    // #################################################################################################################
    // Example 7
    // #################################################################################################################

    public static int toInt(String s) {
        if (s == null || s.length() == 0) {
            throw new NumberFormatException("null");
        }
        boolean isNegative = s.charAt(0) == '-';
        boolean isPositive = s.charAt(0) == '+';
        int number = 0;
        for (
                int i = isNegative ? 1 : isPositive ? 1 : 0, length = s.length();
                i < length;
                ++i
        ) {
            if (!Character.isDigit(s.charAt(i))) {
                throw new NumberFormatException("s=" + s);
            }
            number = number * 10 + s.charAt(i) - '0';
        }
        return isNegative ? -number : number;
    }

    // #################################################################################################################
    // Example 8
    // #################################################################################################################

    public String encode(String message, int shift) {
        StringBuilder encoded = new StringBuilder();

        shift %= 26;

        final int length = message.length();
        for (int i = 0; i < length; i++) {
            char current = message.charAt(i); // Java law : char + int = char

            if (isCapitalLatinLetter(current)) {
                current += shift;
                encoded.append((char) (current > 'Z' ? current - 26 : current)); // 26 = number of latin letters
            } else if (isSmallLatinLetter(current)) {
                current += shift;
                encoded.append((char) (current > 'z' ? current - 26 : current)); // 26 = number of latin letters
            } else {
                encoded.append(current);
            }
        }
        return encoded.toString();
    }

    private static boolean isCapitalLatinLetter(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private static boolean isSmallLatinLetter(char c) {
        return c >= 'a' && c <= 'z';
    }

    // #################################################################################################################
    // Example 9
    // #################################################################################################################

    public static Optional<Node> findFirst(final Node node, final String name) {
        if (node.getName().equals(name)) {
            return Optional.of(node);
        }

        Queue<Node> queue = new ArrayDeque<>(node.getSubNodes());

        while (!queue.isEmpty()) {
            final Node current = queue.poll();

            if (current.getName().equals(name)) {
                return Optional.of(current);
            }

            queue.addAll(current.getSubNodes());
        }

        return Optional.empty();
    }

    static class Node {

        private final String name;
        private final List<Node> subNodes;

        public Node(final String name) {
            this.name = name;
            this.subNodes = new ArrayList<>();
        }

        public Node(final String name, final List<Node> subNodes) {
            this.name = name;
            this.subNodes = subNodes;
        }

        public String getName() {
            return name;
        }

        public List<Node> getSubNodes() {
            return subNodes;
        }
    }


    // #################################################################################################################
    // Example 10
    // #################################################################################################################

    public static <T extends Comparable<T>> int selectPivotIndex(
            T[] array,
            int left,
            int right
    ) {
        int mid = (left + right) >>> 1;
        T pivot = array[mid];

        while (left <= right) {
            while (less(array[left], pivot)) {
                ++left;
            }
            while (less(pivot, array[right])) {
                --right;
            }
            if (left <= right) {
                swap(array, left, right);
                ++left;
                --right;
            }
        }
        return left;
    }

    public <T extends Comparable<T>> T[] quickSort(T[] array) {
        doSort(array, 0, array.length - 1);
        return array;
    }

    private static <T extends Comparable<T>> void doSort(
            T[] array,
            int left,
            int right
    ) {
        if (left < right) {
            int pivot = selectPivotIndex(array, left, right);
            doSort(array, left, pivot - 1);
            doSort(array, pivot, right);
        }
    }

    // #################################################################################################################
    // Example 11
    // #################################################################################################################

    public Connection createConnection(String url, String user, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    // #################################################################################################################
    // Example 12
    // #################################################################################################################

    public static List<String> tokenize(String tokens) {
        if (tokens == null) {
            return Collections.emptyList();
        }

        return Stream.of(tokens.split("(?<=[a-z])(?=[A-Z])|_|-|\\d|(?<=[A-Z])(?=[A-Z][a-z])|\\s+"))
                .filter(not(String::isEmpty))
                .map(Evaluation::removeUnusableCharacters)
                .filter(not(String::isEmpty))
                .map(String::toLowerCase)
                .toList();
    }

    private static String removeUnusableCharacters(String content) {
        return content.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isLetter)
                .map(Objects::toString)
                .filter(not(String::isEmpty))
                .reduce("", String::concat);
    }

    // #################################################################################################################
    // Example 13
    // #################################################################################################################

    public static String export(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }

    // #################################################################################################################
    // Example 14
    // #################################################################################################################

    public static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // #################################################################################################################
    // Example 15
    // #################################################################################################################

    public static URL[] getJarFileURLs() {
        List<URL> urls = new ArrayList<>();
        String home = SystemPropertyUtils.resolvePlaceholders("${spring.home:${SPRING_HOME:.}}");
        File extDirectory = new File(new File(home, "lib"), "ext");
        if (extDirectory.isDirectory()) {
            for (File file : extDirectory.listFiles()) {
                if (file.getName().endsWith(".jar")) {
                    try {
                        urls.add(file.toURI().toURL());
                    }
                    catch (MalformedURLException ex) {
                        throw new IllegalStateException(ex);
                    }
                }
            }
        }
        return urls.toArray(new URL[0]);
    }

    // #################################################################################################################
    // Example 16
    // #################################################################################################################

    public class DateTimeUtils {

        private static final String[] patterns =
                new String[] {"yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm z", "yyyy-MM-dd"};

        public static Duration convertDuration(String text) {
            Matcher m =
                    Pattern.compile(
                                    "\\s*(?:(\\d+)\\s*(?:days?|d))?"
                                            + "\\s*(?:(\\d+)\\s*(?:hours?|hrs?|h))?"
                                            + "\\s*(?:(\\d+)\\s*(?:minutes?|mins?|m))?"
                                            + "\\s*(?:(\\d+)\\s*(?:seconds?|secs?|s))?"
                                            + "\\s*",
                                    Pattern.CASE_INSENSITIVE)
                            .matcher(text);
            if (!m.matches()) throw new IllegalArgumentException("Not valid duration: " + text);

            int days = (m.start(1) == -1 ? 0 : Integer.parseInt(m.group(1)));
            int hours = (m.start(2) == -1 ? 0 : Integer.parseInt(m.group(2)));
            int mins = (m.start(3) == -1 ? 0 : Integer.parseInt(m.group(3)));
            int secs = (m.start(4) == -1 ? 0 : Integer.parseInt(m.group(4)));
            return Duration.ofSeconds((days * 86400) + (hours * 60L + mins) * 60L + secs);
        }
    }

    // #################################################################################################################
    // Example 17
    // #################################################################################################################

    public static String sendRequest(String url) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString("Hi there!"))
                .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // #################################################################################################################
    // Example 18
    // #################################################################################################################
    public class ZipReader {

        public static String getContentOfFile(String path) throws IOException {

            try(ZipFile file = new ZipFile(path)) {
                final Enumeration<? extends ZipEntry> entries = file.entries();
                while (entries.hasMoreElements()) {
                    final ZipEntry entry = entries.nextElement();
                    if (entry.getName().equals("project.json")) {
                        InputStream is = file.getInputStream(entry);
                        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        br.close();
                        return sb.toString();
                    }
                }
            }
            return null;
        }

        /**
         * A method returning the filename for a given filepath
         *
         * @param path the file path
         * @return the filename
         * @throws IOException
         */
        public static String getName(String path) throws IOException {
            final ZipFile file = new ZipFile(path);
            String name = file.getName();
            file.close();
            return name;
        }
    }

    // #################################################################################################################
    // Example 19
    // #################################################################################################################

    public static int indexOfDifference(String[] strs) {
        if (strs == null || strs.length <= 1) {
            return -1;
        }
        boolean anyStringNull = false;
        boolean allStringsNull = true;
        int arrayLen = strs.length;
        int shortestStrLen = Integer.MAX_VALUE;
        int longestStrLen = 0;

        // find the min and max string lengths; this avoids checking to make
        // sure we are not exceeding the length of the string each time through
        // the bottom loop.
        for (final String str : strs) {
            if (str == null) {
                anyStringNull = true;
                shortestStrLen = 0;
            } else {
                allStringsNull = false;
                shortestStrLen = Math.min(str.length(), shortestStrLen);
                longestStrLen = Math.max(str.length(), longestStrLen);
            }
        }

        // handle lists containing all nulls or all empty strings
        if (allStringsNull || (longestStrLen == 0 && !anyStringNull)) {
            return -1;
        }

        // handle lists containing some nulls or some empty strings
        if (shortestStrLen == 0) {
            return 0;
        }

        // find the position with the first difference across all strings
        int firstDiff = -1;
        for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
            char comparisonChar = strs[0].charAt(stringPos);
            for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
                if (strs[arrayPos].charAt(stringPos) != comparisonChar) {
                    firstDiff = stringPos;
                    break;
                }
            }
            if (firstDiff != -1) {
                break;
            }
        }

        if (firstDiff == -1 && shortestStrLen != longestStrLen) {
            // we compared all of the characters up to the length of the
            // shortest string and didn't find a match, but the string lengths
            // vary, so return the length of the shortest string.
            return shortestStrLen;
        }
        return firstDiff;
    }

    // #################################################################################################################
    // Example 20
    // #################################################################################################################

    public static <T> T getRandom(List<T> list, List<Integer> probabilities) {

        if (probabilities == null || probabilities.size() <= 1 || probabilities.size() != list.size()) {
            int index = ThreadLocalRandom.current().nextInt(0, list.size());
            return list.get(index);
        }


        int totalProbabilityMass = probabilities.stream().reduce(Integer::sum).get();
        int roll = ThreadLocalRandom.current().nextInt(1, totalProbabilityMass + 1);

        int currentTotalChance = 0;
        for (int i = 0; i < list.size(); i++) {
            currentTotalChance += probabilities.get(i);

            if (roll <= currentTotalChance) {
                return list.get(i);
            }
        }


        return list.get(0);
    }
}



