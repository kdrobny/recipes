import { Pipe, PipeTransform } from '@angular/core';
import {Enum} from "../model/enum";

@Pipe({
  name: 'enumValid'
})
export class EnumValidPipe implements PipeTransform {

  transform(enumList: Enum[]): Enum[] {
    if (!enumList) {
      return enumList;
    }
    else {
      return enumList.filter(
        el => el.valid === 'Y'
      );
    }
  }

}
