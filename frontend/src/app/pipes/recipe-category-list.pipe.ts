import { Pipe, PipeTransform } from '@angular/core';
import {Recipe} from "../model/recipe";
import {RecipeCategoryPanel} from "../model/recipe-category-panel";

@Pipe({
  name: 'recipeCategoryList'
})
export class RecipeCategoryListPipe implements PipeTransform {

  // get distinct category list
  transform(recipeList: Recipe[]): RecipeCategoryPanel[] {
    if (!recipeList) {
      return null;
    }
    else {
      return recipeList.map(el => new RecipeCategoryPanel(el))
                .filter((rc, i, srcArr) =>
                    srcArr.findIndex(el => el.desc === rc.desc) === i);
    }
  }

}
