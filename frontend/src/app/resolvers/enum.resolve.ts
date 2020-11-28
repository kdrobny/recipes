import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve} from "@angular/router";
import {EnumService} from "../service/enum.service";

@Injectable()
export class EnumResolve implements Resolve<any> {

  constructor(private enumService: EnumService) { }

  resolve(route: ActivatedRouteSnapshot) {
    return this.enumService.loadEnums();
  }
}
