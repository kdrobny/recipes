import { Pipe, PipeTransform } from '@angular/core';
import {Recipe} from "../model/recipe";

@Pipe({
  name: 'recipeFilterPreparationTime'
})
export class RecipeFilterPreparationTimePipe implements PipeTransform {

  transform(recipeList: Recipe[], preparationTimeDesc: string): Recipe[] {
    if (!preparationTimeDesc || !recipeList) {
      return recipeList;
    }
    else {
      return recipeList.filter(
        el => el.preparationTimeDescRO === preparationTimeDesc
      );
    }
  }

}
