package com.baidu.openrasp.config.js.setter;

import com.google.gson.JsonElement;
import org.mozilla.javascript.ast.AstNode;

public interface ValueSetter {

    void setValue(JsonElement jsonElement, AstNode node);

}
