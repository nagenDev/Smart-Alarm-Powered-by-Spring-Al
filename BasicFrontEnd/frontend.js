// ====== Scheduler function ======
async function waitForAlarm(alarmId) {
  try {
    showToast("⏰ Waiting for alarm to trigger...");
    
    const response = await fetch(`http://localhost:8080/api/scheduler/alarm/${alarmId}`);

    if (response.ok) {
      const blob = await response.blob();
      if (blob.size > 0) {
        const url = URL.createObjectURL(blob);
        const player = document.getElementById("responsePlayer");
        player.src = url;
        player.play();
        showToast("⏰ Alarm ringing!");
      }
    }
  } catch (err) {
    console.error("Scheduler error:", err);
    showToast("Error: " + err.message);
  }
}

// ====== Main event listeners ======
document.addEventListener("DOMContentLoaded", () => {
  const setButton = document.getElementById("openSet");
  const getButton = document.getElementById("get");
  const clearButton = document.getElementById("clear");
  const updateButton = document.getElementById("update");

  // === Set Alarm ===
  setButton.onclick = async () => {
    const json = prompt(
      'Enter alarm JSON:\n{\n  "id": 99,\n  "promtString": "Wake up Jog",\n  "time": "2025-08-29T14:43:00}"\n"time must be in utc"\n" or subtract 5hr 30min from ist"'
    );
    if (!json) return;

    let alarm;
    try {
      alarm = JSON.parse(json);
    } catch (e) {
      alert("Invalid JSON format");
      return;
    }

    try {
      const res = await createAlarm(alarm);

      if (res.ok) {
        showToast("✅ Alarm saved successfully");
        waitForAlarm(alarm.id);
      } else {
        const msg = await res.text();
        showToast("❌ Failed: " + msg);
      }
    } catch (err) {
      console.error(err);
      showToast("⚠️ Error while saving alarm");
    }
  };

  // === Get Alarms ===
  getButton.onclick = async () => {
    try {
      const alarms = await getAlarms();
      console.log("Alarms:", alarms);
      alert("Saved alarms:\n\n" + JSON.stringify(alarms, null, 2));
    } catch (err) {
      console.error(err);
      showToast("⚠️ Error fetching alarms");
    }
  };

  // === Update Alarm ===
  updateButton.onclick = async () => {
    const json = prompt(
      'Enter updated alarm JSON:\n{\n  "id": 99,\n  "promtString": "Updated Alarm",\n  "time": "2025-08-29T15:00:00"\n}"time must be in utc"'
    );
    if (!json) return;

    let alarm;
    try {
      alarm = JSON.parse(json);
    } catch (e) {
      alert("Invalid JSON format");
      return;
    }

    try {
      const res = await updateAlarm(alarm.id, alarm);

      if (res.ok) {
        showToast("✅ Alarm updated successfully");
      } else {
        const msg = await res.text();
        showToast("❌ Failed: " + msg);
      }
    } catch (err) {
      console.error(err);
      showToast("⚠️ Error while updating alarm");
    }
  };

  // === Delete Alarm ===
  clearButton.onclick = async () => {
    const id = prompt("Enter alarm ID to delete:");
    if (!id) return;

    try {
      const res = await deleteAlarm(id);

      if (res.ok) {
        showToast("✅ Alarm deleted successfully");
      } else {
        const msg = await res.text();
        showToast("❌ Failed: " + msg);
      }
    } catch (err) {
      console.error(err);
      showToast("⚠️ Error while deleting alarm");
    }
  };
});

