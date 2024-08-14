import DOMElements from "./DOMElements.js";

// class Users extends DOMElements {
//     constructor() {
//         super({
//             selections: {
//                 userDeleteButtons: '.delete-user-button',
//                 userAddConfirmButton: '#confirm--user-add-button',
//                 showUserFormButton: '#show-user-form-button',
//             }
//         })
//
//         this.addEventListeners();
//     }
//
//     addEventListeners() {
//         const { userDeleteButtons } = this.elements;
//         userDeleteButtons.forEach(btn => {
//             btn.addEventListeners('click', console.log('e'))
//         })
//     }
// }

class Users extends DOMElements {
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

        // userFormCloseButton.addEventListener('click', )


    }

    async fetchUserForm(href) {
        const { userFormBody } = this.elements;
        console.log(href)

        const response = await fetch(href);
        userFormBody.innerHTML = await response.text();

        const script = document.createElement('script');
        script.src = '/beautynoon/js/userForm.js';
        script.classList.add("script");
        document.body.appendChild(script);
    }

    removeScriptFromPage() {
        console.log('ss')
        const script = document.querySelector(".script")
        document.body.removeChild(script);
    }
}


export default Users;

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
//
// const showFormButton = document.querySelector("#show-form");
//
// showFormButton.addEventListener('click', async (e) => {
//     const response = await fetch("/beautynoon/users/new");
//     const result = await response.text();
//
//     document.querySelector("#user-form-body").innerHTML = result;
//
//     const script = document.createElement('script');
//     script.src = '/beautynoon/js/userForm.js';
//     script.classList.add("script");
//     document.body.appendChild(script);
// })
//
// const editFormButtons = document.querySelectorAll(".edit-form");
//
// editFormButtons.forEach(btn => {
//     btn.addEventListener('click', async (e) => {
//         const href = btn.getAttribute('href');
//         console.log(href)
//
//         const response = await fetch(href);
//         const result = await response.text();
//
//         document.querySelector("#user-form-body").innerHTML = result;
//
//         const script = document.createElement('script');
//         script.classList.add("script");
//         script.src = '/beautynoon/js/userForm.js';
//         document.body.appendChild(script);
//
//
//         // window.location.href = href;
//     })
// })
//
// const btnClose = document.querySelector("#btn-close");
//
// btnClose.addEventListener('click', (e) => {
//     console.log('ss')
//     const script = document.querySelector(".script")
//     document.body.removeChild(script);
//
// })


