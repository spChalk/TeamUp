import {Component, OnInit} from '@angular/core';
import {Account, AppUserRole} from "../account/account";
import {AccountService} from "../account/account.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {ExportService} from "../export/export.service";
import {JSONFile} from "@angular/cli/utilities/json-file";
import {Byte} from "@angular/compiler/src/util";

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
  public selectedUsers: Account[] = [];

  constructor(private accountService: AccountService,
              private exportService: ExportService) {}

  ngOnInit() {
    this.getAccounts();
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

    this.accountService.deleteAccountById(accountId).subscribe(
      (response: void) => {
        this.getAccounts();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
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
      if(this.selectedUsers.length && this.selectedUsers.find(x => x.email == account.email)) {
        this.selectedUsers.splice(this.selectedUsers.indexOf(account, 0), 1);
      } else {
        this.selectedUsers.push(account);
      }
  }

  public onSelectAll(): void {

  }

  public onRemoveAllSelections(): void {
    this.selectedUsers.length = 0;
  }

  public onExportSelectedJSON() {

    let data = [];
    for(let account of this.accounts) {

      data.push({
        "firstName": account.firstName,
        "lastName": account.lastName,
        "email": account.email,
        "phone": account.phone,
        "crDate": account.dateCreated,
        "role": "USER"
      })
    }

    /*this.exportService.exportJSON(data);*/

   /* public getFileName(response: HttpResponse<Blob>){
      let filename: string;
      try {
        const contentDisposition: string = response.headers.get('content-disposition');
        const r = /(?:filename=")(.+)(?:")/
        filename = r.exec(contentDisposition)[1];
      }
      catch (e) {
        filename = 'myfile.txt'
      }
      return filename
    }*/


    /*public downloadFile() {
      this.exportService.downloadFile("exp")
        .subscribe(
          (response: HttpResponse<Blob>) => {
            let filename: string = this.getFileName(response)
            let binaryData = [];
            binaryData.push(response.body);
            let downloadLink = document.createElement('a');
            downloadLink.href = window.URL.createObjectURL(new Blob(binaryData, { type: 'blob' }));
            downloadLink.setAttribute('download', filename);
            document.body.appendChild(downloadLink);
            downloadLink.click();
          }
        )
    }*/
  }

  public onExportSelectedXML() {

    let data = [];
    for(let account of this.accounts) {

      data.push({
        "firstName": account.firstName,
        "lastName": account.lastName,
        "email": account.email,
        "phone": account.phone,
        "crDate": account.dateCreated,
        "role": "USER"
      })
    }

    this.exportService.exportXML(data).subscribe(
      (response: Byte[]) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


}
