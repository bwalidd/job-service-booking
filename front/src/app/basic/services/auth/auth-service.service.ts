import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { UserStorageService } from '../storage/user-storage.service';


const BASIC_URL = 'http://localhost:8080/';
const Auth_Header = "Authorization";

@Injectable({
  providedIn: 'root' // This makes the service available globally without adding it to providers.
})
export class AuthServiceService {

  constructor(private http:HttpClient,private userService:UserStorageService) {}

  RegisterClient(client: any):Observable<any> {
    return this.http.post(BASIC_URL + 'client/sign-up', client);
  }
  
  RegisterCompany(company: any):Observable<any> {
    return this.http.post(BASIC_URL + 'company/sign-up', company);
  }

  Login(email:String,password:String):Observable<any> {
    return this.http.post(BASIC_URL + 'client/login', {email,password},{observe:'response'})
    .pipe(
      map((res: HttpResponse<any>) => {
        this.userService.saveUser(res.body);
        console.log(res.body);
        const tokenlength = res.headers.get(Auth_Header)?.length;
        const token = res.headers.get(Auth_Header)?.substring(7,tokenlength);
        this.userService.saveToken(token);
        return res;
      })
    );
    
  }


}
