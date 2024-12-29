import { Component } from '@angular/core';
import { CompanyService } from '../../services/company.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-all-ads',
  templateUrl: './all-ads.component.html',
  styleUrls: ['./all-ads.component.scss']
})
export class AllAdsComponent {

  AllAds: any[] = [];

  constructor(private cmpSrv:CompanyService, private nz:NzNotificationService) { }


  getAllAds(): void {
    this.cmpSrv.getAllAds().subscribe(res => {
      console.log(res);
      this.AllAds = res;
    }, error => {
      console.log(error);
    });
  }

  ngOnInit(): void {
    this.getAllAds();
  }

  updateImg(img:any){
    return 'data:image/png;base64,'+img;
  }


  deleteAd(id:number): void {
    this.cmpSrv.DeleteAd(id).subscribe(res => {
      if (res.status === 200){
        this.nz.success('Success','Ad deleted successfully',{nzDuration:3000});
        this.getAllAds();
      }
    }, error => {
      console.log('-------------->',error.message);
    });
  }
}
