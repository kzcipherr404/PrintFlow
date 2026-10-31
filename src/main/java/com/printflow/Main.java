
package com.printflow;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.javalin.Javalin;

// Main entry point for the PrintFlow application
public class Main {

    // Shared queue used by all API endpoints
    private static PrintQueue queue;

    public static void main(String[] args) {

        // Load saved jobs when the application starts
        queue = new PrintQueue("data/jobs.txt");

        // Create and configure the Javalin application
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        });

        // Health-check endpoint
        app.get("/api/health", ctx -> {
            ctx.json(Map.of(
                "application", "PrintFlow",
                "status", "running"
            ));
        });

        // 1. GET: Retrieve all printing jobs
        app.get("/api/jobs", ctx -> {

            List<Map<String, Object>> response = new ArrayList<>();

            for (PrintJob job : queue.getAllJobs()) {
                response.add(toJobResponse(job));
            }

            ctx.json(response);
        });

        // 2. POST: Create a new printing job
        app.post("/api/jobs", ctx -> {

            try {
                Map<String, Object> request =
                    ctx.bodyAsClass(Map.class);

                String type = requiredString(request, "type");

                Object customerObject = request.get("customer");

                if (!(customerObject instanceof Map<?, ?> customerData)) {
                    throw new IllegalArgumentException(
                        "Customer details are required."
                    );
                }

                String name = requiredString(customerData, "name");
                String contact = requiredString(
                    customerData, "contactNumber"
                );

                Object pagesObject = request.get("pages");

                if (!(pagesObject instanceof Number)) {
                    throw new IllegalArgumentException(
                        "Pages must be a number."
                    );
                }

                int pages = ((Number) pagesObject).intValue();

                if (((Number) pagesObject).doubleValue() != pages) {
                    throw new IllegalArgumentException(
                        "Pages must be a whole number."
                    );
                }

                // Generate a unique job ID
                String jobId = "PF-" +
                    UUID.randomUUID().toString().substring(0, 8);

                Customer customer = new Customer(name, contact);

                PrintJob job;

                // Create the correct job type
                if (type.equalsIgnoreCase("BW")) {
                    job = new BlackWhiteJob(jobId, customer, pages);

                } else if (type.equalsIgnoreCase("COLOUR")) {
                    job = new ColourJob(jobId, customer, pages);

                } else {
                    throw new IllegalArgumentException(
                        "Type must be BW or COLOUR."
                    );
                }

                // Add job to queue and save it
                queue.addJob(job);

                ctx.status(201);
                ctx.json(toJobResponse(job));

            } catch (IllegalArgumentException e) {
                ctx.status(400);
                ctx.json(Map.of("error", e.getMessage()));
            }
        });

        // 3. GET: Retrieve a job's queue position
        app.get("/api/jobs/{id}/position", ctx -> {

            String jobId = ctx.pathParam("id");

            try {
                int position = queue.getPosition(jobId);

                ctx.json(Map.of(
                    "jobId", jobId,
                    "position", position
                ));

            } catch (JobNotFoundException e) {
                ctx.status(404);
                ctx.json(Map.of("error", e.getMessage()));
            }
        });

        // 4. PUT: Update a job's status
        app.put("/api/jobs/{id}/status", ctx -> {

            String jobId = ctx.pathParam("id");

            try {
                Map<String, Object> request =
                    ctx.bodyAsClass(Map.class);

                String status = requiredString(request, "status");

                queue.updateStatus(jobId, status);

                ctx.json(Map.of(
                    "message", "Job status updated successfully.",
                    "jobId", jobId,
                    "status", status.toUpperCase()
                ));

            } catch (JobNotFoundException e) {
                ctx.status(404);
                ctx.json(Map.of("error", e.getMessage()));

            } catch (IllegalArgumentException e) {
                ctx.status(400);
                ctx.json(Map.of("error", e.getMessage()));
            }
        });

        // 5. DELETE: Remove a printing job
        app.delete("/api/jobs/{id}", ctx -> {

            String jobId = ctx.pathParam("id");

            try {
                queue.removeJob(jobId);

                ctx.json(Map.of(
                    "message", "Job removed successfully.",
                    "jobId", jobId
                ));

            } catch (JobNotFoundException e) {
                ctx.status(404);
                ctx.json(Map.of("error", e.getMessage()));
            }
        });

        // Start the application
        app.start(7070);

        System.out.println(
            "PrintFlow running at http://localhost:7070"
        );
    }

    // Converts a PrintJob object into a JSON-friendly response
    private static Map<String, Object> toJobResponse(PrintJob job) {

        Map<String, Object> response = new LinkedHashMap<>();

        String type = (job instanceof BlackWhiteJob)
            ? "BW" : "COLOUR";

        response.put("jobId", job.getJobId());
        response.put("type", type);
        response.put("customer", Map.of(
            "name", job.getCustomer().getName(),
            "contactNumber", job.getCustomer().getContactNumber()
        ));
        response.put("pages", job.getPages());
        response.put("status", job.getStatus());
        response.put("cost", job.getCost());

        return response;
    }

    // Reads a required non-empty string from a request
    private static String requiredString(
            Map<?, ?> request, String key) {

        Object value = request.get(key);

        if (!(value instanceof String) ||
                ((String) value).trim().isEmpty()) {

            throw new IllegalArgumentException(
                key + " is required."
            );
        }

        return ((String) value).trim();
    }
}
