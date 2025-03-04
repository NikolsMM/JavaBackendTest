package com.nikols;

import com.nikols.models.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication //Esto [|] y [2] es lo que hace que mi aplicación sea una aplicación de Spring
@RestController //Esto hace que to do lo que este anotado con @GetMapping, @Post etc sean cosas el usuario pueda acceder
@RequestMapping("api/v1/customers") //Como está al root level, cada vez que alguien vaya a este link, se llama al GetMapping y recibirá un empty List por [3]
public class Main {

    private final CustomerRepository customerRepository;
    // private -> solo se puede acceder dentro de la clase Main
    // final -> si se asigna un valor a customerRepository, no se puede cambiar, i.e., no se puede reasignar a otro CustomerRepository
    // aquí instanciamos CustomerRepository

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    //Recuerda que aquí lo que ocurre es que Main recibe un objeto CustomerRepository como PARÁMETRO
    //Luego asigna a nuestro CAMPO customerRepository de la clase el parámetro.
    //Esto lo hacemos porque Spring Boot necesita INYECTAR la instancia de CustomerRepository en Main (equivalente a hacer un @Autowired).
    //INYECTAR -> El framework crea automáticamente un objeto y lo proporciona a las partes del código que lo necesitan.

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args); // esto es [2]
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();//[3]
        //Podemos usar el método findAll porque viene de customerRepository que es un CustomerRepository que extiende la interfaz JpaRepo, con sus metodos.
    }

    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){

    }

    @PostMapping //Post para crear
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @DeleteMapping("/{id}") // Para pasarle variables a traves de la URL.
    public void deleteCustomer(@PathVariable("id") Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("/{id}") //Put para modificar
    public void updateCustomer(@RequestBody NewCustomerRequest request, @PathVariable("id") Integer id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("The customer with ID:" + id + "was not found.")));
        customer.setId(id);
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }
}
/*
    @GetMapping("/") //Esto es lo que necesitamos para los post puts etc.
    public GreetResponse greet(){
        GreetResponse response = new GreetResponse(
                "Hello",
                12,
                "Nikols",
                new Person("holaa", List.of("comida", "comida!!")));
        return response;
    }

    record GreetResponse(String greet, int edad, String name, Person uwuEwez ){}

    record Person(String greet, List<String> comida){}

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