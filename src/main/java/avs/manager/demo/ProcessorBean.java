package avs.manager.demo;

import java.util.Random;

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


    public String hashIt(String body) {
//    	System.out.println(body.hashCode()%3);
        return String.valueOf(Math.abs(body.hashCode()%3));
    }
    public void checkSequence (Exchange exchange) {
    	Timestamp temp =(Timestamp) exchange.getIn().getHeader("CamelGooglePubsub.PublishTime");
    	if (this.timestamp ==null) {
    	this.timestamp = temp;
    	}
    	double seconds=temp.getSeconds()+temp.getNanos()*1e-9;
    	double last_message = timestamp.getSeconds()+timestamp.getNanos()*1e-9;
    	if (seconds-last_message <0) {
    		count++;
    		LOGGER.log(Level.INFO,"Total Messages out of order "+count);
    		
    	}

    		
    	}
	public String addTimestamp(String body) {
		Date date = new Date();
		return body+"\noutboundTimestampMillis:"+date.getTime();
	}
    public String getCarrier(String body) {
    	String lines[] = body.split("\\r?\\n");
        return lines[3].substring(0,2);
    } 	

}
