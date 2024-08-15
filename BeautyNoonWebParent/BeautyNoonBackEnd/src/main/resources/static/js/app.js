import UserForm from "./UserForm.js";
import Utils from "./Utils.js";
import SubmitUserForm from "./SubmitUserForm.js";

class App {
    constructor() {
        this.loadScripts();
    }


    loadScripts() {
        const users = new UserForm();
        const utils = new Utils();
        // const submitUserForm = new SubmitUserForm();
    }
}

const app = new App();
