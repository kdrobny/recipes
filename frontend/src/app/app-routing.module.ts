import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RecipeListComponent} from "./recipe-list/recipe-list.component";
import {RecipeFormComponent} from "./recipe-form/recipe-form.component";
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'recipes'},
  {path: 'home', component: RecipeListComponent},
  {path: 'recipes', component: RecipeListComponent},
  {path: 'addrecipe', component: RecipeFormComponent},
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
