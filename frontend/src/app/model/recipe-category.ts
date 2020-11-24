import {Recipe} from "./recipe";

export class RecipeCategory {
  idCategory : number;
  opened : boolean;

  constructor(recipe: Recipe) {
    this.idCategory = recipe.idCategory;
    this.opened = false;
  }

}
