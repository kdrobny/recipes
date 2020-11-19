package com.example.recipes;

import com.example.recipes.persistance.model.Recipe;
import com.example.recipes.persistance.repo.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecipesApplication {
    private static final Logger log = LoggerFactory.getLogger(RecipesApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RecipesApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner demo(RecipeRepository rep) {
        return (args) -> {
            rep.save(new Recipe("test1", "iiii", "dddd", 1L, 1L, 1L));
            rep.save(new Recipe("test2", "iiii", "dddd", 2L, 2L, 2L));

            log.info("Recipes created");
        };
    }

}
