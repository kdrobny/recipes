import {Component} from '@angular/core';
import {Recipe} from "../model/recipe";
import {ActivatedRoute, Router} from "@angular/router";
import {RecipeService} from "../service/recipe.service";
import {EnumService} from "../service/enum.service";

@Component({
  selector: 'app-recipe-form',
  templateUrl: './recipe-form.component.html',
  styleUrls: ['./recipe-form.component.css']
})
export class RecipeFormComponent {

  recipe: Recipe;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private recipeService: RecipeService,
    public enumService: EnumService) {

    this.recipe = new Recipe();
    const param = this.route.snapshot.paramMap.get('id');
    if (param) {
      const id = +param;
      this.getRecipe(id);
    }
  }

  getRecipe(id: number) {
    this.recipeService.getRecipe(id).subscribe(
      recipe => this.recipe = recipe
    );
  }

  onSubmit() {
    this.recipeService.saveRecipe(this.recipe).subscribe(result => this.gotoRecipeList());
  }

  gotoRecipeList() {
    this.router.navigate(['/recipes']);
  }

}
