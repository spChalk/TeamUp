import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication';

@Component({
  selector: 'app-nav-bar-admin',
  templateUrl: './nav-bar-admin.component.html',
  styleUrls: ['./nav-bar-admin.component.css']
})
export class NavBarAdminComponent implements OnInit {

    constructor(private router: Router,
    private authenticationService : AuthenticationService) {
    }

    ngOnInit(){
    }
    getCurrentUser(){
        return this.authenticationService.getCurrentUser();
    }

    logout(){
        this.authenticationService.logOut();
        this.router.navigate(['/']);
    }
}
