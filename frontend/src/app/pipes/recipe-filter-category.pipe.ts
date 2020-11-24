import { Pipe, PipeTransform } from '@angular/core';
import {Recipe} from "../model/recipe";

@Pipe({
  name: 'recipeFilterCategory'
})
export class RecipeFilterCategoryPipe implements PipeTransform {

  transform(recipeList: Recipe[], idCategory: number): Recipe[] {
    if (!idCategory || !recipeList) {
      return recipeList;
    }
    else {
      return recipeList.filter(
        el => el.idCategory === idCategory
      );
    }
  }

}
