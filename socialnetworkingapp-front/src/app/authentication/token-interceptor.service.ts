import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from '.';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {

  private auth = "Authorization";
  private tokenPrefix = "Bearer ";
  constructor(private authenticationService : AuthenticationService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler):
  Observable<HttpEvent<any>>{

    //take token
    const token = this.authenticationService.getJWT();

    //take headers of http request
    let newHeaders = req.headers;

    //attach token
    if(token){
        newHeaders= newHeaders.append(this.auth, this.tokenPrefix + token);
        newHeaders= newHeaders.append("Access-Control-Allow-Origin","https://localhost:4200");
    }
    //clone this request with the new headers
    const authRequest = req.clone({headers: newHeaders});

    //return the observable that will run the request
    //also pass it to next interceptor if any
    return next.handle(authRequest);
  }
}
