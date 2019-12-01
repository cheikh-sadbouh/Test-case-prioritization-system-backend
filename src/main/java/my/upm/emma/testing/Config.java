package my.upm.emma.testing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public  static  responsEntity getbean(){
        return  new responsEntity();
    }
}
