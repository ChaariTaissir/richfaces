package org.richfaces.renderkit.html;

import org.richfaces.component.AbstractTogglePanelTitledItem;
import org.richfaces.component.html.HtmlAccordionItem;
import org.richfaces.renderkit.RendererBase;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

import static org.richfaces.renderkit.HtmlConstants.*;
import static org.richfaces.renderkit.html.DivPanelRenderer.attributeAsString;

class AccordionItemHeaderRenderer extends TableIconsRendererHelper<HtmlAccordionItem> {

    public AccordionItemHeaderRenderer() {
        super("header", "rf-ac-itm");
    }

    protected void encodeHeaderIconLeft(ResponseWriter writer, FacesContext context, HtmlAccordionItem panel) throws IOException {
        String iconInactive = panel.isDisabled() ? panel.getLeftIconDisabled() : panel.getLeftIconInactive();
        String iconActive = panel.isDisabled() ? panel.getLeftIconDisabled() : panel.getLeftIconActive();

        encodeTdIcon(writer, context, cssClassPrefix + "-ico", iconInactive, iconActive);
    }

    protected void encodeHeaderIconRight(ResponseWriter writer, FacesContext context, HtmlAccordionItem panel) throws IOException {
        String iconInactive = panel.isDisabled() ? panel.getRightIconDisabled() : panel.getRightIconInactive();
        String iconActive = panel.isDisabled() ? panel.getRightIconDisabled() : panel.getRightIconActive();

        //TODO nick - should this be "-ico-exp"? also why expanded icon state is connected with right icon alignment?
        encodeTdIcon(writer, context, cssClassPrefix + "-exp-ico", iconInactive, iconActive);
    }

    @Override
    protected void encodeTdIcon(ResponseWriter writer, FacesContext context, String cssClass, String attrIconCollapsedValue, String attrIconExpandedValue) throws IOException {
        writer.startElement(TD_ELEM, null);
        writer.writeAttribute(CLASS_ATTRIBUTE, cssClass, null);

        encodeIdIcon(writer, context, attrIconCollapsedValue, cssIconsClassPrefix + "-act");
        encodeIdIcon(writer, context, attrIconExpandedValue, cssIconsClassPrefix + "-inact");

        writer.endElement(TD_ELEM);
    }

    @Override
    protected void encodeHeaderTextValue(ResponseWriter writer, FacesContext context, HtmlAccordionItem titledItem) throws IOException {
        if (titledItem.isDisabled()) {
            encodeHeader(writer, context, titledItem, AbstractTogglePanelTitledItem.HeaderStates.disabled);
        } else {
            encodeHeader(writer, context, titledItem, AbstractTogglePanelTitledItem.HeaderStates.active);
            encodeHeader(writer, context, titledItem, AbstractTogglePanelTitledItem.HeaderStates.inactive);
        }
    }

    private static void encodeHeader(ResponseWriter writer, FacesContext context, AbstractTogglePanelTitledItem component,
                                     AbstractTogglePanelTitledItem.HeaderStates state) throws IOException {
        writer.startElement(DIV_ELEM, component);
        writer.writeAttribute(CLASS_ATTRIBUTE, RendererBase.concatClasses("rf-ac-itm-lbl-" + state.abbreviation(), attributeAsString(component, state.headerClass())), null);

        writeFacetOrAttr(writer, context, component, "header", component.getHeaderFacet(state));

        writer.endElement(DIV_ELEM);
    }
}
