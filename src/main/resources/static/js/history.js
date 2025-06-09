function updateHistoryTable(historyList) {
    const tbody = document.getElementById("history-table-body");
    const noHistoryText = document.getElementById("no-history-text");

    if (!tbody || !noHistoryText) {
        console.error("History elements not found in DOM");
        return;
    }

    tbody.innerHTML = "";

    if (!historyList || historyList.length === 0) {
        noHistoryText.style.display = "block";
        return;
    }

    noHistoryText.style.display = "none";

    historyList.forEach(item => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${item.prizeName}</td>
            <td>${new Date(item.timestamp).toLocaleDateString()} ${new Date(item.timestamp).toLocaleTimeString()}</td>
        `;
        tbody.appendChild(row);
    });
}

function fetchAndShowHistory() {
    fetch("/api/spin-history")
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch history");
            }
            return response.json();
        })
        .then(data => {
            console.log("History data received:", data);
            updateHistoryTable(data);
            showHistoryPanel();
        })
        .catch(error => {
            console.error("Error fetching history:", error);
            alert("Failed to load spin history: " + error.message);
        });
}

function showHistoryPanel() {
    const historyPanel = document.getElementById("history-panel");
    const overlay = document.getElementById('overlay');
    
    if (historyPanel && overlay) {
        historyPanel.classList.remove('hidden');
        historyPanel.classList.add('visible');
        overlay.classList.add('visible');
    }
}

function hideHistoryPanel() {
    const historyPanel = document.getElementById("history-panel");
    const overlay = document.getElementById('overlay');
    
    if (historyPanel && overlay) {
        historyPanel.classList.add('hidden');
        historyPanel.classList.remove('visible');
        overlay.classList.remove('visible');
    }
}

// Initialize history functionality when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    const showHistoryBtn = document.getElementById("show-history-btn");
    const closeHistoryBtn = document.getElementById("close-history-btn");
    const overlay = document.getElementById('overlay');

    // Show history button event listener
    if (showHistoryBtn) {
        showHistoryBtn.addEventListener("click", fetchAndShowHistory);
    }

    // Close history button event listener
    if (closeHistoryBtn) {
        closeHistoryBtn.addEventListener("click", hideHistoryPanel);
    }

    // Close history panel when clicking overlay (only if history panel is visible)
    if (overlay) {
        overlay.addEventListener("click", (event) => {
            const historyPanel = document.getElementById("history-panel");
            if (historyPanel && historyPanel.classList.contains('visible')) {
                hideHistoryPanel();
            }
        });
    }
});