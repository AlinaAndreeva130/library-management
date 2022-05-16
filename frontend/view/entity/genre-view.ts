import {customElement, html, LitElement} from "lit-element";
import '@vaadin/vaadin-grid';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-button';

@customElement('genre-view')
export class GenreView extends LitElement {
    protected createRenderRoot(): Element | ShadowRoot {
        return this;
    }

    protected render(): unknown {
        return html`
            <vaadin-vertical-layout id="genre-view-root">
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