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

package org.wso2.carbon.gateway.internal.mediation.engine.mediators.headerrouter.builder;

import org.wso2.carbon.gateway.internal.mediation.engine.mediators.Mediator;
import org.wso2.carbon.gateway.internal.mediation.engine.mediators.headerrouter.HeaderBasedRouter;
import org.wso2.carbon.gateway.internal.mediation.engine.mediators.headerrouter.HeaderBasedRouterConfig;

/**
 * The Builder class for HeaderBasedRouter
 */
public class HeaderBasedRouterBuilder {


    private HeaderBasedRouter headerBasedRouter;


    public static HeaderBasedRouterBuilder headerbasedrouter() {
        return new HeaderBasedRouterBuilder();
    }

    private HeaderBasedRouterBuilder() {
        headerBasedRouter = new HeaderBasedRouter();
    }

    public HeaderValueBuilder ifHeader(String header) {

        return new HeaderValueBuilder(header, this, headerBasedRouter);
    }

    public HeaderBasedRouter elseDefault(Mediator defaultRoute) {
        headerBasedRouter.setDefaultMediator(defaultRoute);
        return headerBasedRouter;
    }

    /**
     * Util class used by  HeaderBasedRouterBuilder
     */
    public static class HeaderValueBuilder {

        private String header;
        private HeaderBasedRouterBuilder headerBasedRouterBuilder;
        private HeaderBasedRouter headerBasedRouter;


        public HeaderValueBuilder(String header, HeaderBasedRouterBuilder headerBasedRouterBuilder,
                                  HeaderBasedRouter headerBasedRouter) {
            this.header = header;
            this.headerBasedRouterBuilder = headerBasedRouterBuilder;
            this.headerBasedRouter = headerBasedRouter;
        }


        public RouteBuilder equals(String value) {
            return new RouteBuilder(header, value, headerBasedRouterBuilder, headerBasedRouter);
        }


    }

    /**
     * Util class used by  HeaderBasedRouterBuilder
     */
    public static class RouteBuilder {

        private String header;
        private String value;
        private HeaderBasedRouterBuilder headerValueBuilder;
        private HeaderBasedRouter headerBasedRouter;


        public RouteBuilder(String header, String value, HeaderBasedRouterBuilder headerBasedRouterBuilder,
                            HeaderBasedRouter headerBasedRouter) {
            this.header = header;
            this.value = value;
            this.headerValueBuilder = headerBasedRouterBuilder;
            this.headerBasedRouter = headerBasedRouter;
        }


        public HeaderBasedRouterBuilder then(Mediator mediator) {
            HeaderBasedRouterConfig headerBasedRouterConfig = new HeaderBasedRouterConfig(header, value, mediator);
            this.headerBasedRouter.addRouteForHeader(headerBasedRouterConfig);
            return headerValueBuilder;
        }


    }

}

