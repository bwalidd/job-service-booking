import { Component } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CompanyService } from '../../services/company.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-ad',
  templateUrl: './create-ad.component.html',
  styleUrls: ['./create-ad.component.scss']
})
export class CreateAdComponent {

  ValidateForm!: FormGroup;
  selectedFile!: File | null;
  imagePreview!: string | ArrayBuffer | null;

  constructor(private fb:FormBuilder,
              private cmpSrv:CompanyService,
              private nzNotif:NzNotificationService,
              private router:Router) { }

  ngOnInit(): void {
    this.ValidateForm = this.fb.group({
      serviceName: [null,[Validators.required]],
      description: [null,[Validators.required]],
      price: [null,[Validators.required]],
    });
  }

  OnFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }

  previewImage(): void {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile!);
  }

  submitForm(): void {
    const formData: FormData = new FormData();
    formData.append('serviceName',this.ValidateForm.get('serviceName')!.value);
    formData.append('description',this.ValidateForm.get('description')!.value);
    formData.append('price',this.ValidateForm.get('price')!.value);
    formData.append('img',this.selectedFile!);

    this.cmpSrv.postAd(formData).subscribe(res => {
      console.log('form data: ',formData.get('description'));
      this.nzNotif.success('SUCCESS','Ad created successfully',{nzDuration:5000});
      this.router.navigateByUrl('company/dashboard');
      console.log(res);
    }, error => {
      this.nzNotif.error('ERROR',`${error.error}`,{nzDuration:5000});
      console.log('----------> ',error);
    });
  }

}
