import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserStorageService } from './basic/services/storage/user-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';

  isClientLoggedIn!: boolean;
  isCompanyLoggedIn!: boolean;

  constructor(private router:Router) {
    
  }

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      this.isClientLoggedIn = UserStorageService.isClientLoggedIn();
      this.isCompanyLoggedIn = UserStorageService.isCompanyLoggedIn();
    });
  }

  logout(): void {
    UserStorageService.logout();
    this.router.navigateByUrl('login');
  }

}
