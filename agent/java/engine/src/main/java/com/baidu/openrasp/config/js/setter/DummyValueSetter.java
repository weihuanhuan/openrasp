package com.baidu.openrasp.config.js.setter;

import com.google.gson.JsonElement;
import org.mozilla.javascript.ast.AstNode;

public class DummyValueSetter implements ValueSetter {

    @Override
    public void setValue(JsonElement jsonElement, AstNode node) {
    }

}
