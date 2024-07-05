package com.example.springboot;

// import org.springframework.boot.SpringApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// import com.example.springboot.models.Product;
// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;

@EnableJpaRepositories("com.example.springboot.repository")
@SpringBootApplication
public class SpringbootApplication implements ApplicationRunner, CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootApplication.class, args);

		// Product beras = new Product("beras", 10000.0);
		// Gson gson = new Gson();
		// String jsonProduct = gson.toJson(beras);
		// System.out.println(jsonProduct);


		// Gson gsonBuilder = new GsonBuilder().create();
		// Product myProduct = gsonBuilder.fromJson(jsonProduct, Product.class);

		// System.err.println(myProduct.getPrice());
	}

    @Override
    public void run(ApplicationArguments args) throws Exception {
		System.out.println("Application started from ApplicationRunner with option names : " +
		args.getOptionNames());
    }

    @Override
    public void run(String... args) throws Exception {
       
    }

}
