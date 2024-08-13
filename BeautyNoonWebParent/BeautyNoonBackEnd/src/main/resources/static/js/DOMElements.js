class DOMElements {
    elements = {};

    constructor({ selections }) {
        this.selectDOMElements(selections);
    }

    selectDOMElements(selections) {
        for (const [key, value] of Object.entries(selections)) {
            this.elements[key] = document.querySelectorAll(value);

            if (this.elements[key].length === 0) this.elements[key] = null;
            else if (this.elements[key].length === 1) this.elements[key] = document.querySelector(value);
        }
    }
}

export default DOMElements;
