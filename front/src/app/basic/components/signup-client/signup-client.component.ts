import { Component, inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthServiceService } from '../../services/auth/auth-service.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-signup-client',
  templateUrl: './signup-client.component.html',
  styleUrls: ['./signup-client.component.scss']
})

export class SignupClientComponent {

  ValidateForm!: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthServiceService, private router: Router, private notification: NzNotificationService) {
  }

  ngOnInit(): void {
    this.ValidateForm = this.fb.group({
      email: [null, [Validators.email, Validators.required]],
      name: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      phone: [null],
      password: [null, [Validators.required]],
      password2: [null, [Validators.required]],
      
    });
  }

  submitForm(): void {
    this.authService.RegisterClient(this.ValidateForm.value).subscribe(res => {
      this.notification.success('Success', 'User created successfully',{nzDuration:5000});
      this.router.navigateByUrl('/login');
    }, error => {
      console.log(error);
      this.notification.error('ERROR', `${error.error}`, { nzDuration: 5000 });
    });
  }

}
