document.addEventListener('DOMContentLoaded', function () {
    const templateSelect = document.getElementById('template');
    if (templateSelect) {
        templateSelect.addEventListener('change', loadTemplateFields);
    }
});

function loadTemplateFields() {
    const templateId = document.getElementById("template").value;
    if (templateId) {
        const form = document.getElementById("postForm");
        const communityId = form.querySelector("[name='communityId']").value;
        form.action = `/communities/${communityId}/posts/templates/${templateId}/loadFields`;
        form.method = 'GET';
        form.submit();
    }
}
