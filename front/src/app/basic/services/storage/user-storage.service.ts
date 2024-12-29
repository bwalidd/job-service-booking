import { Injectable } from '@angular/core';

const TOKEN='s_token';
const USER='s_user';


@Injectable({
  providedIn: 'root'
})
export class UserStorageService {
  

  constructor() { }


  public saveToken(token: any): void {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN, token);
  }

  static getToken(): string | null {
    return window.localStorage.getItem(TOKEN);
  }

  public saveUser(user: any): void {
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  static getUser(): any {
    const user = window.localStorage.getItem(USER);
    if (user) {
      return JSON.parse(user);
    }
    return {};
  }

  static getUserId(): any {
    const user = this.getUser();
    return user.userId;
  }

  static getUserRole(): any {
    const user = this.getUser();
    // console.log('user',user);
    return user.userRole;
  }

  static isClientLoggedIn(): boolean {
    if (this.getToken() === null) {
      return false;
    }
    const role = this.getUserRole();
    // console.log('-------->',role);
    return role == 'CLIENT';
  }

  static isCompanyLoggedIn(): boolean {
    if (this.getToken() === null) {
      return false;
    }
    const role = this.getUserRole();
    return role == 'COMPANY';
  
  }

  static logout(): void {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }
}
