package com.example.recipes.rest;

import com.example.recipes.persistance.model.Recipe;
import com.example.recipes.persistance.repo.RecipeCategoryRepository;
import com.example.recipes.persistance.repo.RecipeRepository;
import com.example.recipes.rest.dto.RecipeDto;
import com.example.recipes.rest.exception.EntityIdMismatchException;
import com.example.recipes.rest.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/recipes")
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List findAll() {
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/title/{title}")
    public List findByTitle(@PathVariable String title) {
        return recipeRepository.findByTitle(title)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RecipeDto findOne(@PathVariable Long id) {
        return convertToDto(recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find recipe " + id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDto create(@RequestBody RecipeDto recipeDto) {
        Recipe recipe = convertToEntity(recipeDto);
        Recipe recipeCreated = recipeRepository.save(recipe);
        return convertToDto(recipeCreated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find customer " + id));
        recipeRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public RecipeDto updateRecipe(@RequestBody RecipeDto recipeDto, @PathVariable Long id) {
        if (id != recipeDto.getId()) {
            throw new EntityIdMismatchException();
        }

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find recipe " + id));

        recipe = updateEntity(recipe, recipeDto);

        Recipe recipeUpdated = recipeRepository.save(recipe);

        return convertToDto(recipeUpdated);
    }

    private RecipeDto convertToDto(Recipe recipe) {
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);

        return recipeDto;
    }

    private Recipe convertToEntity(RecipeDto recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);

        return recipe;
    }

    private Recipe updateEntity(Recipe recipe, RecipeDto recipeDto) {
        modelMapper.map(recipeDto, recipe);

        return recipe;
    }

}
