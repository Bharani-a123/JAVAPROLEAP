const baseUrl = '/api';

async function postData(url, data) {
  const res = await fetch(url, {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data)
  });
  return res.json();
}

document.getElementById('userForm').onsubmit = async e => {
  e.preventDefault();
  const user = {
    name: userName.value,
    email: userEmail.value,
    contactNo: userContact.value,
    dob: userDob.value
  };
  const result = await postData(`${baseUrl}/users`, user);
  alert('User added: ' + result.name);
  e.target.reset();
};

document.getElementById('vaccineForm').onsubmit = async e => {
  e.preventDefault();
  const vaccine = {
    name: vaccineName.value,
    manufacturer: vaccineManufacturer.value,
    recommendedDoses: vaccineDoses.value,
    intervalDays: vaccineInterval.value
  };
  const result = await postData(`${baseUrl}/vaccines`, vaccine);
  alert('Vaccine added: ' + result.name);
  e.target.reset();
};

document.getElementById('recordForm').onsubmit = async e => {
  e.preventDefault();
  const record = {
    user: { userId: recordUserId.value },
    vaccine: { vaccineId: recordVaccineId.value },
    doseNumber: recordDose.value,
    vaccinationDate: recordDate.value
  };
  const result = await postData(`${baseUrl}/records`, record);
  alert('Record added for User ID ' + result.user.userId);
  e.target.reset();
};

async function fetchUsers() {
  const res = await fetch(`${baseUrl}/users`);
  const data = await res.json();
  document.getElementById('output').textContent = JSON.stringify(data, null, 2);
}

async function fetchVaccines() {
  const res = await fetch(`${baseUrl}/vaccines`);
  const data = await res.json();
  document.getElementById('output').textContent = JSON.stringify(data, null, 2);
}

async function fetchRecords() {
  const res = await fetch(`${baseUrl}/records`);
  const data = await res.json();
  document.getElementById('output').textContent = JSON.stringify(data, null, 2);
}

async function fetchUpcoming() {
  const res = await fetch(`${baseUrl}/reports/upcoming-doses`);
  const data = await res.json();
  document.getElementById('output').textContent = JSON.stringify(data, null, 2);
}

async function fetchMissed() {
  const res = await fetch(`${baseUrl}/reports/missed-doses`);
  const data = await res.json();
  document.getElementById('output').textContent = JSON.stringify(data, null, 2);
}
