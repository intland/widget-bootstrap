package com.intland.codebeamer.dashboard.component.widgets.bootstrap;

import com.intland.codebeamer.dashboard.component.common.RenderingContextFactory;
import com.intland.codebeamer.dashboard.component.widgets.common.DefaultWidgetEditorRenderer;
import com.intland.codebeamer.dashboard.component.widgets.common.ModelPopulator;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.editor.FormLayoutEditorFooter;
import com.intland.codebeamer.utils.TemplateRenderer;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Qualifier("helloWorldWidgetEditorRenderer")
public class HelloWorldEditorRenderer extends DefaultWidgetEditorRenderer<HelloWorldWidget> {

	@Autowired
	public HelloWorldEditorRenderer(final ModelPopulator<VelocityContext> modelPopulator,
			final TemplateRenderer templateRenderer, final FormLayoutEditorFooter formLayoutEditorFooter,
			final RenderingContextFactory renderingContextFactory) {
		super(modelPopulator, templateRenderer, formLayoutEditorFooter,
				renderingContextFactory);
	}

	@Override
	protected Map<String, WidgetAttribute> getDescriptor() {
		return HelloWorldWidget.getDescriptor();
	}

}
