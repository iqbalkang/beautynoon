import UserForm from "./UserForm.js";
import Utils from "./Utils.js";

class App {
    constructor() {
        this.loadScripts();
    }


    loadScripts() {
        const users = new UserForm();
        const utils = new Utils();
    }
}

const app = new App();
