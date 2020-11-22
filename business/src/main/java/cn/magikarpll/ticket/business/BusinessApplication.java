package cn.magikarpll.ticket.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableScheduling
public class BusinessApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(BusinessApplication.class);
    }
}
