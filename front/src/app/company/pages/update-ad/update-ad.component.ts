import { Component } from '@angular/core';
import { CompanyService } from '../../services/company.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-update-ad',
  templateUrl: './update-ad.component.html',
  styleUrls: ['./update-ad.component.scss']
})
export class UpdateAdComponent {


  ValidateForm!: FormGroup;
  selectedFile!: File | null;
  imagePreview!: string | ArrayBuffer | null;
  existingImage: string | null = null;

  imgChanged = false;

  constructor(
      private cmpSrv:CompanyService,
      private activatedRoute:ActivatedRoute,
      private router:Router,
      private nzNotif:NzNotificationService,
      private fb:FormBuilder
    ) { }


  adId = this.activatedRoute.snapshot.params['id'];

  displayAd(){
    this.cmpSrv.getAdById(this.adId).subscribe(res=>{
      // console.log(res);
      this.ValidateForm.patchValue(res);
      this.existingImage = 'data:image/jpeg;base64,'+res.returnedImg;
    },error=>{
      console.log(error);
    }
  )
  }


  ngOnInit(): void {
    this.ValidateForm = this.fb.group({
      serviceName: [null,[Validators.required]],
      description: [null,[Validators.required]],
      price: [null,[Validators.required]],
    });
    this.displayAd();
  }


  OnFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
    this.previewImage();
    this.existingImage =null;
    this.imgChanged = true;
  }

  previewImage(): void {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile!);
  }

  updateAd(){
    const formData: FormData = new FormData();
    if (this.imgChanged && this.selectedFile) {
      formData.append('img',this.selectedFile);
    }
    formData.append('serviceName',this.ValidateForm.get('serviceName')!.value);
    formData.append('description',this.ValidateForm.get('description')!.value);
    formData.append('price',this.ValidateForm.get('price')!.value);


    console.log('--------------');
    console.log(formData);
    console.log('--------------');
    this.cmpSrv.updateAd(this.adId,formData).subscribe(res=>{
      this.nzNotif.success('SUCCESS','Ad updated successfully',{nzDuration:5000});
      this.router.navigateByUrl('company/dashboard');
    },error=>{
      this.nzNotif.error('ERROR',`${error.error}`,{nzDuration:5000});
      console.log('----me------> ',error);
    });
  }


}
