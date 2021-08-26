package com.baidu.openrasp.config.js;

import com.baidu.openrasp.config.js.setter.ValueSetter;
import com.baidu.openrasp.config.js.setter.ValueSetterFactory;
import com.google.gson.JsonElement;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.ObjectLiteral;
import org.mozilla.javascript.ast.VariableDeclaration;
import org.mozilla.javascript.ast.VariableInitializer;

import java.util.List;

public class AlgorithmConfigMerger implements AlgorithmConfigVisitor {

    private final String name;

    private final int type;
    private final int depth;

    private final JsonElement algorithmConfig;

    public AlgorithmConfigMerger(String name, int type, int depth, JsonElement algorithmConfig) {
        this.name = name;
        this.type = type;
        this.depth = depth;
        this.algorithmConfig = algorithmConfig;
    }

    @Override
    public boolean visit(AstNode node) {
        if (algorithmConfig == null) {
            return false;
        }

        if (!isMatchNode(node)) {
            return true;
        }

        AstNode initializer = findVariableInitializer(node);
        if (!(initializer instanceof ObjectLiteral)) {
            return true;
        }

        ValueSetter valueSetter = ValueSetterFactory.getValueSetter(algorithmConfig);
        valueSetter.setValue(algorithmConfig, initializer);
        return false;
    }

    private boolean isMatchNode(AstNode node) {
        if (node.getType() != type || node.depth() != depth) {
            return false;
        }
        return true;
    }

    private AstNode findVariableInitializer(AstNode node) {
        List<VariableInitializer> variables = ((VariableDeclaration) node).getVariables();
        if (variables == null || variables.isEmpty()) {
            return null;
        }

        for (VariableInitializer variable : variables) {
            if (variable.isDestructuring()) {
                continue;
            }

            Name target = (Name) variable.getTarget();
            if (name == null || !name.equals(target.getIdentifier())) {
                continue;
            }
            return variable.getInitializer();
        }
        return null;
    }

}
