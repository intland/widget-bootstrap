package com.intland.codebeamer.dashboard.component.widgets.bootstrap;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.AbstractWidget;
import com.intland.codebeamer.dashboard.component.widgets.common.WidgetAttributeWrapper;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.StringAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic=true)
public class HelloWorldWidget extends AbstractWidget {

	private static final String VERSION = "1.0.0";

	private final Renderer<HelloWorldWidget> htmlRenderer;
	private final Renderer<HelloWorldWidget> editorRenderer;


	public static Map<String, WidgetAttribute> getDescriptor() {
		return new HashMap<>();
	}
	/**
	 * @param id
	 * @param attributes
	 * @param htmlRenderer
	 * @param editorRenderer
	 */
	public HelloWorldWidget(@JsonProperty("id") final String id, @JsonProperty("attributes") final Map<String, WidgetAttribute> attributes,
                            @JacksonInject("helloWorldWidgetHtmlRenderer") final Renderer<HelloWorldWidget> htmlRenderer,
                            @JacksonInject("helloWorldWidgetEditorRenderer") final Renderer<HelloWorldWidget> editorRenderer) {
		super(id, attributes);

		this.htmlRenderer = htmlRenderer;
		this.editorRenderer = editorRenderer;
	}

	public String getTypeName() {
		return HelloWorldWidget.class.getCanonicalName();
	}

	public String renderToHtml(final RenderingContext renderingContext) {
		return htmlRenderer.render(renderingContext, this);
	}

	public String renderEditorToHtml(final RenderingContext renderingContext) {
		return editorRenderer.render(renderingContext, this);
	}

	public String getDefaultTitleKey() {
		return "dashboard.helloWorld.widget.name";
	}

	public String getVersion() {
		return VERSION;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
