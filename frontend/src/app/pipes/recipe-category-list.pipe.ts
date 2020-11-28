import {Pipe, PipeTransform} from '@angular/core';
import {RecipeCategoryPanel} from "../model/recipe-category-panel";
import {Enum} from "../model/enum";

@Pipe({
  name: 'recipeCategoryList'
})
export class RecipeCategoryListPipe implements PipeTransform {

  // get distinct category list
  transform(recipeList: Enum[]): RecipeCategoryPanel[] {
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
