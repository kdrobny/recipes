import { Pipe, PipeTransform } from '@angular/core';
import {Recipe} from "../model/recipe";

@Pipe({
  name: 'recipeFilterTitle'
})
export class RecipeFilterTitlePipe implements PipeTransform {

  transform(recipeList: Recipe[], title: string): Recipe[] {
    if (!title || !recipeList) {
      return recipeList;
    }
    else {
      title = title.toLocaleLowerCase();
      return recipeList.filter(
        el => el.title.toLocaleLowerCase().indexOf(title) !== -1
      );
    }
  }

}
