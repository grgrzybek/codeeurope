/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package grgr.ce2017.camel;

import java.util.Random;

import grgr.ce2017.model.BattleResult;
import grgr.ce2017.model.CelestialBody;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CeProcessor implements Processor {

    public static Logger LOG = LoggerFactory.getLogger(CeProcessor.class);
    private static Random RND = new Random();

    @Override
    public void process(Exchange exchange) {
        MessageContentsList content = exchange.getIn().getBody(MessageContentsList.class);
        LOG.info("Invading with {} robots", content.get(0));

        int number = (int) content.get(0);

        BattleResult r = new BattleResult();
        r.setCelestialBody(CelestialBody.random());
        r.setResult(RND.nextBoolean());
        r.setCasualties(RND.nextInt(number));

        exchange.getIn().setBody(r);
    }

}
