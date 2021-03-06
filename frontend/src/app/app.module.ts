import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RecipeListComponent} from './recipe-list/recipe-list.component';
import {RecipeFormComponent} from './recipe-form/recipe-form.component';
import {
  HTTP_INTERCEPTORS,
  HttpClientModule,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpXsrfTokenExtractor
} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RecipeService} from "./service/recipe.service";
import {PanelComponent} from './panel/panel.component';
import {RecipeCategoryListPipe} from './pipes/recipe-category-list.pipe';
import {RecipeFilterCategoryPipe} from './pipes/recipe-filter-category.pipe';
import {LoginComponent} from './login/login.component';
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {Router} from "@angular/router";
import {EnumService} from "./service/enum.service";
import {EnumResolve} from "./resolvers/enum.resolve";
import {EnumValidPipe} from './pipes/enum-valid.pipe';
import {ModalModule} from "ngx-bootstrap/modal";
import { RecipeFilterTitlePipe } from './pipes/recipe-filter-title.pipe';
import { RecipeFilterIngredientsPipe } from './pipes/recipe-filter-ingredients.pipe';
import { RecipeFilterDirectionsPipe } from './pipes/recipe-filter-directions.pipe';
import { RecipeFilterDifficultyPipe } from './pipes/recipe-filter-difficulty.pipe';
import { RecipeFilterPreparationTimePipe } from './pipes/recipe-filter-preparation-time.pipe';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 401) {
          this.router.navigate(['login'], {queryParams: {returnUrl: req.url}});
        }
        return throwError(err);
      })
    );
  }
}

@Injectable()
export class HttpXsrfInterceptor implements HttpInterceptor {

  constructor(private tokenExtractor: HttpXsrfTokenExtractor) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let requestMethod: string = req.method;
    requestMethod = requestMethod.toLowerCase();

    if (requestMethod && (requestMethod === 'post' || requestMethod === 'delete' || requestMethod === 'put')) {
      const headerName = 'X-XSRF-TOKEN';
      let token = this.tokenExtractor.getToken() as string;
      if (token !== null && !req.headers.has(headerName)) {
        req = req.clone({headers: req.headers.set(headerName, token)});
      }
    }

    return next.handle(req);
  }
}

@NgModule({
  declarations: [
    AppComponent,
    RecipeListComponent,
    RecipeFormComponent,
    PanelComponent,
    RecipeCategoryListPipe,
    RecipeFilterCategoryPipe,
    LoginComponent,
    EnumValidPipe,
    RecipeFilterTitlePipe,
    RecipeFilterIngredientsPipe,
    RecipeFilterDirectionsPipe,
    RecipeFilterDifficultyPipe,
    RecipeFilterPreparationTimePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule.forRoot()
  ],
  providers: [RecipeService,
    EnumService,
    EnumResolve,
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: HttpXsrfInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
