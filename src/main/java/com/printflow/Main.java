
package com.printflow;

import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        });

        app.get("/api/health", ctx -> {
            ctx.json(java.util.Map.of(
                "application", "PrintFlow",
                "status", "running"
            ));
        });

        app.start(7070);

        System.out.println("PrintFlow running at http://localhost:7070");
    }
}
