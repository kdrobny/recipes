import {Recipe} from "./recipe";

export class RecipeCategoryPanel {
  desc : string;
  opened : boolean;

  constructor(recipe: Recipe) {
    this.desc = recipe.categoryDescRO;
    this.opened = true;
  }

}
