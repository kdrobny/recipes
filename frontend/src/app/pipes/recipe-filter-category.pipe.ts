import { Pipe, PipeTransform } from '@angular/core';
import {Recipe} from "../model/recipe";

@Pipe({
  name: 'recipeFilterCategory'
})
export class RecipeFilterCategoryPipe implements PipeTransform {

  transform(recipeList: Recipe[], categoryDesc: string): Recipe[] {
    if (!categoryDesc || !recipeList) {
      return recipeList;
    }
    else {
      return recipeList.filter(
        el => el.categoryDescRO === categoryDesc
      );
    }
  }

}
