import { Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {AuthorizedComponent} from "./components/authorized/authorized.component";
import {UsersComponent} from "./components/users/users.component";
import {AdminsComponent} from "./components/admins/admins.component";

export const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "authorized", component: AuthorizedComponent},
  {path: "users", component: UsersComponent},
  {path: "admins", component: AdminsComponent},
  {path: "**", redirectTo: "", pathMatch: 'full'}
];
