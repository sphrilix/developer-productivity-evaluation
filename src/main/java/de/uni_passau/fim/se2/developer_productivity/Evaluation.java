package de.uni_passau.fim.se2.developer_productivity;


import javax.lang.model.element.Element;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;
import static java.util.function.Predicate.not;

public class Evaluation {

    // #################################################################################################################
    // Example 1
    // #################################################################################################################

    public static int __(int number) {
        return number < 0 ? -number : number;
    }

    // #################################################################################################################
    // Example 2
    // #################################################################################################################

    public static <T> void ___(T[] array, int idx, int idy) {
        T swap = array[idx];
        array[idx] = array[idy];
        array[idy] = swap;
    }

    // #################################################################################################################
    // Example 3
    // #################################################################################################################

    public static double ____(double[] numbers) {
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        return sum / numbers.length;
    }

    // #################################################################################################################
    // Example 4
    // #################################################################################################################

    public static long _____(int n) {
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

    public boolean ______(String text) {
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

    public <T extends Comparable<T>> T[] _______(T[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j > 0 && less(array[j], array[j - 1]); j--) {
                ___(array, j, j - 1);
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

    public static int ________(String s) {
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

    public String _________(String message, int shift) {
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

    public static Optional<Node> __________(final Node node, final String name) {
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

    public static <T extends Comparable<T>> int ____________(
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
                ___(array, left, right);
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
            int pivot = ____________(array, left, right);
            doSort(array, left, pivot - 1);
            doSort(array, pivot, right);
        }
    }

    // #################################################################################################################
    // Example 11
    // #################################################################################################################

    public Connection ____________(String url, String user, String password) {
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

    public static List<String> _____________(String tokens) {
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

    public static String ______________(String urlToRead) throws Exception {
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

    public static void _______________(Session session, String toEmail, String subject, String body){
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

    public static String ________________(String url, String body) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}

