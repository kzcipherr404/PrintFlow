
package com.printflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintQueue {

    private final List<PrintJob> jobs = new ArrayList<>();

    private final String filePath;

    private final ObjectMapper mapper = new ObjectMapper();

    public PrintQueue(String filePath) {

        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "File path cannot be blank."
            );
        }

        this.filePath = filePath;

        load();
    }

    public void addJob(PrintJob job) {

        if (job == null) {
            throw new IllegalArgumentException(
                "Job cannot be null."
            );
        }

        if (findJobOrNull(job.getJobId()) != null) {
            throw new IllegalArgumentException(
                "Job ID already exists: " + job.getJobId()
            );
        }

        jobs.add(job);
        save();
    }

    public List<PrintJob> getAllJobs() {

        return Collections.unmodifiableList(
            new ArrayList<>(jobs)
        );
    }

    public PrintJob findById(String jobId)
            throws JobNotFoundException {

        PrintJob job = findJobOrNull(jobId);

        if (job == null) {
            throw new JobNotFoundException(jobId);
        }

        return job;
    }

    private PrintJob findJobOrNull(String jobId) {

        for (PrintJob job : jobs) {

            if (job.getJobId().equals(jobId)) {
                return job;
            }
        }

        return null;
    }

    public int getPosition(String jobId)
            throws JobNotFoundException {

        for (int i = 0; i < jobs.size(); i++) {

            if (jobs.get(i).getJobId().equals(jobId)) {
                return i + 1;
            }
        }

        throw new JobNotFoundException(jobId);
    }

    public void updateStatus(String jobId, String newStatus)
            throws JobNotFoundException {

        if (newStatus == null) {
            throw new IllegalArgumentException(
                "New status cannot be null."
            );
        }

        PrintJob job = findById(jobId);

        job.setStatus(newStatus);

        save();
    }

    public void removeJob(String jobId)
            throws JobNotFoundException {

        PrintJob job = findById(jobId);

        jobs.remove(job);

        save();
    }

    public int size() {
        return jobs.size();
    }

    public boolean isEmpty() {
        return jobs.isEmpty();
    }

    // Converts all jobs into JSON and saves them.
    public void save() {

        try {

            File file = new File(filePath);

            File parent = file.getParentFile();

            if (parent != null) {
                parent.mkdirs();
            }

            ArrayNode jsonJobs = mapper.createArrayNode();

            for (PrintJob job : jobs) {

                ObjectNode jobNode = mapper.createObjectNode();

                jobNode.put("jobId", job.getJobId());

                if (job instanceof BlackWhiteJob) {
                    jobNode.put("type", "BW");
                } else if (job instanceof ColourJob) {
                    jobNode.put("type", "COLOUR");
                } else {
                    throw new IllegalStateException(
                        "Unknown print job type."
                    );
                }

                ObjectNode customerNode =
                    mapper.createObjectNode();

                customerNode.put(
                    "name",
                    job.getCustomer().getName()
                );

                customerNode.put(
                    "contactNumber",
                    job.getCustomer().getContactNumber()
                );

                jobNode.set("customer", customerNode);

                jobNode.put("pages", job.getPages());
                jobNode.put("status", job.getStatus());

                jsonJobs.add(jobNode);
            }

            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(file, jsonJobs);

        } catch (IOException e) {

            throw new UncheckedIOException(
                "Failed to save jobs to " + filePath, e
            );
        }
    }

    // Loads previously saved JSON jobs.
    public void load() {

        jobs.clear();

        File file = new File(filePath);

        if (!file.exists()) {
            return;
        }

        try {

            JsonNode root = mapper.readTree(file);

            if (root == null || !root.isArray()) {
                throw new IllegalStateException(
                    "Invalid jobs JSON file."
                );
            }

            for (JsonNode node : root) {

                String id = node.get("jobId").asText();

                String type = node.get("type").asText();

                JsonNode customerNode = node.get("customer");

                String name = customerNode
                    .get("name").asText();

                String contact = customerNode
                    .get("contactNumber").asText();

                int pages = node.get("pages").asInt();

                String status = node.get("status").asText();

                Customer customer =
                    new Customer(name, contact);

                PrintJob job;

                if (type.equals("BW")) {

                    job = new BlackWhiteJob(
                        id, customer, pages
                    );

                } else if (type.equals("COLOUR")) {

                    job = new ColourJob(
                        id, customer, pages
                    );

                } else {

                    throw new IllegalStateException(
                        "Unknown job type: " + type
                    );
                }

                job.setStatus(status);

                jobs.add(job);
            }

        } catch (IOException e) {

            throw new UncheckedIOException(
                "Failed to load jobs from " + filePath, e
            );
        }
    }
}
