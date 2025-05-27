//DOM Elements
const progress = document.getElementsByClassName("line left")[0]
const milestones = document.querySelectorAll('.milestone');

const showMissionBtn = document.getElementById('show-mission-btn');
const closeMissionBtn = document.getElementById('close-mission-btn');
const missionPanel = document.getElementById('mission-panel');

const showHistoryBtn = document.getElementById('show-history-btn');
const closeHistoryBtn = document.getElementById('close-history-btn');
const historyPanel = document.getElementById('history-panel');
const overlay = document.getElementById('overlay');

document.addEventListener('DOMContentLoaded', function () {
    const totalSpent = document.body.getAttribute('total-spent');
    const maxSpent = 3000000; // 3000k
    value = Math.min(100, (totalSpent / maxSpent) * 100);
    updateProgressBar(value);

    document.querySelectorAll('.ticket-box').forEach(function (box) {
        box.addEventListener('click', function () {
            if (this.classList.contains('revealed') && !this.classList.contains('claimed')) {
                const milestone = this.closest('.milestone');
                const rewardId = milestone.dataset.rewardId;
                const ticketCount = parseInt(this.querySelector('.ticket-count').textContent, 10);

                fetch('/users/claim-purchase-reward', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ rewardId: rewardId })
                })
                    .then(response => {
                        if (!response.ok) {
                            if (response.status === 409) {
                                throw new Error('Reward already claimed');
                            }
                            throw new Error('Failed to claim reward');
                        }
                        return response.json();
                    })
                    .then(data => {
                        this.classList.add('claimed');
                        const spinNumberElement = document.querySelector('.spin-number');
                        const currentSpins = parseInt(spinNumberElement.textContent, 10);
                        spinNumberElement.textContent = currentSpins + ticketCount;
                    })
                    .catch(error => {
                        alert(error.message);
                    });
            }
        });
    });
});

function updateProgressBar(value) {
    progress.style.height = `${value}%`;
    progress.style.bottom = '0';
    progress.style.backgroundColor = getComputedStyle(document.documentElement)
        .getPropertyValue('--milestone-color').trim();

    milestones.forEach(milestone => {
        const milestoneValue = parseInt(milestone.getAttribute('data-value'));
        if (milestoneValue <= value) {
            const classList = Array.from(milestone.classList);
            const matchingClass = classList.find(c => c.startsWith('milestone__'));
            if (matchingClass) {
                const ticketBox = document.querySelector(`.${matchingClass} .ticket-box`);
                if (ticketBox) {
                    ticketBox.classList.add("revealed");
                }
            }
        }
    });
}

// Show and hide the mission and history panel

showMissionBtn.addEventListener('click', () => {
    missionPanel.classList.add('visible');
    overlay.classList.add('visible');
});

closeMissionBtn.addEventListener('click', () => {
    missionPanel.classList.remove('visible');
    overlay.classList.remove('visible');
});

showHistoryBtn.addEventListener('click', () => {
    fetchPrizeHistory();
    historyPanel.classList.add('visible');
    overlay.classList.add('visible');
});

closeHistoryBtn.addEventListener('click', () => {
    historyPanel.classList.remove('visible');
    overlay.classList.remove('visible');
});

overlay.addEventListener('click', () => {
    let missionVisible = missionPanel.classList.contains('visible');
    let historyVisible = historyPanel.classList.contains('visible');

    if (missionVisible || historyVisible) {
        missionPanel.classList.remove('visible');
        historyPanel.classList.remove('visible');
        overlay.classList.remove('visible');
    }
});

function fetchPrizeHistory() {
    fetch("/spin/history")
        .then(response => response.json())
        .then(data => {
            const historyList = document.getElementById("history-list");
            const noHistoryText = document.getElementById("no-history-text");
            historyList.innerHTML = "";

            if (data.length === 0) {
                noHistoryText.style.display = "block";
            } else {
                noHistoryText.style.display = "none";
                data.forEach(entry => {
                    const li = document.createElement("li");
                    li.textContent = `${entry.prizeName} - ${new Date(entry.timestamp).toLocaleString()}`;
                    historyList.appendChild(li);
                });
            }
        })
        .catch(error => {
            console.error("Error fetching prize history:", error);
        });
}


