import DOMElements from "./DOMElements.js";
// import SubmitUserForm from "./SubmitUserForm";

class UserForm extends DOMElements {
    constructor() {
        super({
            selections: {
                userAddConfirmButton: '#confirm-user-add-button',
                showUserFormButton: '#user-form-show-button',
                userFormBody: '#user-form-body',
                deleteUserButtons: '.delete-user-button',
                editUserButtons: ".edit-user-button",
                userFormCloseButton: "#user-form-close-button",
                editAccountButton: ".edit-account-button",
            },
        });

        this.addEventListeners();
    }

    addEventListeners() {
        const { deleteUserButtons, showUserFormButton, editUserButtons, editAccountButton } = this.elements;

        showUserFormButton.addEventListener('click', this.fetchUserForm.bind(this, "/beautynoon/users/new"));

        if(!editUserButtons && !deleteUserButtons) return;
        const editUserButtonsArray = this.checkIfNodeList(editUserButtons);
        const deleteUserButtonsArray = this.checkIfNodeList(deleteUserButtons);

        editUserButtonsArray.forEach(editButton => {
            const href = editButton.getAttribute('href');
            editButton.addEventListener('click', this.fetchUserForm.bind(this, href))
        })

        deleteUserButtonsArray.forEach(deleteButton => {
            const href = deleteButton.getAttribute('href');
            deleteButton.addEventListener('click', this.openConfirmationModal.bind(this, href))
        })

        editAccountButton.addEventListener('click', this.fetchUserForm.bind(this, editAccountButton.getAttribute('href')));

        // editAccountButton.forEach(editButton => {
        //     const href = editButton.getAttribute('href');
        //     editAccountButton.addEventListener('click', this.fetchUserForm.bind(this, href))
        // })

    }

    checkIfNodeList(list) {
        return list instanceof NodeList ? [...list] : [list];
    }

    async fetchUserForm(href) {
        const { userFormBody } = this.elements;

        const response = await fetch(href);
        userFormBody.innerHTML = await response.text();

        const module = await import('/beautynoon/js/SubmitUserForm.js');
        const mod = await new module.default()
    }

    openConfirmationModal(href) {
        const { userAddConfirmButton } = this.elements;
        userAddConfirmButton.addEventListener('click', this.deleteUser.bind(null, href));
    }

    deleteUser(href) {
        window.location.href = href;
    }
}


export default UserForm;





