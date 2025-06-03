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
            return response.json().then(err => {
                throw new Error(err.error || 'Failed to claim mission');
            });
        }
        return response.json();
    })
    .then(data => {
        // Update the spin count in the UI
        const spinElement = document.querySelector('.spin-number');
        if (spinElement) {
            spinElement.textContent = data.spin;
        }
        
        // Update the button state - add disabled class and change text
        const claimedBtn = document.querySelector(`button[data-mission-id="${missionId}"][data-mission-type="${missionType}"]`);
        if (claimedBtn) {
            claimedBtn.classList.add('disabled');
            claimedBtn.textContent = 'Claimed';
            claimedBtn.disabled = true;
        }
        
        // Check if we should disable the claim all button
        updateClaimAllButtonState();
        
        // Show confetti animation
        if (typeof confetti !== 'undefined') {
            confetti({
                particleCount: 100,
                spread: 70,
                origin: { y: 0.6 }
            });
        }
    })
    .catch(error => {
        console.error('Error claiming mission:', error);
        alert('Error: ' + error.message);
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
            return response.json().then(err => {
                throw new Error(err.error || 'Failed to claim all missions');
            });
        }
        return response.json();
    })
    .then(data => {
        // Update the spin count in the UI
        const spinElement = document.querySelector('.spin-number');
        if (spinElement) {
            spinElement.textContent = data.spin;
        }
        
        // Update all claim buttons to be disabled and change text
        const claimButtons = document.querySelectorAll('.claim-btn:not(.disabled)');
        claimButtons.forEach(button => {
            button.classList.add('disabled');
            button.textContent = 'Claimed';
            button.disabled = true;
        });
        
        // Disable the claim all button
        updateClaimAllButtonState();
        
        // Show confetti animation if any missions were claimed
        if (data.claimedCount > 0 && typeof confetti !== 'undefined') {
            confetti({
                particleCount: 150,
                spread: 100,
                origin: { y: 0.6 }
            });
        }
    })
    .catch(error => {
        console.error('Error claiming all missions:', error);
        alert('Error: ' + error.message);
    });
}

// Function to update the state of the claim all button
function updateClaimAllButtonState() {
    const claimAllButton = document.querySelector('.claim-all-btn');
    const availableClaimButtons = document.querySelectorAll('.claim-btn:not(.disabled)');
    
    if (claimAllButton) {
        if (availableClaimButtons.length === 0) {
            claimAllButton.disabled = true;
            claimAllButton.classList.add('disabled');
        } else {
            claimAllButton.disabled = false;
            claimAllButton.classList.remove('disabled');
        }
    }
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
            missionPanel.classList.add('visible'); 
            if (overlay) overlay.classList.add('visible'); 
        });
    }
    
    if (closeMissionBtn && missionPanel) {
        closeMissionBtn.addEventListener('click', function() {
            missionPanel.classList.remove('visible');
            if (overlay) overlay.classList.remove('visible'); 
        });
    }
    
    //Claim button
    document.addEventListener('click', function(e) {
        if (e.target && e.target.classList.contains('claim-btn') && !e.target.classList.contains('disabled')) {
            const missionId = parseInt(e.target.getAttribute('data-mission-id'));
            const missionType = e.target.getAttribute('data-mission-type');
            claimMission(missionId, missionType);
        }
    });

    //Claim all button
    const claimAllButton = document.querySelector('.claim-all-btn');
    if (claimAllButton) {
        claimAllButton.addEventListener('click', function() {
            if (!this.disabled) {
                claimAllMissions();
            }
        });
    }
    
    //Initialize the claim all button state on page load
    updateClaimAllButtonState();
});