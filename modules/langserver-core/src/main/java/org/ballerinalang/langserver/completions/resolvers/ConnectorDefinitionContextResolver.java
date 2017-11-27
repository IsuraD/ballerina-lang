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

package org.ballerinalang.langserver.completions.resolvers;

import org.ballerinalang.langserver.completions.SuggestionsFilterDataModel;
import org.ballerinalang.langserver.completions.consts.ItemResolverConstants;
import org.ballerinalang.langserver.completions.consts.Priority;
import org.ballerinalang.langserver.completions.consts.Snippet;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.InsertTextFormat;

import java.util.ArrayList;

/**
 * Resolver for Connector definition context.
 */
public class ConnectorDefinitionContextResolver extends AbstractItemResolver {
    @Override
    public ArrayList<CompletionItem> resolveItems(SuggestionsFilterDataModel dataModel) {

        ArrayList<CompletionItem> completionItems = new ArrayList<>();

        if (!this.isAnnotationContext(dataModel)) {
            CompletionItem connectorActionItem = new CompletionItem();
            connectorActionItem.setLabel(ItemResolverConstants.ACTION);
            connectorActionItem.setInsertText(Snippet.CONNECTOR_ACTION.toString());
            connectorActionItem.setInsertTextFormat(InsertTextFormat.Snippet);
            connectorActionItem.setDetail(ItemResolverConstants.ACTION_TYPE);
            connectorActionItem.setSortText(Priority.PRIORITY4.name());
            completionItems.add(connectorActionItem);
        }

        return completionItems;
    }
}
