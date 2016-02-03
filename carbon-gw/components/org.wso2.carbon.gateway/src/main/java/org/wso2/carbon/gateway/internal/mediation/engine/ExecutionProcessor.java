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

package org.wso2.carbon.gateway.internal.mediation.engine;

import org.wso2.carbon.gateway.internal.mediation.engine.configuration.EngineConfiguration;
import org.wso2.carbon.gateway.internal.mediation.engine.mediators.Mediator;
import org.wso2.carbon.gateway.internal.mediation.engine.sequence.Sequence;
import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.CarbonMessageProcessor;
import org.wso2.carbon.messaging.TransportSender;

import java.util.List;
import java.util.Map;

/**
 * A class that executes the engine
 */
public class ExecutionProcessor implements CarbonMessageProcessor {

    private EngineConfiguration engineConfiguration;

    public ExecutionProcessor() {
        engineConfiguration = EngineConfiguration.getInstance();
    }


    @Override
    public boolean receive(CarbonMessage carbonMessage, CarbonCallback carbonCallback) throws Exception {
        if (engineConfiguration != null) {
            Map<String, Sequence> sequenceList = engineConfiguration.getSequenceMap();

            for (Map.Entry entry : sequenceList.entrySet()) {
                List<Mediator> mediatorList = ((Sequence) entry.getValue()).getMediators();
                CarbonCallback temp = carbonCallback;
                for (Mediator mediator : mediatorList) {
                    temp = mediator.receive(carbonMessage, temp);
//                if (temp == null) {
//                    //Need  to throw an error
//                }
                }
            }

            // GatewayContextHolder.getInstance().getSender().send(carbonMessage, temp);
        }

        return true;
    }


    @Override
    public void setTransportSender(TransportSender transportSender) {

    }


    @Override
    public String getId() {
        return null;
    }
}
