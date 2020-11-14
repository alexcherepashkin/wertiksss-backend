package ua.alexch.demowert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoWertApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWertApplication.class, args);
    }

//    @Bean
//    CommandLineRunner init(UserRepository userRepository) {
//        return args -> {
//            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
//                User user = new User(name, name.toLowerCase() + "@domain.com");
//                userRepository.save(user);
//            });
//            userRepository.findAll().forEach(System.out::println);
//        };
//    }
}
