import {Component, OnInit} from '@angular/core';
import {Account} from "../account/account";
import {AccountService} from "../account/account.service";
import {HttpErrorResponse, HttpEventType, HttpResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {ExportService} from "../export/export.service";
import {UploadFileService} from "../upload-files/upload-files.service";
import {Router} from "@angular/router";
import {BioService} from "../bio/bio.service";
import {environment} from "../../environments/environment";
import { AuthenticationService } from '../authentication';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  public accounts: Account[];
  public similarAccs: Account[];
  public deleteAccount: Account;
  public infoAccount: Account;
  public selectedUsers: Account[];

  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';

  constructor(private accountService: AccountService,
              private exportService: ExportService,
              private uploadService: UploadFileService,
              private bioService: BioService,
              public router: Router,
              private authenticationService : AuthenticationService) {
    this.selectedUsers = [];
  }

  ngOnInit() {

    //users cannot access admin page
    this.getAccounts();
    if(this.authenticationService.isAdmin() === false){
          this.router.navigate(['/home']);
    }
  }

  selectFile(event: any) {
    this.selectedFiles = event.target.files;
  }

  upload(email: string) {
    this.progress = 0;
    this.currentFile = this.selectedFiles.item(0);
    this.uploadService.uploadAdmin(this.currentFile, email, this.authenticationService.getJWT()).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.message = event.body.message;
        }
      },
      err => {
        this.progress = 0;
        this.message = 'Could not upload the file!';
        this.currentFile = undefined;
      });

    this.selectedFiles = undefined;
  }

  public getAccounts(): void {
    this.accountService.getAllAccounts().subscribe(
      (response: Account[]) => {
        this.accounts = response;
        this.similarAccs = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onClickModal(account: Account, mode: string): void {

    const container = document.getElementById('main-container');

    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if(mode === 'add') {
      button.setAttribute('data-target', '#addAccount');
    }
    if(mode === 'info') {
      this.infoAccount = account;
      button.setAttribute('data-target', '#info');
    }
    if(mode === 'remove') {
      this.deleteAccount = account;
      button.setAttribute('data-target', '#remove');
    }
    if(mode === 'addPhoto') {
      button.setAttribute('data-target', '#addPhoto');
    }
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public onSearchSimilar(form: NgForm): void {
    this.accounts = [];

    for(let account of this.similarAccs) {
      if(account.firstName.toLowerCase().includes(form.value.keyword.toLowerCase()) ||
        account.lastName.toLowerCase().includes(form.value.keyword.toLowerCase())) {
          this.accounts.push(account);
      }
    }
  }

  public onInfo(accountId: number): void {

    this.accountService.getAccountById(accountId).subscribe(
      (response: Account) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteAccount(account: Account): void {
    this.accountService.deleteAccountById(account.id).subscribe(
      (news: void) => {
        this.getAccounts();
      }, (e: HttpErrorResponse) => {
        alert(e.message);
      }
    );
  }

  public onAddAccount(account: Account): void {

    this.accountService.registerAccount(account).subscribe(
      (response: Account) => {
        console.log(response);
        this.getAccounts();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onSelectUser(account: Account): void {

      /* If the selected account exists, remove it, else add it. */
      if(this.selectedUsers.find(x => x.email === account.email)) {
        this.selectedUsers.splice(this.selectedUsers.indexOf(account, 0), 1);
      } else {
        this.selectedUsers.push(account);
      }
  }

  public onExportSelectedXML(mode: string) {

    let accs = this.accounts;

    if(mode === "sel") {
      accs = this.selectedUsers;
    }

    let ids: number[] = [];
    for(let acc of accs) {
      ids.push(acc.id);
    }

    this.exportService.exportXML(ids).subscribe(
      (resp: string) => {

        this.exportService.downloadFile(environment.apiBaseUrl + resp).subscribe(
          (response: any) => {

            let element = document.createElement('a');
            let blob = new Blob([response], {
              type: 'text/xml'
            });
            element.href = URL.createObjectURL(blob);
            element.setAttribute('download',  'accounts__' + Date.now() + '.xml');
            document.body.appendChild(element);
            element.click();
            this.exportService.cleanup().subscribe();
            window.location.reload();
        }),
          (error: any) => console.log('Error downloading the file'), //when you use stricter type checking
          () => console.info('File downloaded successfully');
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onExportSelectedJSON(mode: string) {

    let accs = this.accounts;

    if(mode === "sel") {
      accs = this.selectedUsers;
    }

    let ids: number[] = [];
    for(let acc of accs) {
      ids.push(acc.id);
    }

    this.exportService.exportJSON(ids).subscribe(
      (resp: string) => {

        this.exportService.downloadFile(environment.apiBaseUrl + resp).subscribe(
          (response: any) => {

            let element = document.createElement('a');
            let blob = new Blob([response], {
              type: 'text/json'
            });
            element.href = URL.createObjectURL(blob);
            element.setAttribute('download',  'accounts__' + Date.now() + '.json');
            document.body.appendChild(element);
            element.click();
            this.exportService.cleanup().subscribe();
            window.location.reload();
          }),
          (error: any) => console.log('Error downloading the file'), //when you use stricter type checking
          () => console.info('File downloaded successfully');
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}
