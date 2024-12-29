import { Component } from '@angular/core';
import { Route } from '@angular/router';
import { CompanyService } from '../../services/company.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-company-dashboard',
  templateUrl: './company-dashboard.component.html',
  styleUrls: ['./company-dashboard.component.scss']
})
export class CompanyDashboardComponent {

  bookings:any;
  constructor(private companySrv:CompanyService,private nz:NzNotificationService) { }

  ngOnInit(): void {

    this.getBookedServices();
  }

  getBookedServices(){
    this.companySrv.allReservations().subscribe(res => {
      console.log(res);
      this.bookings = res;
    }, error => {
      console.log(error);
    });
  }

  changeStatus(id:any,status:String){
    this.companySrv.changeStatus(id,status).subscribe(res => {
      console.log(res);
      this.nz.success('Success', 'Status changed successfully',{nzDuration:2000});
      this.getBookedServices();
    }, error => {
      console.log(error);
      this.nz.error('Error', 'Error changing status',{nzDuration:2000});
    });
  }
}
