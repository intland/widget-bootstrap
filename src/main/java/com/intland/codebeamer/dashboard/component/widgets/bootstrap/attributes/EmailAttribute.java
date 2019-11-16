package com.intland.codebeamer.dashboard.component.widgets.bootstrap.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intland.codebeamer.dashboard.component.exception.ValidationErrorType;
import com.intland.codebeamer.dashboard.component.exception.WidgetFieldValidationException;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.StringAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;

public class EmailAttribute extends StringAttribute {
    /**
     * @param value
     * @param required
     * @param validationRequired
     */
    public EmailAttribute(@JsonProperty("value") final String value,
                          @JsonProperty("required") final boolean required,
                          @JsonProperty("validationRequired") final boolean validationRequired
    ) {
        super(value, required, validationRequired);
    }

    private EmailAttribute(WidgetAttribute<String> attribute) {
        super(attribute.getValue(), attribute.isRequired(), attribute.isValidationRequired());
    }

    @Override
    public WidgetAttribute<String> convert(String newEmailValue) {
        if (!StringUtils.isEmpty(newEmailValue) && isValid(newEmailValue)) {
            return new EmailAttribute(super.convert(newEmailValue));
        }
        throw new WidgetFieldValidationException(ValidationErrorType.UNKNOWN, Collections.singletonList("Error"));
    }

    private boolean isValid(String email) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
