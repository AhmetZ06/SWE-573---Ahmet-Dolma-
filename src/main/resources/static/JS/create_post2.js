// post_template.js

function addField() {
    const fieldsDiv = document.getElementById('fields');
    const fieldCount = fieldsDiv.children.length;

    const newFieldDiv = document.createElement('div');
    newFieldDiv.className = 'form-group';

    const newLabel = document.createElement('label');
    newLabel.setAttribute('for', 'field' + fieldCount);
    newLabel.textContent = 'Custom Field ' + (fieldCount + 1);

    const newInput = document.createElement('input');
    newInput.type = 'text';
    newInput.className = 'form-control mb-2';
    newInput.id = 'field' + fieldCount;
    newInput.name = 'fields[customField' + fieldCount + ']';
    newInput.required = true;

    newFieldDiv.appendChild(newLabel);
    newFieldDiv.appendChild(newInput);
    fieldsDiv.appendChild(newFieldDiv);
}
