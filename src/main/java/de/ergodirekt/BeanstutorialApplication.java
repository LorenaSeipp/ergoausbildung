package de.ergodirekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BeanstutorialApplication {

    public interface SaySomethingService {
        public String saySomething();
    }

    @Component
    @Qualifier("sayHelloService")
    public class SayHelloService implements SaySomethingService {
        @Override
        public String saySomething(){
            return "Hello World";
        }
    }

    @Configuration
    public class SaySomethingConfiguration {
        @Bean
        @Primary
        public SaySomethingConfigurableService saySomethingConfigurableService(){
            SaySomethingConfigurableService saySomethingConfigurableService = new SaySomethingConfigurableService();
            saySomethingConfigurableService.setWhatToSay("Goodbye");
            return saySomethingConfigurableService;
        }

    }

    public class SaySomethingConfigurableService implements SaySomethingService {
        private String whatToSay = "";

        @Override
        public String saySomething() {
            return whatToSay;
        }
        public void setWhatToSay (String whatToSay){
            this.whatToSay = whatToSay;
        }
    }

    @RestController
    public class SaySomethingController {
        @Autowired
        @Qualifier("sayHelloService")
        SaySomethingService saySomethingService;

        @GetMapping ("/")
        public String home(){
            return saySomethingService.saySomething();
        }
    }
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(BeanstutorialApplication.class, args);

        // SaySomethingService saySomethingService = applicationContext.getBean(SaySomethingService.class);
        // System.out.println(saySomethingService.saySomething());
    }
}