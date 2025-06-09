document.addEventListener('DOMContentLoaded', function () {
    //DOM Elements
    const wheel = document.getElementById("wheel");
    const spinButton = document.getElementById("spinButton");
    const resultText = document.getElementById("resultText");
    const resultPopup = document.getElementById("resultPopup");
    const closePopupBtn = document.getElementById("closePopupBtn");
    const overlay = document.getElementById('overlay');

    // Wheel Variables
    let rewards = [];
    let prizes = [];
    let segmentCount = 0;
    let segmentAngle = 0;
    let angleSoFar = 0;
    let isSpinning = false; 

    loadPrizes();

    spinButton.addEventListener('click', function() {
        if (isSpinning) return; 
        
        const spinCount = parseInt(document.querySelector('.spin-number').textContent);
        if (spinCount <= 0) {
            alert('You have no spins left!');
            return;
        }
        
        isSpinning = true;
        this.disabled = true;

        fetch('/api/spin', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        })
        .then(response => response.text())
        .then(data => {
            if (data === 'success') {
                spinWheelWithProbability();
            } else {
                console.error('Failed to update mission');
                resetSpinButton();
            }
        })
        .catch(error => {
            console.error('Error:', error);
            resetSpinButton();
        });
    });

    closePopupBtn.addEventListener('click', closePopup);

    function loadPrizes() {
        fetch('/api/prizes')
            .then(response => response.json())
            .then(data => {
                prizes = data;
                rewards = data.map(prize => prize.prizeName);
                segmentCount = rewards.length;
                segmentAngle = 360 / segmentCount;
                drawWheel();
            })
            .catch(error => {
                console.error('Error loading prizes:', error);
                alert('Failed to load wheel prizes. Please refresh the page.');
            });
    }

    function spinWheelWithProbability() {
        // Get probability-based result from server
        fetch('/api/spin-wheel', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        })
        .then(response => response.json())
        .then(result => {
            if (result.error) {
                alert(result.error);
                resetSpinButton();
                return;
            }

            animateWheelToResult(result.prizeName);
            updateSpinCount();

            setTimeout(() => {
                showSpinResult(result.prizeName);
            }, 4200);
        })
        .catch(error => {
            console.error('Error spinning wheel:', error);
            alert('Failed to spin wheel. Please try again.');
            resetSpinButton();
        });
    }

    function animateWheelToResult(prizeName) {
        const selectedIndex = rewards.findIndex(reward => reward === prizeName);

        const targetAngle = selectedIndex * segmentAngle + (segmentAngle / 2);
        const spinRotations = 720 + Math.random() * 360;
        const finalAngle = (360 - targetAngle + spinRotations) % 360;
 
        angleSoFar += spinRotations + finalAngle;
        wheel.style.transform = `rotate(${angleSoFar}deg)`;
    }

    function updateSpinCount() {
        const spinElement = document.querySelector('.spin-number');
        const currentSpins = parseInt(spinElement.textContent);
        const newSpinCount = currentSpins - 1;
        spinElement.textContent = newSpinCount;

        // Update in database
        fetch('/users/me', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ spin: newSpinCount })
        }).catch(error => {
            console.error('Error updating spins:', error);
            spinElement.textContent = currentSpins;
        });
    }

    function showSpinResult(prizeName) {
        if (prizeName === "Better luck next time!") {
            resultText.innerText = "Better luck next time!";
        } else if (prizeName === "One more spin") {
            giveExtraSpin();
            resultText.innerText = "One more spin! ";
        } else {
            resultText.innerText = `🎁 Congratulations! You won: ${prizeName}!`;
            showConfetti();
        }

        saveResultToServer(prizeName);
        showResultPopup();
        resetSpinButton();
    }

    function giveExtraSpin() {
        const spinElement = document.querySelector('.spin-number');
        const currentSpins = parseInt(spinElement.textContent);
        const newSpinCount = currentSpins + 1;
        spinElement.textContent = newSpinCount;
        
        // Update in database
        fetch('/users/me', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ spin: newSpinCount })
        });
    }

    function saveResultToServer(prizeName) {
        fetch('/api/save-spin', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({ reward: prizeName })
        })
        .then(response => response.json())
        .then(data => {
            console.log('Spin result saved:', data);
        })
        .catch(error => {
            console.error('Error saving result:', error);
        });
    }

    function drawWheel() {
        const centerX = 200;
        const centerY = 200;
        const radius = 200;
        
        wheel.innerHTML = '';
        
        const colors = [
            "#f44336", "#e91e63", "#9c27b0", "#673ab7", 
            "#3f51b5", "#2196f3", "#00bcd4", "#009688", 
            "#4caf50", "#ff9800"
        ];

        for (let i = 0; i < segmentCount; i++) {
            const startAngle = segmentAngle * i;
            const endAngle = startAngle + segmentAngle;
            const largeArcFlag = segmentAngle > 180 ? 1 : 0;

            const x1 = centerX + radius * Math.cos((startAngle * Math.PI) / 180);
            const y1 = centerY + radius * Math.sin((startAngle * Math.PI) / 180);
            const x2 = centerX + radius * Math.cos((endAngle * Math.PI) / 180);
            const y2 = centerY + radius * Math.sin((endAngle * Math.PI) / 180);

            const path = document.createElementNS("http://www.w3.org/2000/svg", "path");
            const d = `M${centerX},${centerY} L${x1},${y1} A${radius},${radius} 0 ${largeArcFlag},1 ${x2},${y2} Z`;
            path.setAttribute("d", d);
            path.setAttribute("fill", colors[i % colors.length]);
            path.setAttribute("stroke", "#fff");
            path.setAttribute("stroke-width", "2");
            wheel.appendChild(path);

            const textAngle = startAngle + segmentAngle / 2;
            const textRadius = radius * 0.7;
            const tx = centerX + textRadius * Math.cos((textAngle * Math.PI) / 180);
            const ty = centerY + textRadius * Math.sin((textAngle * Math.PI) / 180);

            const text = document.createElementNS("http://www.w3.org/2000/svg", "text");
            text.setAttribute("x", tx);
            text.setAttribute("y", ty);
            text.setAttribute("text-anchor", "middle");
            text.setAttribute("alignment-baseline", "middle");
            text.setAttribute("fill", "white");
            text.setAttribute("font-size", "12"); 
            text.setAttribute("font-weight", "bold");
            
            const textRotation = textAngle > 90 && textAngle < 270 ? textAngle + 180 : textAngle;
            text.setAttribute("transform", `rotate(${textRotation}, ${tx}, ${ty})`);
            text.textContent = rewards[i];
            wheel.appendChild(text);
        }
    }

    function showConfetti() {
        if (typeof confetti !== 'undefined') {
            confetti({
                particleCount: 100,
                spread: 70,
                origin: { y: 0.6 },
                colors: ["#ff0000", "#00ff00", "#0000ff", "#ffff00"]
            });
        }
    }

    function showResultPopup() {
        resultPopup.style.display = "block";
        overlay.classList.add('visible');
    }

    function closePopup() {
        resultPopup.style.display = "none";
        overlay.classList.remove('visible');
    }

    function resetSpinButton() {
        isSpinning = false;
        spinButton.disabled = false;
    }
});

