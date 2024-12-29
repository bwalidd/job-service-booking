import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthServiceService } from '../../services/auth/auth-service.service';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { UserStorageService } from '../../services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  ValidateForm!: FormGroup;

  constructor(private fb:FormBuilder,private authservice:AuthServiceService,private router:Router , private nzNotif:NzNotificationService) { }

  ngOnInit(): void {
    this.ValidateForm = this.fb.group({
      email: [null,[Validators.required]],
      password: [null,[Validators.required]]
    });
  }

  submitForm(): void {
    this.authservice.Login(this.ValidateForm.get(['email'])!.value,this.ValidateForm.get(['password'])!.value).subscribe(res => {
      
      console.log('----------------------');
      console.log(res);
      console.log('----------------------');
      if (UserStorageService.isClientLoggedIn()) {
        console.log('client');
        this.router.navigateByUrl('client/dashboard');
      }else if (UserStorageService.isCompanyLoggedIn()) {
        console.log('company');
        this.router.navigateByUrl('company/dashboard');
      }else{
        console.log('error');
      }
      
    }, error => {
      console.log(error);
      this.nzNotif.error('ERROR',`${error.error}`,{nzDuration:5000});
    });
  }

}
