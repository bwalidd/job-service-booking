import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/basic/services/storage/user-storage.service';

const BASIC_URL = 'http://localhost:8080/';


@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http:HttpClient) { }

  postAd(adDto:FormData):Observable<any>{
    const userId = UserStorageService.getUserId();
    const head = this.createHeaders();
    return this.http.post(BASIC_URL+`api/company/ad/${userId}`,adDto,{
      headers: head
    })
  }


  getAllAds():Observable<any>{
    const user = UserStorageService.getUserId();
    return this.http.get(BASIC_URL+`api/company/all-ads/${user}`,{
      headers: this.createHeaders()
    });
  }

  createHeaders() : HttpHeaders{
      let http:HttpHeaders = new HttpHeaders();
      http = http.set('Authorization', 'Bearer ' + UserStorageService.getToken());
      return http;
  }

  getAdById(id:any):Observable<any>{
    return this.http.get(BASIC_URL+`api/company/ad/${id}`,{
      headers: this.createHeaders()
    });
  }

  updateAd(id:any,adDto:FormData):Observable<any>{
    return this.http.put(BASIC_URL+`api/company/edit-ad/${id}`,adDto,{
      headers: this.createHeaders()
    });
  }

  DeleteAd(id:any):Observable<any>{
    return this.http.delete(BASIC_URL+`api/company/delete-ad/${id}`,{
      headers: this.createHeaders()
    });
  }

  allReservations():Observable<any>{
    const user = UserStorageService.getUserId();
    return this.http.get(BASIC_URL+`api/company/bookings/${user}`,{
      headers: this.createHeaders()
    });
  }

  changeStatus(id:any,status:any):Observable<any>{
    return this.http.get(BASIC_URL+`api/company/book-status/${id}/${status}`,{
      headers: this.createHeaders()
    });
  }

  
}
