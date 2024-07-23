import { Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {AuthorizedComponent} from "./components/authorized/authorized.component";
import {UsersComponent} from "./components/users/users.component";
import {AdminsComponent} from "./components/admins/admins.component";
import {LogoutComponent} from "./components/logout/logout.component";

export const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "authorized", component: AuthorizedComponent},
  {path: "users", component: UsersComponent},
  {path: "admins", component: AdminsComponent},
  {path: "logout", component: LogoutComponent},

  {path: "**", redirectTo: "", pathMatch: 'full'}
];
