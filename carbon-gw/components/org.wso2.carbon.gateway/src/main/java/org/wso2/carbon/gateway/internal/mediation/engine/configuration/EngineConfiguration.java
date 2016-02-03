/*
 * Copyright (c) 2015, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.gateway.internal.mediation.engine.configuration;


import org.wso2.carbon.gateway.internal.mediation.engine.endpoint.outbound.OutBoundEndpoint;
import org.wso2.carbon.gateway.internal.mediation.engine.sequence.Sequence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class that represents the engine configuration
 */
public class EngineConfiguration {


    private static EngineConfiguration engineConfiguration = new EngineConfiguration();

    private Map<String, Sequence> sequenceMap;
    private Map<String, OutBoundEndpoint> outBoundEndpointMap;

    private EngineConfiguration() {
        sequenceMap = new ConcurrentHashMap<>();
        outBoundEndpointMap = new ConcurrentHashMap<>();
    }

    public void addSequence(Sequence sequence) {
        sequenceMap.put(sequence.getName(), sequence);
    }


    public Map<String, Sequence> getSequenceMap() {
        return sequenceMap;
    }

    public OutBoundEndpoint getOutboundEndpoint(String key) {
        return outBoundEndpointMap.get(key);
    }

    public void addEndpoint(String key, OutBoundEndpoint outBoundEndpoint) {
        outBoundEndpointMap.put(key, outBoundEndpoint);
    }

    public static EngineConfiguration getInstance() {
        return engineConfiguration;
    }


}
