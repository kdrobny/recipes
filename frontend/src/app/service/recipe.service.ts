import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";
import {Recipe} from "../model/recipe";

interface Role {
  authority : string
}

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  private recipesUrl: string;
  public authorities: Role[];
  public authenticated: boolean = false;
  public isAdmin: boolean = false;

  constructor(private http: HttpClient) {
    this.recipesUrl = 'http://localhost:8080/api/recipes';
  }

  authenticate(credentials, callback) {

    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    this.isAdmin = false;
    this.http.get('http://localhost:8080/user', {headers: headers, withCredentials: true}).subscribe(response => {
      if (response['authorities']) {
        this.authenticated = true;
        this.authorities = response['authorities'];
        this.authorities.forEach(el => {if (el.authority === 'ROLE_ADMIN') { this.isAdmin = true; }} )
      } else {
        this.authenticated = false;
        this.authorities = [];
      }
      return callback && callback();
    });

  }

  public findAll(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.recipesUrl, {withCredentials: true});
  }

  public getRecipe(id : number): Observable<Recipe> {
    return this.http.get<Recipe>(this.recipesUrl + "/" + id, {withCredentials: true});
  }

  public saveRecipe(recipe: Recipe) {
    if (recipe.id) {
      return this.http.put<Recipe>(this.recipesUrl + "/" + recipe.id, recipe, {withCredentials: true});
    }
    else {
      return this.http.post<Recipe>(this.recipesUrl, recipe, {withCredentials: true});
    }
  }

  public deleteRecipe(id : number) {
    return this.http.delete(this.recipesUrl + "/" + id, {withCredentials: true});
  }

}
