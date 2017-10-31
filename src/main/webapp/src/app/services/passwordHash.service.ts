import {Injectable} from "@angular/core";
import {Md5} from 'ts-md5/dist/md5';

@Injectable()
export class PasswordHashService {

  constructor() {

  }

  public hashPassword(password: string): string {
    return Md5.hashStr(password).toString();
  }

}
