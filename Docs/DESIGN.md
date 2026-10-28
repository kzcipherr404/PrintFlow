
# PrintFlow — Phase 1 Design

## 1. Class responsibilities

### PrintJob (abstract)
Fields:
- jobId: String
- customer: Customer
- pages: int
- status: JobStatus

Methods:
- getCost(): double (abstract)
- getters for private fields
- setStatus(JobStatus status)

### BlackWhiteJob extends PrintJob
Field:
- pricePerPage: double

Method:
- getCost(): double

### ColourJob extends PrintJob
Field:
- pricePerPage: double

Method:
- getCost(): double

### Customer
Fields:
- name: String
- contactNumber: String

Methods:
- getters and necessary setters

### PrintQueue
Field:
- jobs: List<PrintJob>

Methods:
- addJob(PrintJob job)
- getAllJobs()
- findJobById(String jobId)
- getPosition(String jobId)
- updateStatus(String jobId, JobStatus status)
- removeJob(String jobId)
- saveToFile()
- loadFromFile()

### JobNotFoundException
Purpose:
- Represents a requested job ID that does not exist.

## 2. Relationships

- BlackWhiteJob and ColourJob inherit from PrintJob.
- Each PrintJob references a Customer.
- PrintQueue manages a collection of PrintJob objects.
- PrintQueue throws JobNotFoundException when a job cannot be found.

## 3. Job statuses

- QUEUED
- PRINTING
- READY
- COLLECTED

## 4. Queue position

Positions are one-based. The first active job in the queue is position 1.
A collected job is not considered an active queue entry.

## 5. API contract

### GET /jobs
Returns all jobs in queue order.

### POST /jobs
Receives a customer's details, job type, page count, and other required
print-job information. Creates a job and returns the created job.

### GET /jobs/{id}/position
Returns the job's status and queue position.

### PUT /jobs/{id}/status
Receives the new status and updates the job.

### DELETE /jobs/{id}
Removes a job, for example when an order is cancelled.

## 6. Error responses

- 400 Bad Request: invalid input or status.
- 404 Not Found: requested job ID does not exist.
- 500 Internal Server Error: unexpected server-side failure.

## 7. Data persistence

Jobs will be stored in a JSON file under data/.
The application will load saved jobs on startup and save changes to disk.
