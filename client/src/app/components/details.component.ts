import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ArchiveRepository } from '../models/archive.repository';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})

export class DetailsComponent implements OnInit {

  formData: any;
   

  constructor(private router: Router, private uploadService: UploadService) {

    this.formData = this.uploadService.getFormData();
  }

  backToMain() {
    this.router.navigate(['/']);
  }
}



