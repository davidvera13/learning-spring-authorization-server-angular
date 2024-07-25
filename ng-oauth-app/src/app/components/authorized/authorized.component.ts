import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgIf} from "@angular/common";
import {AuthService} from "../../services/auth.service";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-authorized',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './authorized.component.html',
  styleUrl: './authorized.component.css'
})
export class AuthorizedComponent implements OnInit{
  code: string | null;
  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private tokenService: TokenService) {
    this.code = null;
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(data => {
      this.code = data['code'];
      this.getToken();
    })
  }

  private getToken() {
    const codeVerifier = this.tokenService.getVerifier();
    this.tokenService.deleteVerifier();
    this.authService.getToken(this.code!, codeVerifier).subscribe({
      next: res => {
        console.log(res);
        this.tokenService.setToken(res['access_token'], res['refresh_token']);
        this.router.navigate(['..'])
      },
      error: err => {
        console.log(err)
        return new Error(err.toString());
      },
      complete: () => console.log("done!")
    })
  }
}
