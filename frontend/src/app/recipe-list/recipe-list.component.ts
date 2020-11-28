import {Component, OnInit} from '@angular/core';
import {Recipe} from "../model/recipe";
import {RecipeService} from "../service/recipe.service";
import {Enum} from "../model/enum";
import {EnumService} from "../service/enum.service";

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {

  recipes: Recipe[];
  recipeCategoryList: Enum[];

  constructor(private recipeService: RecipeService, private enumService: EnumService) {
  }

  ngOnInit() {
    this.recipeService.findAll().subscribe(data => {
      this.recipes = data;
    });
    this.recipeCategoryList = this.enumService.getRecipeCategory();
  }

}
