package sk.tope.car_repair_register;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@OpenAPIDefinition(servers = {@Server(url = "/api/car-repair-register", description = "Default Server URL")})
public class CarRepairRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRepairRegisterApplication.class, args);
	}

}
