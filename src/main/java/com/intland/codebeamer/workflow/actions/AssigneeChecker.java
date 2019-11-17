package com.intland.codebeamer.workflow.actions;

import com.intland.codebeamer.manager.util.ChangeVetoedException;
import com.intland.codebeamer.manager.workflow.ActionCall;
import com.intland.codebeamer.manager.workflow.WorkflowAction;
import com.intland.codebeamer.manager.workflow.WorkflowPhase;
import com.intland.codebeamer.persistence.dto.TrackerItemDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@WorkflowAction(value = "assigneeChecker")
public class AssigneeChecker {

    @ActionCall(WorkflowPhase.Before)
    public void checkAssignee(TrackerItemDto subject) throws ChangeVetoedException {
        if (CollectionUtils.isEmpty(subject.getAssignedTo())) {
            throw new ChangeVetoedException("Tracker item have to be assigned to someone.");
        }
    }
}
