import { Pipe, PipeTransform } from '@angular/core';
import {Recipe} from "../model/recipe";
import {RecipeCategory} from "../model/recipe-category";

@Pipe({
  name: 'recipeCategoryList'
})
export class RecipeCategoryListPipe implements PipeTransform {

  // get distinct category list
  transform(recipeList: Recipe[]): RecipeCategory[] {
    if (!recipeList) {
      return null;
    }
    else {
      return recipeList.map(el => new RecipeCategory(el))
                .filter((rc, i, srcArr) =>
                    srcArr.findIndex(el => el.idCategory === rc.idCategory) === i);
    }
  }

}
