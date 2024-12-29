import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientComponent } from './client.component';
import { ClientDashboardComponent } from './pages/client-dashboard/client-dashboard.component';
import { AdDetailsComponent } from './pages/ad-details/ad-details.component';
import { MyBookingsComponent } from './pages/my-bookings/my-bookings.component';
import { ReviewComponent } from './pages/review/review.component';

const routes: Routes = [
  { path: '', component: ClientComponent },
  {path:'dashboard',component:ClientDashboardComponent},
  {path:'ad/:id',component:AdDetailsComponent},
  {path:'Bookings',component:MyBookingsComponent},
  {path:'review/:id',component:ReviewComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
