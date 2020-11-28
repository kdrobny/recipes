import {Enum} from "./enum";

export class RecipeCategoryPanel {
  desc : string;
  opened : boolean;

  constructor(recipe: Enum) {
    this.desc = recipe.desc;
    this.opened = true;
  }

}
