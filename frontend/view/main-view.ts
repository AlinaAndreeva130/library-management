import {html, LitElement, customElement} from "lit-element";
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';

@customElement('main-view')
export class MainView extends LitElement {
    protected createRenderRoot(): Element | ShadowRoot {
        return this;
    }

    protected render() {
        return html`
            <vaadin-vertical-layout id="main-view-root">
                <label id="text"/>
            </vaadin-vertical-layout>
        `
    }
}