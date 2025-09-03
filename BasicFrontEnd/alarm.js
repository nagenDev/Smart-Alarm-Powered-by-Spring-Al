class AlarmScheduler {
    constructor() {
        this.activeAlarms = new Map(); // Store active alarms: {id: timeoutId}
    }

    // Schedule an alarm to trigger at specific time
    scheduleAlarm(alarm) {
        const now = new Date();
        const alarmTime = new Date(alarm.time);
        
        // Calculate time difference in milliseconds
        const timeDiff = alarmTime.getTime() - now.getTime();
        
        if (timeDiff <= 0) {
            console.log("Alarm time already passed:", alarm.id);
            return false;
        }

        // Clear existing alarm if any
        this.cancelAlarm(alarm.id);

        // Set timeout for the alarm
        const timeoutId = setTimeout(() => {
            this.triggerAlarm(alarm);
        }, timeDiff);

        // Store the timeout reference
        this.activeAlarms.set(alarm.id, timeoutId);
        
        console.log(`Alarm ${alarm.id} scheduled for ${alarmTime}`);
        return true;
    }

    // Cancel a scheduled alarm
    cancelAlarm(alarmId) {
        if (this.activeAlarms.has(alarmId)) {
            clearTimeout(this.activeAlarms.get(alarmId));
            this.activeAlarms.delete(alarmId);
            console.log(`Alarm ${alarmId} cancelled`);
        }
    }

    // Trigger the alarm - call TTS API
    async triggerAlarm(alarm) {
        console.log("🔔 Alarm triggered:", alarm.id);
        
        try {
            // Get the text for TTS (use alarm message or default)
            const alertText = alarm.promtString || `Alarm triggered for ${new Date().toLocaleTimeString()}`;
            
            // Call TTS API
            const response = await fetch(`http://localhost:8080/api/textToVoice/get?inputText=${encodeURIComponent(alertText)}`);
            
            if (response.ok) {
                const blob = await response.blob();
                if (blob.size > 0) {
                    // Play the audio
                    const url = URL.createObjectURL(blob);
                    const player = document.getElementById("responsePlayer");
                    player.src = url;
                    player.play();
                    
                    showToast("⏰ Alarm ringing: " + alertText);
                    console.log("Alarm audio played successfully");
                }
            } else {
                console.error("TTS API error:", response.status);
                // Fallback: browser speech synthesis
                this.fallbackTTS(alertText);
            }
        } catch (error) {
            console.error("Alarm trigger error:", error);
            this.fallbackTTS(alarm.promtString || "Alarm triggered");
        } finally {
            // Remove from active alarms
            this.activeAlarms.delete(alarm.id);
        }
    }

    // Fallback using browser's speech synthesis
    fallbackTTS(text) {
        if ('speechSynthesis' in window) {
            const speech = new SpeechSynthesisUtterance(text);
            window.speechSynthesis.speak(speech);
            showToast("⏰ Alarm: " + text);
        } else {
            // Ultimate fallback: alert
            alert("⏰ ALARM: " + text);
        }
    }

    // Initialize alarms from database
    async initializeAlarms() {
        try {
            const alarms = await getAlarms(); // Your existing function
            const now = new Date();
            
            alarms.forEach(alarm => {
                const alarmTime = new Date(alarm.time);
                if (alarmTime > now) {
                    this.scheduleAlarm(alarm);
                }
            });
            
            console.log(`Initialized ${alarms.length} alarms`);
        } catch (error) {
            console.error("Error initializing alarms:", error);
        }
    }

    // Clean up all alarms
    cleanup() {
        this.activeAlarms.forEach((timeoutId, alarmId) => {
            clearTimeout(timeoutId);
        });
        this.activeAlarms.clear();
        console.log("All alarms cleaned up");
    }
}

// Global instance
const alarmScheduler = new AlarmScheduler();

// Initialize when page loads
document.addEventListener('DOMContentLoaded', () => {
    alarmScheduler.initializeAlarms();
});

// Export for use in other files
window.alarmScheduler = alarmScheduler;