import DOMElements from "./DOMElements.js";

class UserForm extends DOMElements {
    constructor() {
        super({
            selections: {
                userAddConfirmButton: '#confirm--user-add-button',
                showUserFormButton: '#user-form-show-button',
                userFormBody: '#user-form-body',
                deleteUserButtons: '.delete-user-button',
                editUserButtons: ".edit-user-button",
                userFormCloseButton: "#user-form-close-button"
            },
        });

        this.addEventListeners();
    }

    addEventListeners() {
        const { deleteUserButtons, showUserFormButton, editUserButtons, userFormCloseButton } = this.elements;

        showUserFormButton.addEventListener('click', this.fetchUserForm.bind(this, "/beautynoon/users/new"));

        editUserButtons.forEach(editButton => {
            const href = editButton.getAttribute('href');
            editButton.addEventListener('click', this.fetchUserForm.bind(this, href))
        })

        userFormCloseButton.addEventListener('click', this.removeScriptFromPage.bind())

        deleteUserButtons.forEach(deleteButton => {
            deleteButton.addEventListener('click', this.fetchUserForm.bind(this, href))
        })

    }

    async fetchUserForm(href) {
        const { userFormBody } = this.elements;

        const response = await fetch(href);
        userFormBody.innerHTML = await response.text();

        const script = document.createElement('script');
        script.src = '/beautynoon/js/SubmitUserForm.js';
        script.classList.add("script");
        document.body.appendChild(script);
    }

    removeScriptFromPage() {
        const script = document.querySelector(".script")
        document.body.removeChild(script);
    }
}


export default UserForm;

// const deleteButtons = document.querySelectorAll(("#delete-button"))
// const confirmButton = document.querySelector(("#confirm-button"))
//
// const deleteUser = (href) => window.location.href = href;
//
//
// deleteButtons.forEach(btn => {
//     btn.addEventListener('click', (e) => {
//         const href = btn.getAttribute('href');
//         confirmButton.addEventListener('click', deleteUser.bind(null, href));
//     })
// })
//
// const removeToast = (element) => {
//     element.closest("#toast-container").classList.add('animate__bounceOut');
//     element.addEventListener('animationend', () => {
//         element.closest('#toast-container').remove();
//     }, { once: true });
// };
//
//
// window.addEventListener("load", () => {
//     const toastMessage = document.getElementById('toast-container');
//
//     if(!toastMessage) return;
//
//     toastMessage.addEventListener('click', (event) => removeToast(event.currentTarget));
//
//     setTimeout(() => {
//         removeToast(toastMessage);
//     }, 4000);
//
// })





