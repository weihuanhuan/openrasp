package com.baidu.openrasp.config.js.setter;

import com.google.gson.JsonElement;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.NumberLiteral;

public class NumberValueSetter implements ValueSetter {

    @Override
    public void setValue(JsonElement jsonElement, AstNode node) {
        Number asNumber = jsonElement.getAsNumber();
        NumberLiteral numberLiteral = (NumberLiteral) node;
        numberLiteral.setNumber(asNumber.doubleValue());
    }

}
