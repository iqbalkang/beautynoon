(function() {

    const form = document.getElementById("user-form");

    const posterContainer = document.querySelector("#poster-container");
    const posterLabel = document.querySelector("#poster-label");
    const posterImage = document.querySelector("#poster-image");

    const fileInput = document.querySelector("#poster");

    fileInput.addEventListener('change', (event) => {
        const file = event.target.files[0];
        const reader = new FileReader();

        reader.onload = (e) => {
            const fileContent = e.target.result;

            if(!posterImage) {
                const newPosterImage = document.createElement("img");
                newPosterImage.classList.add("h-100", "w-100")
                posterLabel.remove();
                newPosterImage.src = fileContent;
                posterContainer.append(newPosterImage)
                posterContainer.classList.remove("border-dashed")
            } else {
                posterImage.src = fileContent;
            }
        };

        reader.readAsDataURL(file);
    });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const email = document.querySelector("#email").value;
        const userId = document.querySelector("#userId").value;
        const editMode = document.querySelector("#editMode").value;
        // const csrfToken = document.querySelector('input[name="_csrf"]').value;
        const emailError = document.querySelector("#email-error");
        const toast = document.querySelector("#toast-container");

        console.log(editMode)


        const formData = {email}
        let link;

        if(editMode) link = `/beautynoon/check-email?id=${userId}`;
        else link = '/beautynoon/check-email'

        console.log(link)

        const response = await fetch(link, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // 'X-CSRF-TOKEN': csrfToken
            },
            body: JSON.stringify(formData)
        });

        if(!response.ok) return toast.classList.remove("d-none");

        const result = await response.json();

        if(result === true) return form.submit();
        else emailError.classList.remove("d-none")
    })

})();