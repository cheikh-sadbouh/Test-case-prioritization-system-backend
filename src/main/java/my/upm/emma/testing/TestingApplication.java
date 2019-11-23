package my.upm.emma.testing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class TestingApplication  implements CommandLineRunner {
@Resource
StorageService storageService;
    public static void main(String[] args) {
        SpringApplication.run(TestingApplication.class, args);

    }
    @Override
    public void run(String... arg) throws Exception {
      // storageService.deleteAll();
        //storageService.init();
    }

}
