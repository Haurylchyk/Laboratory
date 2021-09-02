import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {AuthenticationService} from '../service/authentication.service';
import {Observable} from 'rxjs';
import {UserRole} from '../model/enum/user-role';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private authService: AuthenticationService,
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let result = true;
    if (this.authService.isLoggedIn()) {
      const currentRole = this.authService.getUserRole();
      if (route.data.role && UserRole[route.data.role].toString() !== currentRole) {
        this.router.navigate(['error'],
          {queryParams: {errorCode: 403, errorMessage: 'Access is denied!'}, skipLocationChange: true});
        result = false;
      }
    } else {
      this.router.navigate(['/login']);
      result = false;
    }
    return result;
  }
}
