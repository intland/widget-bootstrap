package com.intland.codebeamer.dashboard.component.widgets.bootstrap;

import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.ProjectAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.TrackerAttribute;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("helloWorldWidgetHtmlRenderer")
public class HelloWorldWidgetHtmlRenderer implements Renderer<HelloWorldWidget> {

	public String render(final RenderingContext renderingContext, final HelloWorldWidget helloWidget) {
		ProjectAttribute projectAttribute = (ProjectAttribute) helloWidget.getAttributes().get(HelloWorldWidget.Attributes.PROJECT.getKey());
		TrackerAttribute trackerAttribute = (TrackerAttribute) helloWidget.getAttributes().get(HelloWorldWidget.Attributes.TRACKER.getKey());

		List<Integer> projectIds = projectAttribute.getValue() != null ? projectAttribute.getValue() : Collections.emptyList();
		List<Integer> trackerIds = trackerAttribute.getValue() != null ? trackerAttribute.getValue() : Collections.emptyList();

		String projectIdsString = projectIds.stream()
				.map(String::valueOf)
				.collect(Collectors.joining(", "));

		String trackerIdsString = trackerIds.stream()
				.map(String::valueOf)
				.collect(Collectors.joining(", "));

		return MessageFormat.format("<strong>Projects and trackers</strong> </br> Selected project ids: {0} </br> Selected tracker ids: {1}", projectIdsString, trackerIdsString);
	}
}
