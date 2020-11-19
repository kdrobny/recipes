package com.example.recipes.rest;

import com.example.recipes.persistance.model.Recipe;
import com.example.recipes.persistance.repo.RecipeRepository;
import com.example.recipes.rest.dto.RecipeDto;
import com.example.recipes.rest.exception.RecipeNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ModelMapper modelMapper;

//    @GetMapping
//    public List findAll() {
//        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping("/lastName/{lastName}")
//    public List findByLastName(@PathVariable String lastName) {
//        return customerRepository.findByLastName(lastName)
//                .stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
    @GetMapping("/{id}")
    public RecipeDto findOne(@PathVariable Long id) {
        return convertToDto(recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Could not find recipe " + id)));
    }
//    public CustomerDto findOne(@PathVariable Long id) {
//        return convertToDto(customerRepository.findById(id)
//                .orElseThrow(() -> new CustomerNotFoundException("Could not find customer " + id)));
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public CustomerDto create(@RequestBody CustomerDto customerDto) {
//        Customer customer = convertToEntity(customerDto);
//        Customer customerCreated = customerRepository.save(customer);
//        return convertToDto(customerCreated);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        customerRepository.findById(id)
//                .orElseThrow(() -> new CustomerNotFoundException("Could not find customer " + id));
//        customerRepository.deleteById(id);
//    }
//
//    @PutMapping("/{id}")
//    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable Long id) {
//        if (customerDto.getId() != id) {
//            throw new CustomerIdMismatchException();
//        }
//
//        Customer customer = customerRepository.findById(id)
//                .orElseThrow(() -> new CustomerNotFoundException("Could not find customer " + id));
//
//        customer = updateEntity(customer, customerDto);
//
//        Customer customerUpdated = customerRepository.save(customer);
//
//        return convertToDto(customerUpdated);
//    }
//
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
