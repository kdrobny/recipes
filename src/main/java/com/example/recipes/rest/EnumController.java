package com.example.recipes.rest;

import com.example.recipes.persistance.model.Enum;
import com.example.recipes.persistance.model.RecipeCategory;
import com.example.recipes.persistance.model.RecipeDifficulty;
import com.example.recipes.persistance.model.RecipePreparationTime;
import com.example.recipes.persistance.repo.RecipeCategoryRepository;
import com.example.recipes.persistance.repo.RecipeDifficultyRepository;
import com.example.recipes.persistance.repo.RecipePreparationTimeRepository;
import com.example.recipes.rest.dto.EnumDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/enums")
public class EnumController {

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    @Autowired
    private RecipeDifficultyRepository recipeDifficultyRepository;

    @Autowired
    private RecipePreparationTimeRepository recipePreparationTimeRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final List<String> enums = Arrays.asList(
            "RecipeCategory",
            "RecipeDifficulty",
            "RecipePreparationTime");

    @Cacheable("enums")
    @GetMapping
    public Map<String, Map<String, Object>> findAll() {
        Map<String, Map<String, Object>> scrEnums = new HashMap<>(enums.size());

        for (String elEnum : enums) {
            Map<String, Object> subResult;

            switch (elEnum) {
                case "RecipeCategory": {
                    List<RecipeCategory> dataList = new ArrayList<>(recipeCategoryRepository.findAll(Sort.by(Sort.Direction.ASC, "ord")));

                    subResult = new LinkedHashMap<>(dataList.size());

                    for (RecipeCategory el : dataList) {
                        subResult.put(String.valueOf(el.getId()), convertToDto(el));
                    }
                    scrEnums.put("RecipeCategory", subResult);
                    break;
                }
                case "RecipeDifficulty": {
                    List<RecipeDifficulty> dataList = new ArrayList<>(recipeDifficultyRepository.findAll(Sort.by(Sort.Direction.ASC, "ord")));

                    subResult = new LinkedHashMap<>(dataList.size());

                    for (RecipeDifficulty el : dataList) {
                        subResult.put(String.valueOf(el.getId()), convertToDto(el));
                    }
                    scrEnums.put("RecipeDifficulty", subResult);
                    break;
                }
                case "RecipePreparationTime": {
                    List<RecipePreparationTime> dataList = new ArrayList<>(recipePreparationTimeRepository.findAll(Sort.by(Sort.Direction.ASC, "ord")));

                    subResult = new LinkedHashMap<>(dataList.size());

                    for (RecipePreparationTime el : dataList) {
                        subResult.put(String.valueOf(el.getId()), convertToDto(el));
                    }
                    scrEnums.put("RecipePreparationTime", subResult);
                    break;
                }
            }
        }

        return scrEnums;
    }

    private EnumDto convertToDto(Enum el) {
        return modelMapper.map(el, EnumDto.class);
    }

}
