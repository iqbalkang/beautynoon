import UserForm from "./UserForm.js";

class App {
    constructor() {
        this.loadScripts();
    }


    loadScripts() {
        const users = new UserForm();
    }
}

const app = new App();
