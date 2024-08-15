import DOMElements from "./DOMElements.js";

class Utils extends DOMElements {
    constructor() {
        super({
            selections: {
                toastMessage: '#toast-container',
            },
        });

        this.addEventListeners();
    }

    addEventListeners() {
        const { toastMessage } = this.elements;
        if(!toastMessage) return;
        toastMessage.addEventListener('click', (event) => this.removeToast(event.currentTarget));
        this.autoRemoveToast(toastMessage);
    }

    removeToast(element) {
        element.closest("#toast-container").classList.add('animate__bounceOut');
        element.addEventListener('animationend', () => {
            element.closest('#toast-container').remove();
        }, { once: true });
    }

    autoRemoveToast(toastMessage) {
        setTimeout(() => {
            this.removeToast(toastMessage);
        }, 3000);
    }

}

export default Utils;