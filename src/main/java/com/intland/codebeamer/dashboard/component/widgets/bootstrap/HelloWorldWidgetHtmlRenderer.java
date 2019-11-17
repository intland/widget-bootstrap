package com.intland.codebeamer.dashboard.component.widgets.bootstrap;

import com.intland.codebeamer.controller.QueriesResult;
import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.ProjectAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.TrackerAttribute;
import com.intland.codebeamer.manager.CbQLManager;
import com.intland.codebeamer.persistence.dao.CbQLPaginatedDtoList;
import com.intland.codebeamer.persistence.dto.TrackerItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("helloWorldWidgetHtmlRenderer")
public class HelloWorldWidgetHtmlRenderer implements Renderer<HelloWorldWidget> {

	@Autowired
	CbQLManager cbQLManager;

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

		QueriesResult trackerItemQueryResult = cbQLManager.runQuery(renderingContext.getRequest(), MessageFormat.format("project.id IN ({0}) AND tracker.id IN ({1})", projectIdsString, trackerIdsString), new CbQLManager.PagingParams(5, 0));
		CbQLPaginatedDtoList<TrackerItemDto> trackerItemsPage = trackerItemQueryResult.getTrackerItems();
		List<TrackerItemDto> trackerItems = trackerItemsPage.getList();

		String trackerItemsRender = trackerItems.stream()
				.map(trackerItemDto -> MessageFormat.format("{0}({1}): {2}", trackerItemDto.getName(), trackerItemDto.getId().toString(), trackerItemDto.getStatus().getName()))
				.collect(Collectors.joining("<br>"));

		return MessageFormat.format("<strong>First page of items </strong> </br> {0}", trackerItemsRender);
	}
}
