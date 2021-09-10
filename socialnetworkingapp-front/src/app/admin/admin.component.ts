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


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  public accounts: Account[];
  public deleteAccount: Account;
  public editAccount: Account;
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
              public router: Router) {
    this.selectedUsers = [];
  }

  ngOnInit() {
    this.getAccounts();
  }

  selectFile(event: any) {
    this.selectedFiles = event.target.files;
  }

  upload(email: string) {
    this.progress = 0;
    this.currentFile = this.selectedFiles.item(0);
    this.uploadService.upload(this.currentFile, email).subscribe(
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
        console.log(this.accounts);
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
    if(mode === 'edit') {
      this.editAccount = account;
      button.setAttribute('data-target', '#edit');
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

  public onSearch(form: NgForm): void {

    this.accountService.getAccountByEmail(form.value.email).subscribe(
      (response: Account) => {
        console.log(response);
        this.accounts = [response];
      },
      (error: HttpErrorResponse) => {
        if(error.status === 500) {
          alert("No user found!");
        }
      }
    );
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

  public onUpdateAccount(account: Account): void {

    this.accountService.updateAccount(account).subscribe(
      (response: Account) => {
        console.log(response);
        this.getAccounts();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteAccount(accountId: number): void {

    /* Find account Id, delete its bio (if present), delete account */
    this.accountService.getAccountById(accountId).subscribe(
      (response: Account) => {
        this.bioService.deleteBioByAccountId(response.id).subscribe(
          (response: void) => {
            this.accountService.deleteAccountById(accountId).subscribe(
              (response: void) => {
                this.getAccounts();
              },
              (error: HttpErrorResponse) => {
                alert(error.message);
              }
            );
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
          }
        );
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
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

/*
    let data = [];
*/
    let accs = this.accounts;

    if(mode === "sel") {
      accs = this.selectedUsers;
    }
    /*for(let account of this.accounts) {

      data.push({
        "firstName": account.firstName,
        "lastName": account.lastName,
        "email": account.email,
        "phone": account.phone,
        "crDate": account.dateCreated,
        "role": "USER"
      })
    }*/

    this.exportService.exportXML(accs).subscribe(
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
            window.location.reload();
       /*   const blob: Blob = new Blob([response], {
            type: 'text/xml' });

          const url = window.URL.createObjectURL(blob);
          fileSaver.saveAs(blob,);
          window.open(url);

          this.selectedUsers.splice(0, this.selectedUsers.length);
          window.location.reload();*/
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

    /*let data = [];*/
    let accs = this.accounts;

    if(mode === "sel") {
      accs = this.selectedUsers;
    }
   /* for(let account of this.accounts) {

      data.push({
        "firstName": account.firstName,
        "lastName": account.lastName,
        "email": account.email,
        "phone": account.phone,
        "crDate": account.dateCreated,
        "role": "USER"
      })
    }*/

    this.exportService.exportJSON(accs).subscribe(
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
            window.location.reload();
           /* const blob:any = new Blob([response], {
              type: 'text/json; charset=utf-8' });

            const url = window.URL.createObjectURL(blob);
            fileSaver.saveAs(blob, 'accounts__' + Date.now() + '.json');
            window.open(url);

            this.selectedUsers.splice(0, this.selectedUsers.length);
            window.location.reload();*/
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
