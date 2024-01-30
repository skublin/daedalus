import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new ServerHandler());
        server.createContext("/command", new CommandHandler());

        server.start();

        System.out.println("Server is running on port 8080.");
    }

    static class ServerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();

            if (path.equals("/"))
                path = "/index.html";

            byte[] response = Files.readAllBytes(Paths.get("resources" + path));

            exchange.sendResponseHeaders(200, response.length);

            exchange.getResponseBody().write(response);

            exchange.getResponseBody().close();
        }
    }

    static class CommandHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String commandJson = br.readLine();

                Command command = new Gson().fromJson(commandJson, Command.class);

                CommandProcessor cp = new CommandProcessor();
                String response = cp.processCommand(command.getCommand());

                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            } else {
                String response = "Wrong command type.";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        static class Command {
            private String command;

            public String getCommand() {
                return command;
            }

            public void setCommand(String command) {
                this.command = command;
            }
        }
    }
}
