function showMessage(elementId, text, type) {
  var box = document.getElementById(elementId);
  if (!box) { return; }
  box.textContent = text;
  box.className = text ? "message " + type : "message";
}

var jobForm = document.getElementById("job-form");

if (jobForm) {
  jobForm.addEventListener("submit", submitJob);
}

async function submitJob(event) {
  event.preventDefault();

  var submitButton = document.getElementById("submit-btn");
  var resultBox = document.getElementById("job-result");

  showMessage("submit-message", "", "");
  resultBox.hidden = true;

  var name = document.getElementById("customerName").value.trim();
  var contact = document.getElementById("contactNumber").value.trim();
  var pagesText = document.getElementById("pages").value.trim();
  var type = document.getElementById("printType").value;
  var pages = Number(pagesText);

  if (name === "") {
    showMessage("submit-message", "Please enter the customer name.", "error");
    return;
  }
  if (contact === "") {
    showMessage("submit-message", "Please enter a contact number.", "error");
    return;
  }
  if (pagesText === "" || !Number.isInteger(pages) || pages < 1) {
    showMessage("submit-message", "Pages must be a whole number of 1 or more.", "error");
    return;
  }

  var job = {
    type: type,
    customer: { name: name, contactNumber: contact },
    pages: pages
  };

  submitButton.disabled = true;

  try {
    var response = await fetch("/api/jobs", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(job)
    });

    var data = null;
    try {
      data = await response.json();
    } catch (parseError) {
      data = null;
    }

    if (response.ok && data && data.jobId) {
      document.getElementById("job-id-value").textContent = data.jobId;
      document.getElementById("job-details").textContent =
        data.pages + " pages, total cost " + Number(data.cost).toFixed(2);
      resultBox.hidden = false;
      showMessage("submit-message", "Your job was submitted successfully.", "success");
      jobForm.reset();
    } else {
      var reason = data && data.error ? data.error : "The server could not accept the job.";
      showMessage("submit-message", reason, "error");
    }
  } catch (networkError) {
    showMessage("submit-message", "Could not reach the server. Check that PrintFlow is running and try again.", "error");
  } finally {
    submitButton.disabled = false;
  }
}

// =====================================
// P4-C: OPERATOR CONTROLS
// =====================================

// Update the status of an existing print job
async function updateJobStatus() {
  var jobId = document.getElementById("operator-job-id").value.trim();
  var newStatus = document.getElementById("operator-status").value;
  var button = document.getElementById("update-status-btn");

  showMessage("operator-message", "", "");

  if (jobId === "") {
    showMessage(
      "operator-message",
      "Please enter a job ID.",
      "error"
    );
    return;
  }

  button.disabled = true;

  try {
    var response = await fetch(
      "/api/jobs/" + encodeURIComponent(jobId) + "/status",
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          status: newStatus
        })
      }
    );

    var data = null;

    try {
      data = await response.json();
    } catch (error) {
      data = null;
    }

    if (response.ok) {
      showMessage(
        "operator-message",
        data && data.message
          ? data.message
          : "Job status updated successfully.",
        "success"
      );
    } else {
      showMessage(
        "operator-message",
        data && data.error
          ? data.error
          : "Could not update the job status.",
        "error"
      );
    }

  } catch (error) {
    showMessage(
      "operator-message",
      "Could not connect to PrintFlow. Please try again.",
      "error"
    );
  } finally {
    button.disabled = false;
  }
}


// Cancel an existing print job
async function cancelPrintJob() {
  var jobId = document.getElementById("operator-job-id").value.trim();
  var button = document.getElementById("cancel-job-btn");

  showMessage("operator-message", "", "");

  if (jobId === "") {
    showMessage(
      "operator-message",
      "Please enter a job ID.",
      "error"
    );
    return;
  }

  var confirmed = confirm(
    "Are you sure you want to cancel job " + jobId + "?"
  );

  if (!confirmed) {
    return;
  }

  button.disabled = true;

  try {
    var response = await fetch(
      "/api/jobs/" + encodeURIComponent(jobId),
      {
        method: "DELETE"
      }
    );

    var data = null;

    try {
      data = await response.json();
    } catch (error) {
      data = null;
    }

    if (response.ok) {
      showMessage(
        "operator-message",
        data && data.message
          ? data.message
          : "Job cancelled successfully.",
        "success"
      );
    } else {
      showMessage(
        "operator-message",
        data && data.error
          ? data.error
          : "Could not cancel the job.",
        "error"
      );
    }

  } catch (error) {
    showMessage(
      "operator-message",
      "Could not connect to PrintFlow. Please try again.",
      "error"
    );
  } finally {
    button.disabled = false;
  }
}


// Connect the buttons to their functions
var updateButton = document.getElementById("update-status-btn");
var cancelButton = document.getElementById("cancel-job-btn");

if (updateButton) {
  updateButton.addEventListener("click", updateJobStatus);
}

if (cancelButton) {
  cancelButton.addEventListener("click", cancelPrintJob);
}