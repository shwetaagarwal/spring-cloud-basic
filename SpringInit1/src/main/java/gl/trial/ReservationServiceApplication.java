package gl.trial;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
public class ReservationServiceApplication {

    @Bean
    CommandLineRunner commandLineRunner (ReservationRepository rr){
        return strings -> {
            Stream.of("shweta","helen","ian","matt","lester")
                    .forEach( name-> rr.save(new Reservation(name)));
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}


}

@RefreshScope
@RestController
class MessageRestController {

    @Value("${message.greeting}")
    private String msg;

    @RequestMapping("/message")
    String message(){
        return msg;
    }
}


@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @RestResource( path = "by-name")
  Collection <Reservation> findByReservationName(@Param("rn")String rn) ;
}


@Entity
class Reservation {
	@Id
	@GeneratedValue
	private Long id;
	private String reservationName;



	public Reservation() {

	}

	@Override
	public String toString() {
		return "Reservation{" +
				"reservationName='" + reservationName + '\'' +
				'}';
	}

	public Reservation(String reservationName) {
		this.reservationName = reservationName;
	}

	public Long getId() {
		return id;
	}

	public String getreservationName() {
		return reservationName;
	}
}
