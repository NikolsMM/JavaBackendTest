package com.nikols;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Esto [|] y [2] es lo que hace que mi aplicación sea una aplicación de Spring
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args); // esto es [2]
    }
}


/*
    //Esto es lo mismo que hacer un record.
    {/*
    class GreetResponse {
        private final String greet;
        GreetResponse(String greet){
            this.greet = greet;
        }

        public String getGreet() {
            return greet;
        }

        @Override
        public String toString() {
            return "GreetResponse{" +
                    "greet='" + greet + '\'' +
                    '}';
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

}
*/