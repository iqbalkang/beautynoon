import DOMElements from "./DOMElements.js";

class SubmitUserForm extends DOMElements {
    constructor() {
        super({
            selections: {
                form: '#user-form',
                posterContainer: "#poster-container",
                posterLabel: "#poster-label",
                posterImage: "#poster-image",
                fileInput: "#poster",
                email: "#email",
                userId: "#userId",
                editMode: "#editMode",
                emailError: "#email-error",
                toast: "#toast-container"
            },
        });

        this.addEventListeners();
    }

    addEventListeners() {
        const { fileInput, form } = this.elements;

        fileInput.addEventListener('change', this.loadImage.bind(this));
        form.addEventListener("submit", this.submitForm.bind(this));
    }

    loadImage() {
        const { posterImage, posterLabel, posterContainer } = this.elements;

        const file = event.target.files[0];
        const reader = new FileReader();

        reader.onload = (e) => {
            const fileContent = e.target.result;

            if(!posterImage) this.createPosterImage(posterImage, posterLabel, posterContainer, fileContent);
            else posterImage.src = fileContent;
        };

        reader.readAsDataURL(file);

    }

    createPosterImage(posterImage, posterLabel, posterContainer, fileContent) {
        const newPosterImage = document.createElement("img");
        newPosterImage.classList.add("h-100", "w-100")
        posterLabel.remove();
        newPosterImage.src = fileContent;
        posterContainer.append(newPosterImage)
        posterContainer.classList.remove("border-dashed")
    }

    async submitForm(e) {
        const { form, email, emailError } = this.elements;

        e.preventDefault();

        const formData = {email: email.value}

        const result = await this.fetchEmailStatus(formData);

        if(result === true) return form.submit();
        else emailError.classList.remove("d-none")

    }

    generateEmailCheckLink() {
        const { userId, editMode } = this.elements;

        if(editMode.value) return `/beautynoon/check-email?id=${userId.value}`;
        else return  '/beautynoon/check-email'
    }

    async fetchEmailStatus(formData) {
        const { toast } = this.elements;

        let link = this.generateEmailCheckLink();

        const response = await fetch(link, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // 'X-CSRF-TOKEN': csrfToken
            },
            body: JSON.stringify(formData)
        });

        if(!response.ok) return toast.classList.remove("d-none");

        return response.json();

    }


}

export default SubmitUserForm;
