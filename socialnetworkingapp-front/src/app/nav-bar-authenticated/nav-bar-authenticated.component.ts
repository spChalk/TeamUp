import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication';

@Component({
  selector: 'app-nav-bar-authenticated',
  templateUrl: './nav-bar-authenticated.component.html',
  styleUrls: ['./nav-bar-authenticated.component.css']
})
export class NavBarAuthenticatedComponent implements OnInit {

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
