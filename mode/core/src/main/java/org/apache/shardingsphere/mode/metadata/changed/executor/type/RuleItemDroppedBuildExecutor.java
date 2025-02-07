/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.mode.metadata.changed.executor.type;

import org.apache.shardingsphere.infra.metadata.version.MetaDataVersion;
import org.apache.shardingsphere.mode.metadata.changed.executor.RuleItemChangedBuildExecutor;
import org.apache.shardingsphere.mode.node.path.config.rule.RuleNodePath;
import org.apache.shardingsphere.mode.node.path.config.rule.item.NamedRuleItemNodePath;
import org.apache.shardingsphere.mode.node.path.config.rule.item.UniqueRuleItemNodePath;
import org.apache.shardingsphere.mode.spi.rule.item.drop.DropNamedRuleItem;
import org.apache.shardingsphere.mode.spi.rule.item.drop.DropRuleItem;
import org.apache.shardingsphere.mode.spi.rule.item.drop.DropUniqueRuleItem;

import java.util.Map.Entry;
import java.util.Optional;

/**
 * Rule item dropped build executor.
 */
public final class RuleItemDroppedBuildExecutor implements RuleItemChangedBuildExecutor<DropRuleItem> {
    
    @Override
    public Optional<DropRuleItem> build(final RuleNodePath ruleNodePath, final String databaseName, final MetaDataVersion metaDataVersion) {
        for (Entry<String, NamedRuleItemNodePath> entry : ruleNodePath.getNamedItems().entrySet()) {
            Optional<String> itemName = entry.getValue().findNameByItemPath(metaDataVersion.getPath());
            if (itemName.isPresent()) {
                return Optional.of(new DropNamedRuleItem(databaseName, itemName.get(), ruleNodePath.getRoot().getRuleType() + "." + entry.getKey()));
            }
        }
        for (Entry<String, UniqueRuleItemNodePath> entry : ruleNodePath.getUniqueItems().entrySet()) {
            if (entry.getValue().getVersionNodePathParser().isActiveVersionPath(metaDataVersion.getPath())) {
                return Optional.of(new DropUniqueRuleItem(databaseName, ruleNodePath.getRoot().getRuleType() + "." + entry.getKey()));
            }
        }
        return Optional.empty();
    }
}
