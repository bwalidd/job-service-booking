import { Component } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-client-dashboard',
  templateUrl: './client-dashboard.component.html',
  styleUrls: ['./client-dashboard.component.scss']
})
export class ClientDashboardComponent {


  validateForm!: FormGroup;
  AllAds: any[] = [];

  constructor(private clientSrv:ClientService,
            private fb:FormBuilder
  ) { }
  
  getAds(){
    this.clientSrv.getAllAds().subscribe(res => {
      this.AllAds = res;
    }, error => {
      console.log(error);
    });
  
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      service: ['',[Validators.required]],
    });
    this.getAds();
  }

  searchAdByName(){
    console.log('------------');
    console.log(this.validateForm.get(['service'])?.value);
    console.log('------------');
    this.clientSrv.getAdByService(this.validateForm.get(['service'])?.value).subscribe(res => {
      console.log(res);
      this.AllAds = res;
    }, error => {
      console.log(error);
    });
  }

  


  updateImg(img:any){
    return 'data:image/png;base64,'+img;
  }
}
