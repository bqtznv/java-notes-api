import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Server {
    static final List<String> NOTES = List.of("alpha", "beta", "gamma");

    public static HttpServer makeServer(int port) throws IOException {
        HttpServer server = HttpServer.create(
                new InetSocketAddress("0.0.0.0", port), 0
        );

        server.createContext("/healthz",
                exchange -> send(exchange, 200, "ok"));

        server.createContext("/notes",
                exchange -> send(exchange, 200, String.valueOf(NOTES.size())));

        server.createContext("/", exchange -> {
            if (exchange.getRequestURI().getPath().equals("/")) {
                send(exchange, 200, "notes service");
            } else {
                send(exchange, 404, "not found");
            }
        });

        return server;
    }

    private static void send(
            HttpExchange exchange,
            int status,
            String body
    ) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
                "Content-Type", "text/plain; charset=utf-8"
        );
        exchange.sendResponseHeaders(status, bytes.length);

        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }

    public static void main(String[] args) throws Exception {
        String portValue = System.getenv().getOrDefault("PORT", "8080");
        int port = Integer.parseInt(portValue);

        makeServer(port).start();
        System.out.println("Server started on port " + port);
    }
}
