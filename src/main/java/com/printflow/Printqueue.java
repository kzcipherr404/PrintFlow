package com.printflow;

import java.io.*;
import java.util.*;

public class Printqueue {

    private final List<PrintJob> jobs = new ArrayList<>();
    private final String filePath;

    public Printqueue(String filePath) {
        if (filePath == null || filePath.isBlank())
            throw new IllegalArgumentException("File path cannot be blank");

        this.filePath = filePath;}


    public void addJob(PrintJob job) {
        if (job == null)
            throw new IllegalArgumentException("Job cannot be null");

        if (findJobOrNull(job.getJobId()) != null)
            throw new IllegalArgumentException(
                "Job ID already exists: " + job.getJobId()
            );

        jobs.add(job);
        save();
    }

    public List<PrintJob> getAllJobs() {
        return Collections.unmodifiableList(jobs);
    }

    public PrintJob findById(String jobId)
            throws JobNotFoundException {

        PrintJob job = findJobOrNull(jobId);

        if (job == null)
            throw new JobNotFoundException(jobId);

        return job;
    }

    private PrintJob findJobOrNull(String jobId) {
        for (PrintJob job : jobs) {
            if (job.getJobId().equals(jobId))
                return job;
        }

        return null;
    }

    public int getPosition(String jobId)
            throws JobNotFoundException {

        for (int i = 0; i < jobs.size(); i++) {
            if (jobs.get(i).getJobId().equals(jobId))
                return i + 1;
        }

        throw new JobNotFoundException(jobId);
    }

    public void updateStatus(String jobId, JobStatus newStatus)
            throws JobNotFoundException {

        if (newStatus == null)
            throw new IllegalArgumentException(
                "New status cannot be null"
            );

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

    public void save() {
        try (PrintWriter writer =
                new PrintWriter(new FileWriter(filePath))) {

            for (PrintJob job : jobs) {
                writer.println(serialize(job));
            }

        } catch (IOException e) {
            throw new UncheckedIOException(
                "Failed to save jobs to " + filePath, e
            );
        }
    }

    public void load() {
        jobs.clear();

        File file = new File(filePath);

        if (!file.exists())
            return;

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    jobs.add(deserialize(line));
                }
            }

        } catch (IOException e) {
            throw new UncheckedIOException(
                "Failed to load jobs from " + filePath, e
            );
        }
    }

    private String serialize(PrintJob job) {
        String type = (job instanceof BlackWhiteJob)
                ? "BW" : "COLOUR";

        Customer c = job.getCustomer();

        return String.join("|",
                job.getJobId(),
                c.getName(),
                c.getContact(),
                String.valueOf(job.getPages()),
                type,
                job.getStatus().name()
        );
    }

    private PrintJob deserialize(String line) {
        String[] p = line.split("\\|");

        if (p.length != 6)
            throw new IllegalStateException(
                "Corrupt job record: " + line
            );

        String id = p[0];
        String name = p[1];
        String contact = p[2];
        int pages = Integer.parseInt(p[3]);
        String type = p[4];

        JobStatus status = JobStatus.valueOf(p[5]);

        Customer customer = new Customer(name, contact);

        PrintJob job;

        if (type.equals("BW")) {
            job = new BlackWhiteJob(id, customer, pages);
        } else if (type.equals("COLOUR")) {
            job = new ColourJob(id, customer, pages);
        } else {
            throw new IllegalStateException(
                "Unknown job type: " + type
            );
        }

        job.setStatus(status);

        return job;
    }
}
