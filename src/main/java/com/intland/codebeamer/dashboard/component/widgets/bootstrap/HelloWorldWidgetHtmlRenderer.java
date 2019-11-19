package com.intland.codebeamer.dashboard.component.widgets.bootstrap;

import com.intland.codebeamer.controller.support.traceability.TraceabilitySupport;
import com.intland.codebeamer.controller.support.traceability.TraceabilitySupport.TraceabilityLevelResultDto;
import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.IntegerAttribute;
import com.intland.codebeamer.manager.TrackerItemManager;
import com.intland.codebeamer.persistence.dto.TraceabilityNotInitialLevelResultDto;
import com.intland.codebeamer.persistence.dto.TrackerItemDto;
import com.intland.codebeamer.persistence.dto.base.VersionReferenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.intland.codebeamer.persistence.dao.impl.EntityCache.TRACKER_ITEM_TYPE;

@Component
@Qualifier("helloWorldWidgetHtmlRenderer")
public class HelloWorldWidgetHtmlRenderer implements Renderer<HelloWorldWidget> {

	@Autowired
	TrackerItemManager trackerItemManager;

	@Autowired
	TraceabilitySupport traceabilitySupport;

	public String render(final RenderingContext renderingContext, final HelloWorldWidget helloWidget) {
		IntegerAttribute trackerItemIdAttribute = (IntegerAttribute) helloWidget.getAttributes().get(HelloWorldWidget.Attributes.TRACKER_ITEM_ID.getKey());

		Integer trackerItemId = trackerItemIdAttribute.getValue();

		String trackerItemsRender = null;
		if (trackerItemId != 0){
			TrackerItemDto trackerItem = trackerItemManager.findById(renderingContext.getUser(), trackerItemId);
			if (Objects.nonNull(trackerItem)){

				TraceabilityNotInitialLevelResultDto notInitialLevelItems = traceabilitySupport.findNotInitialLevelItems(
						renderingContext.getUser(),
						createLevelFilter(Collections.singletonList(trackerItem)),
						50,
						50
				);

				TraceabilityLevelResultDto levelResult = notInitialLevelItems.getLevelResult().get(new VersionReferenceDto(trackerItem, null));

				if(Objects.nonNull(levelResult)) {
					trackerItemsRender = levelResult.getIncomingReferences().stream()
							.map(trackerItemReference -> MessageFormat.format("{0}({1})", trackerItemReference.getName(), trackerItemReference.getId().toString()))
							.collect(Collectors.joining("<br>"));
				}
			}
		}

		if (Objects.isNull(trackerItemsRender)) {
			trackerItemsRender = "Please provide a valid tracker item id";
		}

		return MessageFormat.format("<strong>The subjects of a trackerItem </strong> </br> {0}", trackerItemsRender);
	}

	private TraceabilitySupport.LevelFilter createLevelFilter(List<TrackerItemDto> trackerItems) {
		List<VersionReferenceDto> itemReferences = trackerItems.stream()
				.map(this::createVersionReferenceKey)
				.collect(Collectors.toList());

		TraceabilitySupport.LevelFilter levelFilter = new TraceabilitySupport.LevelFilter();
		levelFilter.setPreviousLevelItems(itemReferences);
		levelFilter.setIncludeIncomingAssociations(true);
		levelFilter.setIncludeOutgoingAssociations(true);
		levelFilter.setIncludeIncomingRelations(true);
		levelFilter.setIncludeOutgoingRelations(true);
		return levelFilter;
	}

	private VersionReferenceDto createVersionReferenceKey(TrackerItemDto trackerItem) {
		return new VersionReferenceDto(TRACKER_ITEM_TYPE, trackerItem.getId(), null);
	}
}
