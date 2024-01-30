function sendCommand() {
    var input = document.getElementById('terminalInput');
    var output = document.getElementById('terminalOutput');
    var command = input.value;

    input.value = '';

    output.textContent += '\n> ' + command;

    fetch('/command', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ command: command })
    })

    .then(response => response.text())
    .then(data => { output.textContent += '\n' + data; })
    .catch(error => { console.error("Error: ", error); });
}