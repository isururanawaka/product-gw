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

package org.wso2.carbon.gateway.internal.mediation.engine.mediators.headerrouter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.gateway.internal.mediation.engine.mediators.Mediator;
import org.wso2.carbon.gateway.internal.mediation.engine.sequence.FlowController;
import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class that responsible for perform the header based routing
 */
public class HeaderBasedRouter implements FlowController {

    private Map<String, HeaderBasedRouterConfig> headerBasedRouterConfigMap;

    private Mediator defaultMediator;

    private static final Logger log = LoggerFactory.getLogger(HeaderBasedRouter.class);


    public HeaderBasedRouter() {
        headerBasedRouterConfigMap = new ConcurrentHashMap<>();
    }


    @Override
    public CarbonCallback receive(CarbonMessage carbonMessage, CarbonCallback carbonCallback)
               throws Exception {

        boolean isdefaultRoute = true;

        CarbonCallback callback = null;

        Map<String, String> map = carbonMessage.getHeaders();
        for (Map.Entry entry : map.entrySet()) {
            String value = entry.getKey() + ":" + entry.getValue();
            HeaderBasedRouterConfig headerBasedRouterConfig = headerBasedRouterConfigMap.get(value);
            if (headerBasedRouterConfig != null) {
                isdefaultRoute = false;
                callback = new HeaderBasedRouterCallBack(carbonMessage, carbonCallback);
                processRequest(carbonMessage, headerBasedRouterConfig.getMediator(), callback);
            }

        }


        if (isdefaultRoute) {
            callback = new HeaderBasedRouterCallBack(carbonMessage, carbonCallback);
            processRequest(carbonMessage, defaultMediator, callback);
        }

        return new HeaderBasedRouterCallBack(carbonMessage, carbonCallback);
    }

    public void setDefaultMediator(Mediator defaultMediator) {
        this.defaultMediator = defaultMediator;
    }

    public void addRouteForHeader(HeaderBasedRouterConfig headerBasedRouterConfig) {
        headerBasedRouterConfigMap.put(headerBasedRouterConfig.toString(), headerBasedRouterConfig);
    }

    private void processRequest(CarbonMessage carbonMessage, Mediator mediator, CarbonCallback carbonCallback)
               throws Exception {
        mediator.receive(carbonMessage, carbonCallback);

    }

}
