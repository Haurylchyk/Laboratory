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
    const isLoggedIn = this.authService.isLoggedIn();
    if (isLoggedIn) {
      const currentRole = this.authService.getUserRole();
      if (route.data.role && UserRole[route.data.role].toString() !== currentRole) {
        this.router.navigateByUrl('error/403/Access is denied!');
        return false;
      }
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

}
