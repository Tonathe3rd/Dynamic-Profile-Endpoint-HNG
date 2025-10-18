import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ProfileServer {

    // Simple Java logger
    private static final Logger logger = Logger.getLogger(ProfileServer.class.getName());

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/me", new ProfileHandler());
        server.createContext("/", exchange -> {
            String response = "Hellooo! Try adding '/me' to your url so as to generate your profile and a cat fact.";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });
        server.setExecutor(null);

        logger.info("🚀 Server started on http://localhost:" + port + "/me");
        server.start();
    }

    static class ProfileHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestMethod = exchange.getRequestMethod();
            String requestURI = exchange.getRequestURI().toString();
            logger.info("📩 Incoming request: " + requestMethod + " " + requestURI);

            if (!"GET".equalsIgnoreCase(requestMethod)) {
                String response = "{\"error\": \"Method not allowed\"}";
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(405, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                logger.warning("❌ Invalid method: " + requestMethod);
                return;
            }

            String catFact = fetchCatFact();
            String timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now());

            String jsonResponse = String.format(
                    """
                            {
                              "status": "success",
                              "user": {
                                "email": "%s",
                                "name": "%s",
                                "stack": "%s"
                              },
                              "timestamp": "%s",
                              "fact": "%s"
                            }""",
                    "get2adeshola@gmail.com",
                    "Adeshola Adetona",
                    "Java",
                    timestamp,
                    escapeJson(catFact)
            );

            byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, responseBytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }

            logger.info("✅ Response sent successfully at " + timestamp);
        }

        private String fetchCatFact() {
            String apiUrl = "https://catfact.ninja/fact";
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int status = connection.getResponseCode();
                if (status == 200) {
                    try (Scanner scanner = new Scanner(connection.getInputStream())) {
                        StringBuilder response = new StringBuilder();
                        while (scanner.hasNextLine()) {
                            response.append(scanner.nextLine());
                        }

                        // Extract the "fact" value manually
                        String json = response.toString();
                        int start = json.indexOf("\"fact\":\"") + 8;
                        int end = json.indexOf("\",", start);
                        if (start > 7 && end > start) {
                            String fact = json.substring(start, end);
                            logger.info("🐱 Cat fact fetched successfully.");
                            return fact;
                        }
                    }
                } else {
                    logger.warning("⚠️ Cat Facts API responded with status: " + status);
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "❌ Failed to fetch cat fact", e);
            }
            return "Could not fetch a cat fact at the moment. Try again later.";
        }

        private String escapeJson(String text) {
            return text.replace("\"", "\\\"").replace("\n", " ").replace("\r", "");
        }
    }
}
