import { Pipe, PipeTransform } from '@angular/core';
import {Recipe} from "../model/recipe";

@Pipe({
  name: 'recipeFilterDirections'
})
export class RecipeFilterDirectionsPipe implements PipeTransform {

  transform(recipeList: Recipe[], directions: string): Recipe[] {
    if (!directions || !recipeList) {
      return recipeList;
    }
    else {
      directions = directions.toLocaleLowerCase();
      return recipeList.filter(
        el => el.directions.toLocaleLowerCase().indexOf(directions) !== -1
      );
    }
  }

}
