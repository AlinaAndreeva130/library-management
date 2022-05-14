import {customElement, html, LitElement} from "lit-element";
import '@vaadin/vaadin-grid';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-button';

@customElement('user-view')
export class UserView extends LitElement {
    protected createRenderRoot(): Element | ShadowRoot {
        return this;
    }

    protected render(): unknown {
        return html`
            <vaadin-vertical-layout id="user-view-root">
                <vaadin-horizontal-layout id="action-panel">
                    <vaadin-button id="add-btn"></vaadin-button>
                    <vaadin-button id="edit-btn"></vaadin-button>
                    <vaadin-button id="delete-btn"></vaadin-button>
                </vaadin-horizontal-layout>
                <vaadin-grid id="grid"/>
            </vaadin-vertical-layout>
        `;
    }

}