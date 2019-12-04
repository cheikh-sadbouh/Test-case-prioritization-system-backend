package my.upm.emma.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@SpringBootApplication(scanBasePackages = "my.upm.emma.testing")
public class TestingApplication  implements CommandLineRunner {
@Resource
StorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(TestingApplication.class, args);

    }
    @Override
    public void run(String... arg) throws Exception {
       storageService.deleteAll();
        storageService.init();

    }




}
