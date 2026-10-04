import com.sun.net.httpserver.HttpServer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Tests {
    static int total = 0;
    static int passed = 0;

    static void check(String name, boolean condition) {
        total++;

        if (condition) {
            passed++;
            System.out.println("PASS: " + name);
        } else {
            System.out.println("FAIL: " + name);
        }
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = Server.makeServer(0);
        server.start();

        try {
            int port = server.getAddress().getPort();
            String baseUrl = "http://127.0.0.1:" + port;
            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> root = client.send(
                    HttpRequest.newBuilder(
                            URI.create(baseUrl + "/")
                    ).build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            check("root answers", root.statusCode() == 200);

            HttpResponse<String> health = client.send(
                    HttpRequest.newBuilder(
                            URI.create(baseUrl + "/healthz")
                    ).build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            check(
                    "healthz is okay",
                    health.statusCode() == 200 && !health.body().isBlank()
            );

            HttpResponse<String> notes = client.send(
                    HttpRequest.newBuilder(
                            URI.create(baseUrl + "/notes")
                    ).build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            check("notes count is three", notes.body().equals("3"));
        } finally {
            server.stop(0);
        }

        System.out.println("TESTS: " + passed + "/" + total);
        System.exit(passed == total ? 0 : 1);
    }
}
