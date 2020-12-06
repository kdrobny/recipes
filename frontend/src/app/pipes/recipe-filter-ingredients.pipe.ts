import { Pipe, PipeTransform } from '@angular/core';
import {Recipe} from "../model/recipe";

@Pipe({
  name: 'recipeFilterIngredients'
})
export class RecipeFilterIngredientsPipe implements PipeTransform {

  transform(recipeList: Recipe[], ingredients: string): Recipe[] {
    if (!ingredients || !recipeList) {
      return recipeList;
    }
    else {
      ingredients = ingredients.toLocaleLowerCase();
      return recipeList.filter(
        el => el.ingredients.toLocaleLowerCase().indexOf(ingredients) !== -1
      );
    }
  }

}
