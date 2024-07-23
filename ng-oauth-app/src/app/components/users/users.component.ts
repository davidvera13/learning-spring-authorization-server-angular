import {Component, OnInit} from '@angular/core';
import {ResourcesService} from "../../services/resources.service";

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit{
  message!: string;
  constructor(private resourceService: ResourcesService) {

  }

  ngOnInit(): void {
    this.resourceService.getUserMessage().subscribe({
      next: res => this.message = res['message'],
      error: (err) => console.log(err),
      complete:() => console.log("done")
    });
  }



}

