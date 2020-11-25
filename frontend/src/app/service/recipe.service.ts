import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";
import {Recipe} from "../model/recipe";

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private recipesUrl: string;
  public authenticated: boolean = false;

  constructor(private http: HttpClient) {
    this.recipesUrl = 'http://localhost:8080/api/recipes';
  }

  authenticate(credentials, callback) {

    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    this.http.get('http://localhost:8080/user', {headers: headers, withCredentials: true}).subscribe(response => {
      if (response['name']) {
        this.authenticated = true;
      } else {
        this.authenticated = false;
      }
      return callback && callback();
    });

  }

  public findAll(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.recipesUrl, {withCredentials: true});
  }

  public save(recipe: Recipe) {
    return this.http.post<Recipe>(this.recipesUrl, recipe, {withCredentials: true});
  }

}
