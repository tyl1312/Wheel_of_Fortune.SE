document.addEventListener('DOMContentLoaded', function () {
    //DOM Elements
    const wheel = document.getElementById("wheel");
    const spinButton = document.getElementById("spinButton");
    const resultText = document.getElementById("resultText");
    const resultPopup = document.getElementById("resultPopup");
    const overlay = document.getElementById('overlay');

    // Lucky Wheel Variables
    const rewards = [
        "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "Better luck next time!"
    ];
    const segmentCount = rewards.length;
    const segmentAngle = 360 / segmentCount;
    let angleSoFar = 0;

    // Initialize the wheel
    drawWheel();

    // Event Listeners
    spinButton.addEventListener('click', spinWheel);
    closePopupBtn.addEventListener('click', closePopup);

    prizes.forEach(prize => {
        prize.addEventListener('click', function () {
            if (this.classList.contains('revealed')) {
                alert("Prize granted!");
            }
        });
    });

    function drawWheel() {
        const centerX = 200;
        const centerY = 200;
        const radius = 200;
        const colors = ["#f44336", "#e91e63", "#9c27b0", "#3f51b5", "#2196f3",
            "#00bcd4", "#009688", "#4caf50", "#cddc39", "#ff9800"];

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
            wheel.appendChild(path);

            // Add text to each segment
            const textAngle = startAngle + segmentAngle / 2;
            const tx = centerX + 140 * Math.cos((textAngle * Math.PI) / 180);
            const ty = centerY + 140 * Math.sin((textAngle * Math.PI) / 180);

            const text = document.createElementNS("http://www.w3.org/2000/svg", "text");
            text.setAttribute("x", tx);
            text.setAttribute("y", ty);
            text.setAttribute("text-anchor", "middle");
            text.setAttribute("alignment-baseline", "middle");
            text.setAttribute("fill", "white");
            text.setAttribute("font-size", "14");
            text.setAttribute("transform", `rotate(${textAngle}, ${tx}, ${ty})`);
            text.textContent = rewards[i];
            wheel.appendChild(text);
        }
    }

    function spinWheel() {
        spinButton.disabled = true;
        const randomSpin = 720 + Math.random() * 360;
        angleSoFar += randomSpin;
        wheel.style.transform = `rotate(${angleSoFar}deg)`;

        setTimeout(() => {
            const finalAngle = angleSoFar % 360;
            const pointerAngle = 360 - finalAngle;
            let index = Math.floor(pointerAngle / segmentAngle);
            if (index >= segmentCount) index = 0;

            const result = rewards[index];

            if (result === "Better luck next time!") {
                resultText.innerText = "Better luck next time!";
            } else {
                resultText.innerText = result;
                confetti({
                    particleCount: 100,
                    spread: 70,
                    origin: { y: 0.6 },
                    colors: ["#ff0000", "#00ff00", "#0000ff", "#ffff00"],
                });
            }

            // 👉 Gọi API để lưu kết quả
            saveResultToServer(result);

            resultPopup.style.display = "block";
            overlay.classList.add('visible');
            spinButton.disabled = false;
        }, 4200);
    }

    // 👉 Hàm gọi API lưu kết quả về server
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
        .then(response => response.text())
        .then(data => {
            console.log("Server response:", data);
            // Nếu muốn có thể hiển thị thông báo:
            // alert(data);
        })
        .catch(error => {
            console.error("Lỗi khi lưu kết quả:", error);
        });
    }

    function closePopup() {
        resultPopup.style.display = "none";
        overlay.classList.remove('visible');
    }
});
