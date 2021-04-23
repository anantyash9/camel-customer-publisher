package com.sabre.avs;


import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import com.google.protobuf.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

@Component("ProcessorBean")
public class ProcessorBean {
	private Timestamp timestamp;
	private int count;
	private final static Logger LOGGER = 
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
	//adds time stamp
	public String addTimestamp(String body,Exchange exchange) {
		Date date = new Date();
		Timestamp temp =(Timestamp) exchange.getIn().getHeader("CamelGooglePubsub.PublishTime");
		long publishtime=(long) ((long) temp.getSeconds()+temp.getNanos()*1e-9);
		body=body+"\navsPublisherConsumedTimeStampMillis:"+date.getTime();
		return body+"\navsSubscriberpublishedTimeStampMillis:"+publishtime;
	}
	
	//utility function for extracting carrier code from body
    public String getCarrier(String body) {
    	String lines[] = body.split("\\r?\\n");
        return lines[3].substring(0,2);
    } 	

}
