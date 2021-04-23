package com.sabre.avs;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.google.pubsub.GooglePubsubConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerRoute extends RouteBuilder {

    // we can use spring dependency injection
    @Autowired
    ProcessorBean processorBean;

    @Override
    public void configure() throws Exception {
        // outbound subscription
        from("{{google-pubsub-subscription}}").routeId("subscriber")
        .bean(processorBean,"addTimestamp")
        .setHeader(GooglePubsubConstants.ORDERING_KEY,method(processorBean,"getCarrier"))
        //customer topic
        .toD("{{pub-sub-prefix}}{{pub-sub-customer}}{{pub-sub-parameters}}")
        .to("log:Throughput Logger?level=INFO&groupInterval=10000&groupDelay=60000&groupActiveOnly=false");

    }

}
