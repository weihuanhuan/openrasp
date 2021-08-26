package com.baidu.openrasp.config.js.setter;

import com.google.gson.JsonElement;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.KeywordLiteral;

public class BooleanValueSetter implements ValueSetter {

    @Override
    public void setValue(JsonElement jsonElement, AstNode node) {
        boolean asBoolean = jsonElement.getAsBoolean();
        KeywordLiteral keywordLiteral = (KeywordLiteral) node;
        keywordLiteral.setType(asBoolean ? Token.TRUE : Token.FALSE);
    }

}
