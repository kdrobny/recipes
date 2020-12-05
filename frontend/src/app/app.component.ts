import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {finalize} from "rxjs/operators";
import {RecipeService} from "./service/recipe.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title : string;

  constructor(private recipeService: RecipeService, private http: HttpClient, private router: Router) {
    this.title = 'My Recipe Book';
    this.recipeService.authenticate(undefined, undefined);
  }

  logout() {
    this.http.post('http://localhost:8080/logout', {}, {withCredentials: true}).pipe(finalize(() => {
      this.recipeService.authenticated = false;
      this.recipeService.authorities = [];
      this.recipeService.isAdmin = false;
      this.router.navigateByUrl('/login');
    })).subscribe();
  }

  authenticated() {
    return this.recipeService.authenticated;
  }

  isAdmin() {
    return this.recipeService.isAdmin;
  }

}
