import {Component, OnInit} from '@angular/core';
import {ResourcesService} from "../../services/resources.service";

@Component({
  selector: 'app-admins',
  standalone: true,
  imports: [],
  templateUrl: './admins.component.html',
  styleUrl: './admins.component.css'
})
export class AdminsComponent implements OnInit{
  message!: string;
  constructor(private resourceService: ResourcesService) {
  }

  ngOnInit(): void {
    this.resourceService.getAdminMessage().subscribe({
      next: res => this.message = res['message'],
      error: (err) => console.log(err),
      complete:() => console.log("done")
    });
  }
}
