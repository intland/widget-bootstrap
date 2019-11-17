package com.intland.codebeamer.workflow.actions;

import com.intland.codebeamer.manager.util.ChangeVetoedException;
import com.intland.codebeamer.manager.workflow.ActionCall;
import com.intland.codebeamer.manager.workflow.ActionParam;
import com.intland.codebeamer.manager.workflow.ActionParam.Type;
import com.intland.codebeamer.manager.workflow.WorkflowAction;
import com.intland.codebeamer.manager.workflow.WorkflowPhase;
import com.intland.codebeamer.persistence.dto.TrackerItemDto;
import com.intland.codebeamer.persistence.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;

@Component
@WorkflowAction(value = "assigneeChecker")
public class AssigneeChecker {

    @ActionCall(WorkflowPhase.After)
    public void checkAssignee(TrackerItemDto subject,
                              @ActionParam(value = "assignedTo", required = true, type = Type.user) UserDto user
                              ) throws ChangeVetoedException {
        if (CollectionUtils.isEmpty(subject.getAssignedTo()) || !subject.getAssignedTo().contains(user)) {
            throw new ChangeVetoedException(MessageFormat.format("Tracker item must be assigned to {0}.", user.getName()));
        }
    }
}
