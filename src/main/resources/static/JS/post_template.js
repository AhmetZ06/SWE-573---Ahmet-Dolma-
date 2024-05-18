function addField() {
    var fieldContainer = document.getElementById("fields");
    var fieldIndex = fieldContainer.children.length;
    var fieldHtml = `
        <div class="field-container">
            <label for="fields${fieldIndex}.fieldName">Field Name:</label>
            <input type="text" name="fields[${fieldIndex}].fieldName" required>
            <label for="fields${fieldIndex}.fieldType">Field Type:</label>
            <select name="fields[${fieldIndex}].fieldType" required>
                <option value="GEOLOCATION">Geolocation</option>
                <option value="IMAGE">Image</option>
                <option value="VIDEO">Video</option>
                <option value="DATE">Date</option>
                <option value="STRING">String</option>
                <option value="INTEGER">Integer</option>
                <option value="DOUBLE">Double</option>
            </select>
        </div>
    `;
    fieldContainer.insertAdjacentHTML('beforeend', fieldHtml);
}
