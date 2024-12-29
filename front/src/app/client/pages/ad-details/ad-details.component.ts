import { Component } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserStorageService } from 'src/app/basic/services/storage/user-storage.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';


@Component({
  selector: 'app-ad-details',
  templateUrl: './ad-details.component.html',
  styleUrls: ['./ad-details.component.scss']
})
export class AdDetailsComponent {

  Adid  = this.activateRoute.snapshot.params['id'];
  avatarUrl: string = '';
  ad: any;
  reviews: any;
  
  ValidateForm!: FormGroup;

  constructor(private clientSrv:ClientService,private activateRoute:ActivatedRoute,
              private fb:FormBuilder,private nz:NzNotificationService,private router:Router
  ) { }



  ngOnInit(): void {
    this.ValidateForm = this.fb.group({
      bookDate: ['',[Validators.required]],
    });
    this.getAdDetails();
  }

  getAdDetails(){
    console.log(this.activateRoute.snapshot.params['id']);
    this.clientSrv.getAdDetails(this.Adid).subscribe(res => {
      this.avatarUrl = 'data:image/png;base64,'+res.dto.returnedImg;
      this.ad = res.dto;
      console.log('-------2->',res);
      this.reviews = res.reviewDtoList;
    }, error => {
      console.log
    });
  }

  bookAd(){
    const bookService = {
      bookDate: this.ValidateForm.get(['bookDate'])?.value,
      adId: this.ad.id,
      userId : UserStorageService.getUserId()
    }

    this.clientSrv.bookAdService(bookService).subscribe(res => {
      console.log('-------3->',res);
      this.nz.success('Success','Service booked successfully',{nzDuration:2000});
      this.router.navigate(['/client/dashboard']);
    }, error => {
      console.log(error);
    });
  }

}
