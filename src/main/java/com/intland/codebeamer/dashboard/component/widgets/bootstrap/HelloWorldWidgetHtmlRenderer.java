package com.intland.codebeamer.dashboard.component.widgets.bootstrap;

import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.StringAttribute;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@Qualifier("helloWorldWidgetHtmlRenderer")
public class HelloWorldWidgetHtmlRenderer implements Renderer<HelloWorldWidget> {

	public String render(final RenderingContext renderingContext, final HelloWorldWidget helloWidget) {
		StringAttribute welcomeTextAttribute = (StringAttribute) helloWidget.getAttributes().get(HelloWorldWidget.Attributes.WELCOME_TEXT.getKey());
		return MessageFormat.format("<strong>{0}</strong>", welcomeTextAttribute.getValue());
	}
}
