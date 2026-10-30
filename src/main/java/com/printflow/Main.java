
package com.printflow;

import java.util.Map;

import io.javalin.Javalin;

// Main entry point for the PrintFlow application
public class Main {

    public static void main(String[] args) {

        // Create and configure the Javalin application
        Javalin app = Javalin.create(config -> {

            // Serve frontend files from the public directory
            config.staticFiles.add("/public");
        });

        // Health-check endpoint to confirm that the API is running
        app.get("/api/health", ctx -> {

            // Return application information as JSON
            ctx.json(Map.of(
                "application", "PrintFlow",
                "status", "running"
            ));
        });

        // Start the application on port 7070
        app.start(7070);

        // Display the application URL in the terminal
        System.out.println(
            "PrintFlow running at http://localhost:7070"
        );
    }
}
