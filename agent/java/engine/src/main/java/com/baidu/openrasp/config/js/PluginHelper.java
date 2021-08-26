package com.baidu.openrasp.config.js;

import com.google.gson.JsonElement;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Decompiler;
import org.mozilla.javascript.IRFactory;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.UintMap;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.NodeVisitor;
import org.mozilla.javascript.ast.ScriptNode;

public class PluginHelper {

    public static String mergePlugin(String contents, JsonElement jsonElement) {
        if (jsonElement == null || contents == null || contents.isEmpty()) {
            return contents;
        }

        AstRoot astRoot = parse(contents);
        merge(astRoot, jsonElement);

        String transformer = transformer(astRoot);
        String decompile = decompile(transformer);
        return decompile;
    }

    private static void merge(AstRoot astRoot, JsonElement jsonElement) {
        NodeVisitor algorithmConfigMerger = new AlgorithmConfigMerger("algorithmConfig", Token.VAR, 1, jsonElement);
        astRoot.visitAll(algorithmConfigMerger);
    }

    private static AstRoot parse(String contents) {
        IRFactory parser = createIRFactory();

        ScriptNode scriptNode = parser.parse(contents, null, 0);
        return scriptNode.getAstRoot();
    }

    private static String transformer(AstRoot astRoot) {
        IRFactory transformer = createIRFactory();
        ScriptNode transformTree = transformer.transformTree(astRoot);
        return transformTree.getEncodedSource();
    }

    private static String decompile(String encodedSource) {
        UintMap uintMap = createUintMap();
        return Decompiler.decompile(encodedSource, Decompiler.TO_SOURCE_FLAG, uintMap);
    }

    private static CompilerEnvirons createCompilerEnvirons() {
        CompilerEnvirons environs = new CompilerEnvirons();
        environs.setLanguageVersion(Context.VERSION_ES6);
        environs.setRecordingComments(true);
        environs.setRecordingLocalJsDocComments(true);
        environs.setAllowSharpComments(true);
        environs.setGeneratingSource(true);
        environs.setOptimizationLevel(-1);
        return environs;
    }

    private static IRFactory createIRFactory() {
        CompilerEnvirons compilerEnvirons = createCompilerEnvirons();
        return new IRFactory(compilerEnvirons, compilerEnvirons.getErrorReporter());
    }

    private static UintMap createUintMap() {
        UintMap uintMap = new UintMap();
        uintMap.put(Decompiler.INITIAL_INDENT_PROP, 0);
        uintMap.put(Decompiler.INDENT_GAP_PROP, 4);
        return uintMap;
    }

}
