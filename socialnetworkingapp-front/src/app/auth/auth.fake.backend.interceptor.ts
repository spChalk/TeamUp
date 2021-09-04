/*
import {Injectable} from '@angular/core';
import {
  HTTP_INTERCEPTORS,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {delay, dematerialize, materialize, mergeMap} from 'rxjs/operators';
import {Account, AppUserRole} from "../account/account";

const users: Account[] = [{ id: 1, appUserRole: AppUserRole.USER, firstName: 'test', lastName: 'test',
                            email: 'test@gmail.com', password: 'test',
                            phone: '1212121212', imageUrl: 'image', followers: [], following: [] }];

@Injectable()
export class FakeBackendInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const { url, method, headers, body } = request;

    // wrap in delayed observable to simulate server api call
    return of(null)
      .pipe(mergeMap(handleRoute))
      .pipe(materialize()) // call materialize and dematerialize to ensure delay even if an error is thrown (https://github.com/Reactive-Extensions/RxJS/issues/648)
      .pipe(delay(500))
      .pipe(dematerialize());

    function handleRoute() {
      switch (true) {
        case url.endsWith('/login') && method === 'POST':
          return authenticate();
        case url.endsWith('/accounts/all') && method === 'GET':
          return getUsers();
        default:
          // pass through any requests not handled above
          return next.handle(request);
      }
    }

    // route functions

    function authenticate() {
      const { username, password } = body;
      const user = users.find(x => x.email === username && x.password === password);
      if (!user) return error('Username or password is incorrect');
      return ok(
        { id: user.id,
                appUserRole: user.appUserRole,
                firstName: user.firstName,
                lastName: user.lastName,
                email: user.email,
                token: 'fake-jwt-token'
        })
    }

    function getUsers() {
      if (!isLoggedIn()) return unauthorized();
      return ok(users);
    }

    // helper functions

    function ok(body?: any) {
      return of(new HttpResponse({ status: 200, body }))
    }

    function error(message: string) {
      return throwError({ error: { message } });
    }

    function unauthorized() {
      return throwError({ status: 401, error: { message: 'Unauthorised' } });
    }

    function isLoggedIn() {
      return headers.get('Authorization') === 'Bearer fake-jwt-token';
    }
  }
}

export let fakeBackendProvider = {
  // use fake backend in place of Http service for backend-less development
  provide: HTTP_INTERCEPTORS,
  useClass: FakeBackendInterceptor,
  multi: true
};
*/
