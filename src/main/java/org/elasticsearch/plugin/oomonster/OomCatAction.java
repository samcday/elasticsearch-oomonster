/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.plugin.oomonster;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.Table;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.*;
import org.elasticsearch.rest.action.cat.AbstractCatAction;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.rest.RestRequest.Method.GET;

public class OomCatAction extends AbstractCatAction {
    @Inject
    public OomCatAction(Settings settings, RestController controller,
                        Client client) {
        super(settings, controller, client);
        controller.registerHandler(GET, "/_cat/oom", this);
    }

    @Override
    protected void doRequest(final RestRequest request, final RestChannel channel, final Client client) {
        channel.sendResponse(new BytesRestResponse(RestStatus.OK, "Well, you asked for it.\n"));

        final Thread t = new Thread() {
            @Override
            public void run() {
                Loggers.getLogger(OomCatAction.class).info("I HUNGER FOR YOUR RAM");
                List<ByteBuffer> doom = new ArrayList<>();
                while (doom.size() < Integer.MAX_VALUE) {
                    doom.add(ByteBuffer.allocate(1024 * 1024));
                }
            }
        };
        t.start();
    }

    @Override
    protected void documentation(StringBuilder sb) {
        sb.append(documentation());
    }

    public static String documentation() {
        return "/_cat/oom\n";
    }

    @Override
    protected Table getTableWithHeader(RestRequest request) {
        final Table table = new Table();
        table.startHeaders();
        table.addCell("oom", "desc:yep");
        table.endHeaders();
        return table;
    }
}
