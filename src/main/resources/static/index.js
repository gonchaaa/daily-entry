function renderEntryList(entries) {
    const list = document.getElementById("entryList");
    list.innerHTML = "";
    entries.forEach(entry => {
        const li = document.createElement("li");
        li.textContent = `${entry.date}: ${entry.title} - ${entry.description}`;
        list.appendChild(li);
    });
}

function saveEntry() {
    const reportDate = document.getElementById("reportDate").value;
    const reportTitle = document.getElementById("reportTitle").value;
    const reportDescription = document.getElementById("reportDescription").value;

    if (!reportTitle || !reportDescription || !reportDate) {
        alert("Please fill in all fields.");
        return;
    }

    const entry = {
        date: reportDate,
        title: reportTitle,
        description: reportDescription,
        status: "PENDING"
    };

    const entries = JSON.parse(localStorage.getItem("entries")) || [];

    if (entries.find(e => e.date === reportDate)) {
        alert("Entry already exists for this date.");
        return;
    }

    entries.push(entry);
    localStorage.setItem("entries", JSON.stringify(entries));
    alert("Entry saved successfully!");

    renderEntryList(entries);
}


function showSummary() {
    const entries = JSON.parse(localStorage.getItem("entries")) || [];

    if (entries.length < 5) {
        alert("You must have 5 daily entries before sending.");
        return;
    }


    let combined = "Weekly Summary:\n";
    entries.forEach(entry => {
        combined += `📅 ${entry.date}\n📌 ${entry.title}\n📝 ${entry.description}\n\n`;
    });

    document.getElementById("summary").innerText = combined;

    // Backend-ə göndər
    fetch("/daily/summary", {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(entries)
    })
    .then(response => response.text())
    .then(data => {
        alert("Summary sent and displayed successfully.");
        localStorage.removeItem("entries");
        renderEntryList([]);
    })
    .catch(err => {
        alert("Error sending entries: " + err.message);
    });
}


document.addEventListener("DOMContentLoaded", () => {
    const savedEntries = JSON.parse(localStorage.getItem("entries")) || [];
    renderEntryList(savedEntries);
});
