import DOMElements from "./DOMElements";

class Utils extends DOMElements {
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

        // this.addEventListeners();
    }

}