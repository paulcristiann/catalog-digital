package Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Web {
    public void run()
    {  String[] args={"--server.port=9090"};
        SpringApplication.run(Web.class,args);
    }
}
