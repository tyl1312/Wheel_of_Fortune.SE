document.addEventListener('DOMContentLoaded', function () {
    //DOM Elements
    const wheel = document.getElementById("wheel");
    const spinButton = document.getElementById("spinButton");
    const resultText = document.getElementById("resultText");
    const resultPopup = document.getElementById("resultPopup");
    const closePopupBtn = document.getElementById("closePopupBtn");
    const overlay = document.getElementById('overlay');

    // Lucky Wheel Variables
    const rewards = [
        "Free Grocery Bag",
        "10% Off Coupon", 
        "Mystery Item",
        "BOGO Coupon",
        "200K VND Voucher",
        "One more spin",
        "Free Produce Item",
        "50K VND Voucher",
        "Super Prize Entry",
        "Better luck next time!"
    ];
    const segmentCount = rewards.length;
    const segmentAngle = 360 / segmentCount;
    let angleSoFar = 0;
    let isSpinning = false; 

    drawWheel();

    spinButton.addEventListener('click', function() {
        if (isSpinning) return; 
        
        const spinCount = parseInt(document.querySelector('.spin-number').textContent);
        if (spinCount <= 0) {
            alert('You have no spins left!');
            return;
        }
        
        isSpinning = true;
        this.disabled = true;
        
        // Call backend to update spin mission first
        fetch('/api/spin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => response.text())
        .then(data => {
            if (data === 'success') {
                spinWheel();
            } else {
                console.error('Failed to update mission');
                isSpinning = false;
                this.disabled = false;
            }
        })
        .catch(error => {
            console.error('Error:', error);
            isSpinning = false;
            this.disabled = false;
        });
    });

    closePopupBtn.addEventListener('click', closePopup);

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
            text.setAttribute("text-shadow", "1px 1px 2px rgba(0,0,0,0.7)");
            
            // Rotate text to be readable
            const textRotation = textAngle > 90 && textAngle < 270 ? textAngle + 180 : textAngle;
            text.setAttribute("transform", `rotate(${textRotation}, ${tx}, ${ty})`);
            text.textContent = rewards[i];
            wheel.appendChild(text);
        }
    }

    function spinWheel() {
        const spinNumberElement = document.querySelector('.spin-number');
        const currentSpins = parseInt(spinNumberElement.textContent);

        if (currentSpins <= 0) {
            alert('You have no spins remaining!');
            isSpinning = false;
            spinButton.disabled = false;
            return;
        }

        const randomSpin = 720 + Math.random() * 360;
        angleSoFar += randomSpin;
        wheel.style.transform = `rotate(${angleSoFar}deg)`;

        const newSpinCount = currentSpins - 1;
        spinNumberElement.textContent = newSpinCount;

        // Update user spins in database
        fetch(`/users/me`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                spin: newSpinCount
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update spins');
            }
            return response.json();
        })
        .catch(error => {
            console.error("Error updating spins:", error);
            spinNumberElement.textContent = currentSpins;
        });

        setTimeout(() => {
            const finalAngle = angleSoFar % 360;
            const pointerAngle = 360 - finalAngle;
            let index = Math.floor(pointerAngle / segmentAngle);
            if (index >= segmentCount) index = 0;

            const result = rewards[index];

            // Handle different prize types
            if (result === "Better luck next time!") {
                resultText.innerText = "Better luck next time!";
            } else if (result === "One more spin") {
                const currentSpinCount = parseInt(spinNumberElement.textContent);
                spinNumberElement.textContent = currentSpinCount + 1;
                
                fetch(`/users/me`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        spin: currentSpinCount + 1
                    })
                });
                
                resultText.innerText = "🎉 One more spin! 🎉";
            } else {
                resultText.innerText = `🎁 Congratulations! You won: ${result}!`;
                confetti({
                    particleCount: 100,
                    spread: 70,
                    origin: { y: 0.6 },
                    colors: ["#ff0000", "#00ff00", "#0000ff", "#ffff00"],
                });
            }

            saveResultToServer(result);
            resultPopup.style.display = "block";
            overlay.classList.add('visible');
            
            // Reset spinning state
            isSpinning = false;
            spinButton.disabled = false;
        }, 4200);
    }

    function saveResultToServer(reward) {
        fetch("/spin/save-result", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                reward: reward
            })
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {
                    throw new Error(err.error || 'Failed to save result');
                });
            }
            return response.json();
        })
        .then(data => {
            console.log("Server response:", data);
        })
        .catch(error => {
            console.error("Error saving result:", error);
        });
    }

    function closePopup() {
        resultPopup.style.display = "none";
        overlay.classList.remove('visible');
    }
});

