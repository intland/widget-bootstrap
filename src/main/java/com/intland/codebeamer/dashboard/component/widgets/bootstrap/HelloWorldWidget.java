package com.intland.codebeamer.dashboard.component.widgets.bootstrap;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.bootstrap.attributes.EmailAttribute;
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

	public enum Attributes implements WidgetAttributeWrapper {
		WELCOME_TEXT("Welcome text", new StringAttribute("Hello World!", true, true)),
		USER_EMAIL("User email", new EmailAttribute("", true, true));

		private String key;
		private WidgetAttribute<?> defaultValue;

		Attributes(String key, WidgetAttribute<?> defaultValue) {
			this.key = key;
			this.defaultValue = defaultValue;
		}

		@Override
		public String getKey() {
			return key;
		}

		@Override
		public WidgetAttribute<?> getDefaultValue() {
			return defaultValue;
		}
	}

	private final Renderer<HelloWorldWidget> htmlRenderer;
	private final Renderer<HelloWorldWidget> editorRenderer;


	public static Map<String, WidgetAttribute> getDescriptor() {
		HashMap<String, WidgetAttribute> descriptor = new HashMap<>();
		descriptor.put(Attributes.WELCOME_TEXT.getKey(), Attributes.WELCOME_TEXT.getDefaultValue());
		descriptor.put(Attributes.USER_EMAIL.getKey(), Attributes.USER_EMAIL.getDefaultValue());
		return descriptor;
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

		addDefaultAttributes(this.attributes);

		this.htmlRenderer = htmlRenderer;
		this.editorRenderer = editorRenderer;
	}

	private void addDefaultAttributes(final Map<String, WidgetAttribute> attributes) {
		attributes.putIfAbsent(HelloWorldWidget.Attributes.WELCOME_TEXT.getKey(), HelloWorldWidget.Attributes.WELCOME_TEXT.getDefaultValue());
		attributes.putIfAbsent(HelloWorldWidget.Attributes.USER_EMAIL.getKey(), HelloWorldWidget.Attributes.USER_EMAIL.getDefaultValue());
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
