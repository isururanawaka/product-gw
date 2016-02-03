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


import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.Constants;

import java.util.Map;


/**
 * A class represent the Engine Callback
 */
public class HeaderBasedRouterCallBack implements CarbonCallback {


    private CarbonCallback responseCallback;

    private CarbonMessage request;

    public HeaderBasedRouterCallBack(CarbonMessage request, CarbonCallback responseCallback) {
        this.responseCallback = responseCallback;
        this.request = request;
    }

    @Override
    public void done(CarbonMessage responseCmsg) {
        if (responseCmsg != null) {
            Map<String, String> transportHeaders = responseCmsg.getHeaders();
            if (transportHeaders != null) {
                responseCmsg.setProperty(Constants.SRC_HNDLR,
                                         request.getProperty(Constants.SRC_HNDLR));
                responseCmsg.setProperty(Constants.DISRUPTOR,
                                         request.getProperty(Constants.DISRUPTOR));
                responseCmsg.setProperty(Constants.CHNL_HNDLR_CTX,
                                         request.getProperty(Constants.CHNL_HNDLR_CTX));
                responseCallback.done(responseCmsg);
            }

        }
    }


}
