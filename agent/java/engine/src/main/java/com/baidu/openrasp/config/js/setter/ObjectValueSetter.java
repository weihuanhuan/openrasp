package com.baidu.openrasp.config.js.setter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.ObjectLiteral;
import org.mozilla.javascript.ast.ObjectProperty;

public class ObjectValueSetter implements ValueSetter {

    @Override
    public void setValue(JsonElement jsonElement, AstNode node) {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        ObjectLiteral objectLiteral = (ObjectLiteral) node;

        for (ObjectProperty element : objectLiteral.getElements()) {
            Name left = (Name) element.getLeft();
            AstNode right = element.getRight();

            JsonElement jsonElementValue = asJsonObject.get(left.getIdentifier());
            ValueSetter valueSetter = ValueSetterFactory.getValueSetter(jsonElementValue);
            valueSetter.setValue(jsonElementValue, right);
        }

    }

}
