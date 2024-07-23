import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NgIf} from "@angular/common";

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
  constructor(private activatedRoute: ActivatedRoute) {
    this.code = null;
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(data => {
      this.code = data['code'];
    })
  }



}
