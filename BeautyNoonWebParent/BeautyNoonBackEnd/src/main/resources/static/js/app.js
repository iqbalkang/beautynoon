import Users from "./users.js";

class App {
    constructor() {
        this.loadScripts();
    }


    loadScripts() {
        const users = new Users();
    }
}

const app = new App();
