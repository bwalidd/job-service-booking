import { Component } from '@angular/core';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrls: ['./my-bookings.component.scss']
})
export class MyBookingsComponent {

  Booking:any[] = [];
  constructor(private clientSrv:ClientService) { }

  ngOnInit(): void {
  
    this.getMyBookings();
  }

  getMyBookings(){
    this.clientSrv.getMyBookings().subscribe(res => {
      console.log(res);
      this.Booking = res;
    }, error => {
      console.log('-------23---->',error);
    });
  }
}
