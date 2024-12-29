import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/basic/services/storage/user-storage.service';


const BASIC_URL = 'http://localhost:8080/';


@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private http:HttpClient) { }

  getAllAds():Observable<any>{
    return this.http.get(BASIC_URL+`api/client/ads`,{
      headers: this.createHeaders()
    });
  }

  createHeaders() : any{
    let http: any = new HttpHeaders();
    http = http.set('Authorization', 'Bearer ' + UserStorageService.getToken());
    return http;
  }

  getAdByService(service:string):Observable<any>{
    return this.http.get(BASIC_URL+`api/client/search/${service}`,{
      headers: this.createHeaders()
    });
  }

  getAdDetails(id:any):Observable<any>{
    return this.http.get(BASIC_URL+`api/client/ad/${id}`,{
      headers: this.createHeaders()
    });
  }


  bookAdService(bookDto:any):Observable<any>{
    return this.http.post(BASIC_URL+`api/client/reserve`,bookDto,{
      headers: this.createHeaders()
    });
  }

  getMyBookings():Observable<any>{
    const user = UserStorageService.getUserId();
    return this.http.get(BASIC_URL+`api/client/my-bookings/${user}`,{
      headers: this.createHeaders()
    });
  }

  giveReview(review:any):Observable<any>{
    return this.http.post(BASIC_URL+`api/client/review`,review,{
      headers: this.createHeaders()
    });
  }

}


