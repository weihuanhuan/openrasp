package com.baidu.openrasp.config.js.setter;

import com.google.gson.JsonElement;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.StringLiteral;

public class StringValueSetter implements ValueSetter {

    @Override
    public void setValue(JsonElement jsonElement, AstNode node) {
        String asString = jsonElement.getAsString();
        StringLiteral stringLiteral = (StringLiteral) node;
        stringLiteral.setValue(asString);
    }

}
