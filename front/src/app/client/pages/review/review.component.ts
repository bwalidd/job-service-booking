import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { ClientService } from '../../services/client.service';
import { UserStorageService } from 'src/app/basic/services/storage/user-storage.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent {

  bookId  = this.activateRoute.snapshot.params['id'];
  ValidateForm!: FormGroup;

  constructor(
    private fb:FormBuilder,
    private clientSrv:ClientService,
    private nz:NzNotificationService,
    private router:Router,
    private activateRoute:ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.ValidateForm = this.fb.group({
      review: [null,Validators.required],
      rating: ['',Validators.required]
    });
  }

  giveReview(){
    const reviewDto = {
      review: this.ValidateForm.get(['review'])?.value,
      rating: this.ValidateForm.get(['rating'])?.value,
      bookId: this.bookId,
      userId: UserStorageService.getUserId()
    }

    this.clientSrv.giveReview(reviewDto).subscribe(res => {
      this.nz.success('Success','Review given successfully',{nzDuration:2000});
      this.router.navigate(['/client/dashboard']);
    }, error => {
      this.nz.error('Error','Error giving review',{nzDuration:2000});
      console.log(error);
    });
  }

}
