const BASE_URL = "http://localhost:8080/alarm";

const getAlarms = () => fetch(BASE_URL).then(r => r.json());

const createAlarm = (alarm) =>
  fetch(`${BASE_URL}/post`, {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(alarm)
  });

const updateAlarm = (id, alarm) =>
  fetch(`${BASE_URL}/put/id/${id}`, {
    method: "PUT",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(alarm)
  });

const deleteAlarm = (id) =>
  fetch(`${BASE_URL}/id/${id}`, { method: "DELETE" });

// Toast function for all files to use
function showToast(msg) {
  const toast = document.getElementById("toast");
  if (toast) {
    toast.innerText = msg;
    toast.style.display = "block";
    setTimeout(() => {
      toast.style.display = "none";
    }, 3000);
  }
}