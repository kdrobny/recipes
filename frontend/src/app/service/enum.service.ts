import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EnumService {

  private enumsUrl: string;
  public authenticated: boolean = false;
  private enums;

  constructor(private http: HttpClient) {
    this.enumsUrl = 'http://localhost:8080/api/enums';
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

  loadEnums() {
    return new Observable(subscriber => {
      this.http.get<any>(this.enumsUrl, {withCredentials: true}).subscribe(response => {
        this.enums = response;
        subscriber.next(this.enums);
        subscriber.complete();
      });
    });
  }

  getEnum(enumName: string): any {
    const enums = this.enums;

    if (enums && enums[enumName]) {
      return enums[enumName];
    }
    return [];
  }

  getEnumItem(enumName: string, enumCode: string): any {
    const enums = this.enums;

    if (enums && enums[enumName] && enums[enumName][enumCode]) {
      return enums[enumName][enumCode];
    }
    return null;
  }

  getRecipeCategory() {
    const options = this.getEnum('RecipeCategory');
    const allItems = [];
    for (const prop in options) {
      if (options.hasOwnProperty(prop)) {
        allItems.push(options[prop]);
      }
    }
    return allItems;
  }

  getRecipeDifficulty() {
    const options = this.getEnum('RecipeDifficulty');
    const allItems = [];
    for (const prop in options) {
      if (options.hasOwnProperty(prop)) {
        allItems.push(options[prop]);
      }
    }
    return allItems;
  }

  getRecipePreparationTime() {
    const options = this.getEnum('RecipePreparationTime');
    const allItems = [];
    for (const prop in options) {
      if (options.hasOwnProperty(prop)) {
        allItems.push(options[prop]);
      }
    }
    return allItems;
  }

}
