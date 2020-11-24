import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RecipeListComponent } from './recipe-list/recipe-list.component';
import { RecipeFormComponent } from './recipe-form/recipe-form.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {RecipeService} from "./service/recipe.service";
import { PanelComponent } from './panel/panel.component';
import { RecipeCategoryListPipe } from './pipes/recipe-category-list.pipe';
import { RecipeFilterCategoryPipe } from './pipes/recipe-filter-category.pipe';

@NgModule({
  declarations: [
    AppComponent,
    RecipeListComponent,
    RecipeFormComponent,
    PanelComponent,
    RecipeCategoryListPipe,
    RecipeFilterCategoryPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [RecipeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
