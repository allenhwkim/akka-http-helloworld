//package hello;

import akka.*;
import akka.actor.*;
import akka.http.javadsl.*;
import akka.http.javadsl.model.*;
import akka.http.javadsl.server.*;
import akka.stream.*;
import akka.stream.javadsl.*;
import java.util.concurrent.*;

public class HelloWorld extends AllDirectives {

    public static void main(String[] args) throws Exception {
        // boot up server using the route as defined below
        ActorSystem system = ActorSystem.create("routes");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        //In order to access all directives we need an instance where the routes are define.
        HelloWorld app = new HelloWorld();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 8080), materializer);

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read(); // let it run until user presses return

        binding
            .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
            .thenAccept(unbound -> system.terminate()); // and shutdown when done
    }

    private Route createRoute() {
        return route(
                path("", () ->
                        get(() ->
                                complete("<h1>Hello World From Akka-Http</h1>"))));
    }
}
