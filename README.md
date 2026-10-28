# PrintFlow — Printing Shop Queue Management System

## Overview
PrintFlow is a printing shop queue management system designed to help printing shop operators organize print jobs and allow customers to track their jobs from submission to collection.

## Users
- **Students/Customers:** Submit print jobs and check job status and queue position.
- **Printing Shop Operators:** Manage print jobs, update statuses, and handle cancellations.

## Technology Stack
- Java 21
- Maven
- Javalin
- HTML, CSS and JavaScript
- JSON file storage

## Main Classes
- `PrintJob` — Abstract parent class representing a print job.
- `BlackWhiteJob` — Calculates black-and-white printing costs.
- `ColourJob` — Calculates colour printing costs.
- `Customer` — Stores customer details.
- `PrintQueue` — Manages print jobs and queue positions.
- `JobNotFoundException` — Handles requests for nonexistent jobs.

## Project Status
Phase 1: Project setup and design.

## Running the Project
Prerequisites: JDK 21 and Maven.

From the project root, run:

```bash
mvn clean compile
mvn exec:java
```

When the application starts, visit http://localhost:7070/api/health to check its status.

## Project Documentation
See the `PrintFlow_Project_Management/` directory for project phases, task assignments, and the GitHub workflow.

**Note:** The run commands will work once the Maven project and starter application have been created and configured.
