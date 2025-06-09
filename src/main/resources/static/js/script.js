//DOM Elements
const progress = document.getElementsByClassName("line left")[0]
const milestones = document.querySelectorAll('.milestone');

const showMissionBtn = document.getElementById('show-mission-btn');
const closeMissionBtn = document.getElementById('close-mission-btn');
const missionPanel = document.getElementById('mission-panel');
const overlay = document.getElementById('overlay'); 

document.addEventListener('DOMContentLoaded', function () {
    const totalSpent = document.body.getAttribute('data-total-spent');
    const maxSpent = 3000000;
    const value = Math.min(100, (totalSpent / maxSpent) * 100);
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
    // Ensure progress element exists
    if (!progress) {
        console.error('Progress bar element not found');
        return;
    }

    progress.style.height = `${value}%`;
    progress.style.bottom = '0';
    progress.style.backgroundColor = getComputedStyle(document.documentElement)
        .getPropertyValue('--milestone-color').trim();

    // Update milestones based on progress
    milestones.forEach(milestone => {
        const milestoneValue = parseInt(milestone.getAttribute('data-value'));
        if (milestoneValue <= value) {
            // Find the ticket box within this specific milestone
            const ticketBox = milestone.querySelector('.ticket-box');
            if (ticketBox && !ticketBox.classList.contains('claimed')) {
                ticketBox.classList.add("revealed");
            }
        }
    });
}

// Show and hide the mission panel
showMissionBtn.addEventListener('click', () => {
    missionPanel.classList.add('visible');
    overlay.classList.add('visible');
});

closeMissionBtn.addEventListener('click', () => {
    missionPanel.classList.remove('visible');
    overlay.classList.remove('visible');
});

overlay.addEventListener('click', () => {
    const missionVisible = missionPanel.classList.contains('visible');
    const historyPanel = document.getElementById('history-panel');
    const historyVisible = historyPanel && historyPanel.classList.contains('visible');

    if (missionVisible) {
        missionPanel.classList.remove('visible');
        overlay.classList.remove('visible');
    } else if (historyVisible) {
        historyPanel.classList.remove('visible');
        overlay.classList.remove('visible');
    }
});


