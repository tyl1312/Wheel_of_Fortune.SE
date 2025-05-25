//Transition for the progress bar
const progress = document.getElementsByClassName("line left")[0]
const milestones = document.querySelectorAll(".milestone")
let value = 0
function myFunction() {
    value = Math.min(100, value + 10); // prevent going over 100%
    progress.style.height = value + '%';
    progress.style.bottom = '0'; // make sure it grows from bottom
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

//Add ticket count to spin number
document.querySelectorAll('.ticket-box').forEach(function (box) {
    box.addEventListener('click', function () {
        if (this.classList.contains('revealed')) {
            const ticketCount = parseInt(this.querySelector('.ticket-count').textContent, 10);

            const spinNumberElement = document.querySelector('.spin-number');
            const currentSpinNumber = parseInt(spinNumberElement.textContent, 10);
            const newSpinNumber = currentSpinNumber + ticketCount;

            spinNumberElement.textContent = newSpinNumber;

            this.classList.add('clicked');
        }
    });
});


// Show and hide the mission and history panel
const showMissionBtn = document.getElementById('show-mission-btn');
const closeMissionBtn = document.getElementById('close-mission-btn');
const missionPanel = document.getElementById('mission-panel');

const showHistoryBtn = document.getElementById('show-history-btn');
const closeHistoryBtn = document.getElementById('close-history-btn');
const historyPanel = document.getElementById('history-panel');
const overlay = document.getElementById('overlay');

showMissionBtn.addEventListener('click', () => {
    missionPanel.classList.add('visible');
    overlay.classList.add('visible');
});

closeMissionBtn.addEventListener('click', () => {
    missionPanel.classList.remove('visible');
    overlay.classList.remove('visible');
});

showHistoryBtn.addEventListener('click', () => {
    historyPanel.classList.add('visible');
    overlay.classList.add('visible');
});

closeHistoryBtn.addEventListener('click', () => {
    historyPanel.classList.remove('visible');
    overlay.classList.remove('visible');
});

overlay.addEventListener('click', () => {
    missionPanel.classList.remove('visible');
    historyPanel.classList.remove('visible');
    overlay.classList.remove('visible');
});
function onSpinFinished(userId) {
    fetch("/api/spin?userId=" + userId, { method: "POST" })
    .then(res => res.json())
    .then(prize => {
        alert("Bạn nhận được: " + prize.prizeDescription);
    });
}
