package avs.manager.demo;

import java.util.Random;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("ProcessorBean")
public class ProcessorBean {

    private int counter;

    public String hashIt(String body) {
//    	System.out.println(body.hashCode()%3);
        return String.valueOf(Math.abs(body.hashCode()%3));
    }
    public void checkSequence (Exchange exchange) {
    	System.out.println(exchange.getIn().getHeaders());
    	
    	
    }

}
