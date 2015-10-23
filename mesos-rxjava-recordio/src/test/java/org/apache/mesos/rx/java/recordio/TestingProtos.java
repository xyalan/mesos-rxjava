/*
 *    Copyright (C) 2015 Apache Software Foundation (ASF)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mesos.rx.java.recordio;

import org.apache.mesos.v1.scheduler.Protos;
import org.jetbrains.annotations.NotNull;

import static com.google.common.collect.Lists.newArrayList;

public final class TestingProtos {
    @NotNull
    static final Protos.Event HEARTBEAT = Protos.Event.newBuilder()
        .setType(Protos.Event.Type.HEARTBEAT)
        .build();
    @NotNull
    static final Protos.Event SUBSCRIBED = subscribed("20151008-161417-16777343-5050-20532-0008", 15);
    @NotNull
    static final Protos.Event OFFER = resourceOffer("host1", "offer", "slave", "frw1", 8d, 8192, 8192);

    @NotNull
    public static Protos.Event subscribed(@NotNull final String frameworkId, final int heartbeatIntervalSeconds) {
        return Protos.Event.newBuilder()
            .setType(Protos.Event.Type.SUBSCRIBED)
            .setSubscribed(
                Protos.Event.Subscribed.newBuilder()
                    .setFrameworkId(org.apache.mesos.v1.Protos.FrameworkID.newBuilder()
                            .setValue(frameworkId)
                    )
                    .setHeartbeatIntervalSeconds(heartbeatIntervalSeconds)
            )
            .build();
    }

    @NotNull
    public static Protos.Event resourceOffer(
        @NotNull final String hostname,
        @NotNull final String offerId,
        @NotNull final String agentId,
        @NotNull final String frameworkId,
        final double cpus,
        final long mem,
        final long disk
    ) {
        return Protos.Event.newBuilder()
            .setType(Protos.Event.Type.OFFERS)
            .setOffers(
                Protos.Event.Offers.newBuilder()
                .addAllOffers(newArrayList(
                    org.apache.mesos.v1.Protos.Offer.newBuilder()
                        .setHostname(hostname)
                        .setId(org.apache.mesos.v1.Protos.OfferID.newBuilder().setValue(offerId))
                        .setAgentId(org.apache.mesos.v1.Protos.AgentID.newBuilder().setValue(agentId))
                        .setFrameworkId(org.apache.mesos.v1.Protos.FrameworkID.newBuilder().setValue(frameworkId))
                        .addResources(org.apache.mesos.v1.Protos.Resource.newBuilder()
                            .setName("cpus")
                            .setRole("*")
                            .setType(org.apache.mesos.v1.Protos.Value.Type.SCALAR)
                            .setScalar(org.apache.mesos.v1.Protos.Value.Scalar.newBuilder().setValue(cpus)))
                        .addResources(org.apache.mesos.v1.Protos.Resource.newBuilder()
                            .setName("mem")
                            .setRole("*")
                            .setType(org.apache.mesos.v1.Protos.Value.Type.SCALAR)
                            .setScalar(org.apache.mesos.v1.Protos.Value.Scalar.newBuilder().setValue(mem)))
                        .addResources(org.apache.mesos.v1.Protos.Resource.newBuilder()
                            .setName("disk")
                            .setRole("*")
                            .setType(org.apache.mesos.v1.Protos.Value.Type.SCALAR)
                            .setScalar(org.apache.mesos.v1.Protos.Value.Scalar.newBuilder().setValue(disk)))
                        .build()
                ))
            )
            .build();
    }
}