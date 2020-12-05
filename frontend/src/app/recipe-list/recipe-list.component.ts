import {Component, OnInit, TemplateRef} from '@angular/core';
import {Recipe} from "../model/recipe";
import {RecipeService} from "../service/recipe.service";
import {Enum} from "../model/enum";
import {EnumService} from "../service/enum.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {
  recipes: Recipe[];
  recipeCategoryList: Enum[];
  modalRef: BsModalRef;
  recipeToDel: Recipe;
  delMsg: string;

  constructor(private recipeService: RecipeService,
              private enumService: EnumService,
              private modalService: BsModalService)
  {
  }

  ngOnInit() {
    this.recipeService.findAll().subscribe(data => {
      this.recipes = data;
    });
    this.recipeCategoryList = this.enumService.getRecipeCategory();
  }

  deleteRecipe(recipe: Recipe) {
    this.recipeService.deleteRecipe(recipe.id).subscribe(result => {
      let newRecipes = this.recipes.filter(item => item.id !== recipe.id);
      this.recipes = newRecipes;
    });
  }

  openModal(template: TemplateRef<any>, recipe: Recipe) {
    this.recipeToDel = recipe;
    this.delMsg = "Are you sure to delete "+recipe.title+"?";
    this.modalRef = this.modalService.show(template, {class: 'modal-lg'});
  }

  confirm(): void {
    this.deleteRecipe(this.recipeToDel);
    this.modalRef.hide();
  }

  decline(): void {
    this.modalRef.hide();
  }

}
