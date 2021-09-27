import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication';
import { ConnectionRequestService } from '../connection-request/connection-request.service';
import { ConnectionRequest } from '../connection-request/ConnectionRequest';

@Component({
    selector: 'app-nav-bar-authenticated',
    templateUrl: './nav-bar-authenticated.component.html',
    styleUrls: ['./nav-bar-authenticated.component.css']
})
export class NavBarAuthenticatedComponent implements OnInit {

    requests : ConnectionRequest[];
    account: string;

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService,
        private connectionRequestService : ConnectionRequestService) {

    }

    ngOnInit() {
        this.account = this.authenticationService.getCurrentUser();
        
    }

    getCurrentUser() {
        return this.authenticationService.getCurrentUser();
    }

    logout() {
        this.authenticationService.logOut();
        this.router.navigate(['/']);
    }

    accept(email : string){
        this.connectionRequestService.acceptRequestWithEmail(email).subscribe(
            (resp:any)=>{
                window.location.reload();
            },
            (error:HttpErrorResponse)=>{
                window.location.reload();
                console.log(error)
            }
        );
    }
    reject(email :string){
        this.connectionRequestService.rejectRequest(email).subscribe(
            (resp:any)=>{
                window.location.reload();
            },
            (error:HttpErrorResponse)=>{
                console.log(error)
            }
        );
    }

    remove(id : number){
        this.connectionRequestService.removeRequest(id).subscribe(
            (resp:any)=>{
                window.location.reload();
            },
            (error:HttpErrorResponse)=>{
                window.location.reload();
                console.log(error)
            }
        );
    }

    public onClickModal(data: any, mode: string): void {

        const container = document.getElementById('main-container');

        const button = document.createElement('button');
        button.type = 'button';
        button.style.display = 'none';
        button.setAttribute('data-toggle', 'modal');

        if (mode === 'friends') {
            button.setAttribute('data-target', '#friends');
            this.connectionRequestService.getAllRequests().subscribe(
                (resp : ConnectionRequest[])=>{
                    this.requests=resp;
                    console.log(resp);
                },
                (error : any)=>{
                    console.log(error);
                }
            )
        }
        if (mode === 'comments') {
            button.setAttribute('data-target', '#comments');
        }

        if (mode === 'likes') {
            button.setAttribute('data-target', '#likes');
        }
        if (container != null) {
            container.appendChild(button);
            button.click();
        }
    }
}
