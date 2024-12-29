import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { AuthServiceService } from '../../services/auth/auth-service.service';

@Component({
  selector: 'app-signup-company',
  templateUrl: './signup-company.component.html',
  styleUrls: ['./signup-company.component.scss']
})
export class SignupCompanyComponent {
  ValidateForm!: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthServiceService, private router: Router, private notification: NzNotificationService) {
  }

  ngOnInit(): void {
    this.ValidateForm = this.fb.group({
      email: [null, [Validators.email, Validators.required]],
      name: [null, [Validators.required]],
      phone: [null],
      password: [null, [Validators.required]],
      password2: [null, [Validators.required]],
      
    });
  }

  submitForm(): void {
    this.authService.RegisterCompany(this.ValidateForm.value).subscribe(res => {
      this.notification.success('Success', 'User created successfully',{nzDuration:5000});
      this.router.navigateByUrl('/login');
    }, error => {
      this.notification.error('ERROR', `${error.error}`, { nzDuration: 5000 });
    });
  }
}
