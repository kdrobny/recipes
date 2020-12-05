import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RecipeListComponent} from "./recipe-list/recipe-list.component";
import {RecipeFormComponent} from "./recipe-form/recipe-form.component";
import {LoginComponent} from "./login/login.component";
import {EnumResolve} from "./resolvers/enum.resolve";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'recipes'},
  {path: 'home', component: RecipeListComponent, resolve: {enums : EnumResolve} },
  {path: 'recipes', component: RecipeListComponent, resolve: {enums : EnumResolve} },
  {path: 'addrecipe', component: RecipeFormComponent, resolve: {enums : EnumResolve} },
  {path: 'editrecipe/:id', component: RecipeFormComponent, resolve: {enums : EnumResolve} },
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
