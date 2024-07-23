import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NgIf} from "@angular/common";
import {AuthService} from "../../services/auth.service";

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
    private authService: AuthService) {
    this.code = null;
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(data => {
      this.code = data['code'];
      this.getToken();
    })
  }

  private getToken() {
    this.authService.getToken(this.code!).subscribe({
      next: res => console.log(res),
      error: err => {
        console.log(err)
        return new Error(err.toString());
      },
      complete: () => console.log("done!")
    })
  }
}
