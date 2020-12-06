import { Pipe, PipeTransform } from '@angular/core';
import {Recipe} from "../model/recipe";

@Pipe({
  name: 'recipeFilterDifficulty'
})
export class RecipeFilterDifficultyPipe implements PipeTransform {

  transform(recipeList: Recipe[], difficultyDesc: string): Recipe[] {
    if (!difficultyDesc || !recipeList) {
      return recipeList;
    }
    else {
      return recipeList.filter(
        el => el.difficultyDescRO === difficultyDesc
      );
    }
  }

}
