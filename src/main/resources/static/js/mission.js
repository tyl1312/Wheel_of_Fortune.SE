// Functions to handle mission claiming

// Function to handle claiming a single mission
function claimMission(missionId, missionType) {
    fetch('/missions/claim', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            missionId: missionId,
            missionType: missionType
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to claim mission');
        }
        return response.json();
    })
    .then(data => {
        // Update the spin count in the UI
        const spinElement = document.querySelector('.spin-number');
        if (spinElement) {
            spinElement.textContent = data.spin;
        }
        
        // Update the button state - add disabled class
        const claimedBtn = document.querySelector(`button[data-mission-id="${missionId}"][data-mission-type="${missionType}"]`);
        if (claimedBtn) {
            claimedBtn.classList.add('disabled');
        }
        
        // Show confetti animation
        confetti({
            particleCount: 100,
            spread: 70,
            origin: { y: 0.6 }
        });
    })
    .catch(error => {
        console.error('Error claiming mission:', error);
    });
}

// Function to handle claiming all completed missions
function claimAllMissions() {
    fetch('/missions/claim-all', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to claim all missions');
        }
        return response.json();
    })
    .then(data => {
        // Update the spin count in the UI
        const spinElement = document.querySelector('.spin-number');
        if (spinElement) {
            spinElement.textContent = data.spin;
        }
        
        // Update all claim buttons to be disabled
        const claimButtons = document.querySelectorAll('.claim-btn:not(.disabled)');
        claimButtons.forEach(button => {
            button.classList.add('disabled');
        });
        
        // Show confetti animation if any missions were claimed
        if (claimButtons.length > 0) {
            confetti({
                particleCount: 150,
                spread: 100,
                origin: { y: 0.6 }
            });
        }
    })
    .catch(error => {
        console.error('Error claiming all missions:', error);
    });
}

// Set up event listeners when the DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    // Show mission panel when clicking the mission button
    const showMissionBtn = document.getElementById('show-mission-btn');
    const missionPanel = document.getElementById('mission-panel');
    const overlay = document.getElementById('overlay');
    const closeMissionBtn = document.getElementById('close-mission-btn');
    
    if (showMissionBtn && missionPanel) {
        showMissionBtn.addEventListener('click', function() {
            missionPanel.classList.remove('hidden');
            if (overlay) overlay.classList.add('active');
        });
    }
    
    if (closeMissionBtn && missionPanel) {
        closeMissionBtn.addEventListener('click', function() {
            missionPanel.classList.add('hidden');
            if (overlay) overlay.classList.remove('active');
        });
    }
    
    // Individual claim buttons
    const claimButtons = document.querySelectorAll('.claim-btn:not(.disabled)');
    claimButtons.forEach(button => {
        button.addEventListener('click', function() {
            const missionId = parseInt(this.getAttribute('data-mission-id'));
            const missionType = this.getAttribute('data-mission-type');
            claimMission(missionId, missionType);
        });
    });

    // Claim all button
    const claimAllButton = document.querySelector('.claim-all-btn');
    if (claimAllButton) {
        claimAllButton.addEventListener('click', function() {
            claimAllMissions();
        });
    }
});