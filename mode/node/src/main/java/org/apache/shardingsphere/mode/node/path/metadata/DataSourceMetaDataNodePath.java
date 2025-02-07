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

package org.apache.shardingsphere.mode.node.path.metadata;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.mode.node.path.NodePathPattern;
import org.apache.shardingsphere.mode.node.path.version.VersionNodePath;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data source meta data node path.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataSourceMetaDataNodePath {
    
    private static final String ROOT_NODE = "/metadata";
    
    private static final String DATA_SOURCES_NODE = "data_sources";
    
    private static final String NODES_NODE = "nodes";
    
    private static final String UNITS_NODE = "units";
    
    /**
     * Get data source root path.
     *
     * @param databaseName database name
     * @return data source root path
     */
    public static String getDataSourceRootPath(final String databaseName) {
        return String.join("/", ROOT_NODE, databaseName, DATA_SOURCES_NODE);
    }
    
    /**
     * Get storage units path.
     *
     * @param databaseName database name
     * @return storage units path
     */
    public static String getStorageUnitsPath(final String databaseName) {
        return String.join("/", getDataSourceRootPath(databaseName), UNITS_NODE);
    }
    
    /**
     * Get storage nodes path.
     *
     * @param databaseName database name
     * @return storage nodes path
     */
    public static String getStorageNodesPath(final String databaseName) {
        return String.join("/", getDataSourceRootPath(databaseName), NODES_NODE);
    }
    
    /**
     * Get storage unit path.
     *
     * @param databaseName database name
     * @param storageUnitName storage unit name
     * @return storage unit path
     */
    public static String getStorageUnitPath(final String databaseName, final String storageUnitName) {
        return String.join("/", getStorageUnitsPath(databaseName), storageUnitName);
    }
    
    /**
     * Get storage node path.
     *
     * @param databaseName database name
     * @param storageNodeName storage node name
     * @return storage node path
     */
    public static String getStorageNodePath(final String databaseName, final String storageNodeName) {
        return String.join("/", getStorageNodesPath(databaseName), storageNodeName);
    }
    
    /**
     * Get storage unit version node path.
     *
     * @param databaseName database name
     * @param storageUnitName storage unit name
     * @return storage unit version node path
     */
    public static VersionNodePath getStorageUnitVersionNodePath(final String databaseName, final String storageUnitName) {
        return new VersionNodePath(String.join("/", getStorageUnitsPath(databaseName), storageUnitName));
    }
    
    /**
     * Get storage node version node path.
     *
     * @param databaseName database name
     * @param storageNodeName storage node name
     * @return storage node version node path
     */
    public static VersionNodePath getStorageNodeVersionNodePath(final String databaseName, final String storageNodeName) {
        return new VersionNodePath(String.join("/", getStorageNodesPath(databaseName), storageNodeName));
    }
    
    /**
     * Get storage unit version pattern unit path.
     *
     * @return storage unit version pattern node path
     */
    public static VersionNodePath getStorageUnitVersionPatternNodePath() {
        return new VersionNodePath(String.join("/", getStorageUnitsPath(NodePathPattern.IDENTIFIER), NodePathPattern.IDENTIFIER));
    }
    
    /**
     * Get storage node version pattern node path.
     *
     * @return storage node version pattern node path
     */
    public static VersionNodePath getStorageNodeVersionPatternNodePath() {
        return new VersionNodePath(String.join("/", getStorageNodesPath(NodePathPattern.IDENTIFIER), NodePathPattern.IDENTIFIER));
    }
    
    /**
     * Find storage unit name by storage unit path.
     *
     * @param path path
     * @return found storage unit name
     */
    public static Optional<String> findStorageUnitNameByStorageUnitPath(final String path) {
        Pattern pattern = Pattern.compile(getStorageUnitPath(NodePathPattern.IDENTIFIER, NodePathPattern.IDENTIFIER) + "$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(path);
        return matcher.find() ? Optional.of(matcher.group(2)) : Optional.empty();
    }
    
    /**
     * Find storage node name by storage node path.
     *
     * @param path path
     * @return found storage unit name
     */
    public static Optional<String> findStorageNodeNameByStorageNodePath(final String path) {
        Pattern pattern = Pattern.compile(getStorageNodePath(NodePathPattern.IDENTIFIER, NodePathPattern.IDENTIFIER) + "$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(path);
        return matcher.find() ? Optional.of(matcher.group(2)) : Optional.empty();
    }
    
    /**
     * Is data source root path.
     *
     * @param path path
     * @return is data source root path or not
     */
    public static boolean isDataSourceRootPath(final String path) {
        return Pattern.compile(getDataSourceRootPath(NodePathPattern.IDENTIFIER) + "?", Pattern.CASE_INSENSITIVE).matcher(path).find();
    }
}
