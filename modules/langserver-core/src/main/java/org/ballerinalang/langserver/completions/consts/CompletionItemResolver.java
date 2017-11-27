/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/
package org.ballerinalang.langserver.completions.consts;

import org.ballerinalang.langserver.completions.resolvers.AbstractItemResolver;
import org.ballerinalang.langserver.completions.resolvers.AnnotationAttachmentContextResolver;
import org.ballerinalang.langserver.completions.resolvers.AnnotationAttachmentResolver;
import org.ballerinalang.langserver.completions.resolvers.BLangStructContextResolver;
import org.ballerinalang.langserver.completions.resolvers.BlockStatementContextResolver;
import org.ballerinalang.langserver.completions.resolvers.DefaultResolver;
import org.ballerinalang.langserver.completions.resolvers.PackageNameContextResolver;
import org.ballerinalang.langserver.completions.resolvers.ParameterContextResolver;
import org.ballerinalang.langserver.completions.resolvers.ServiceContextResolver;
import org.ballerinalang.langserver.completions.resolvers.StatementContextResolver;
import org.ballerinalang.langserver.completions.resolvers.TopLevelResolver;
import org.ballerinalang.langserver.completions.resolvers.parsercontext.ParserRuleAnnotationBodyContextResolver;
import org.ballerinalang.langserver.completions.resolvers.parsercontext.ParserRuleAssignmentStatementContextResolver;
import org.ballerinalang.langserver.completions.resolvers.parsercontext.ParserRuleAttachmentPointContextResolver;
import org.ballerinalang.langserver.completions.resolvers.parsercontext.ParserRuleConstantDefinitionContextResolver;
import org.ballerinalang.langserver.completions.resolvers.parsercontext.ParserRuleGlobalVariableDefinitionContextResolver;
import org.ballerinalang.langserver.completions.resolvers.parsercontext.ParserRuleStatementContextResolver;
import org.ballerinalang.langserver.completions.resolvers.parsercontext.ParserRuleTriggerWorkerContext;
import org.ballerinalang.langserver.completions.resolvers.parsercontext.ParserRuleTypeNameContextResolver;
import org.ballerinalang.langserver.completions.resolvers.parsercontext.ParserRuleVariableDefinitionStatementContextResolver;
import org.ballerinalang.langserver.completions.resolvers.parsercontext.ParserRuleWorkerReplyContext;
import org.ballerinalang.model.AnnotationAttachment;
import org.wso2.ballerinalang.compiler.parser.antlr4.BallerinaParser;
import org.wso2.ballerinalang.compiler.tree.BLangService;
import org.wso2.ballerinalang.compiler.tree.BLangStruct;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum for the completion item resolvers.
 */
public enum CompletionItemResolver {

    DEFAULT(DefaultResolver.class,
            new DefaultResolver()),
    STATEMENT_CONTEXT(StatementContextResolver.class,
            new StatementContextResolver()),
    TOP_LEVEL_CONTEXT(null,
            new TopLevelResolver()),
    PACKAGE_NAME_CONTEXT(BallerinaParser.PackageNameContext.class,
            new PackageNameContextResolver()),
    ANNOTATION_ATTACHMENT_CONTEXT(BallerinaParser.AnnotationAttachmentContext.class,
            new AnnotationAttachmentContextResolver()),
    IMPORT_DECLARATION_CONTEXT(BallerinaParser.ImportDeclarationContext.class,
            new PackageNameContextResolver()),
    PARAMETER_CONTEXT(BallerinaParser.ParameterContext.class,
            new ParameterContextResolver()),
    BLOCK_STATEMENT_CONTEXT(BlockStatementContextResolver.class,
            new BlockStatementContextResolver()),
    ANNOTATION_ATTACHMENT(AnnotationAttachment.class,
            new AnnotationAttachmentResolver()),
    STRUCT_CONTEXT(BLangStruct.class,
            new BLangStructContextResolver()),
    SERVICE_CONTEXT(BLangService.class,
            new ServiceContextResolver()),

    PARSER_RULE_STATEMENT_CONTEXT(BallerinaParser.StatementContext.class,
            new ParserRuleStatementContextResolver()),
    PARSER_RULE_VAR_DEF_STMT_CONTEXT(BallerinaParser.VariableDefinitionStatementContext.class,
            new ParserRuleVariableDefinitionStatementContextResolver()),
    PARSER_RULE_TRIGGER_WORKER_CONTEXT(BallerinaParser.TriggerWorkerContext.class,
            new ParserRuleTriggerWorkerContext()),
    PARSER_RULE_WORKER_REPLY_CONTEXT(BallerinaParser.WorkerReplyContext.class,
            new ParserRuleWorkerReplyContext()),
    PARSER_RULE_TYPE_NAME_CONTEXT(BallerinaParser.TypeNameContext.class,
            new ParserRuleTypeNameContextResolver()),
    PARSER_RULE_CONST_DEF_CONTEXT(BallerinaParser.ConstantDefinitionContext.class,
            new ParserRuleConstantDefinitionContextResolver()),
    PARSER_RULE_GLOBAL_VAR_DEF_CONTEXT(BallerinaParser.GlobalVariableDefinitionContext.class,
            new ParserRuleGlobalVariableDefinitionContextResolver()),
    PARSER_RULE_ANNOTATION_BODY_CONTEXT(BallerinaParser.AnnotationBodyContext.class,
            new ParserRuleAnnotationBodyContextResolver()),
    PARSER_RULE_ATTACHMENT_POINT_CONTEXT(BallerinaParser.AttachmentPointContext.class,
            new ParserRuleAttachmentPointContextResolver()),
    PARSER_RULE_ASSIGN_STMT_CONTEXT(BallerinaParser.AssignmentStatementContext.class,
            new ParserRuleAssignmentStatementContextResolver());

    private final Class context;
    private final AbstractItemResolver completionItemResolver;
    private static final Map<Class, AbstractItemResolver> resolverMap =
            Collections.unmodifiableMap(initializeMapping());

    CompletionItemResolver(Class context, AbstractItemResolver itemResolver) {
        this.context = context;
        this.completionItemResolver = itemResolver;
    }

    private Class getContext() {
        return context;
    }

    private AbstractItemResolver getCompletionItemResolver() {
        return completionItemResolver;
    }

    /**
     * Get the resolver by the class.
     * @param context - context class to extract the relevant resolver
     * @return {@link AbstractItemResolver} - Item resolver for the given context
     */
    public static AbstractItemResolver getResolverByClass(Class context) {
        return resolverMap.get(context);
    }

    private static Map<Class, AbstractItemResolver> initializeMapping() {
        Map<Class, AbstractItemResolver> map = new HashMap<>();
        for (CompletionItemResolver resolver : CompletionItemResolver.values()) {
            map.put(resolver.getContext(), resolver.getCompletionItemResolver());
        }
        return map;
    }
}
