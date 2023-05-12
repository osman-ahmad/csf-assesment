import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
// import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})

export class UploadComponent implements OnInit {
  uploadPhoto!: FormGroup;

  constructor(private fb: FormBuilder) {}

  // ngOnInit() {
  //   this.uploadPhoto = this.fb.group({
  //     name: [''],
  //     title: [''],
  //     comments: [''],
  //     archive: [null]
  //   });
  // }
  ngOnInit(): void {
      this.uploadPhoto = this.fb.group({
      name: this.fb.control<string>(''),
      title: this.fb.control<string>(''),
      complain: this.fb.control<string>('')
      
    })
    
  }
  upload() {
    const formData = new FormData();
    formData.append('name', this.uploadPhoto.value.name);
    formData.append('title', this.uploadPhoto.value.title);
    formData.append('comments', this.uploadPhoto.value.comments);
    formData.append('archive', this.uploadPhoto.value.archive);

    // Here, you can make an HTTP request to send the form data to the backend
    // For example, using Angular's HttpClient
    // this.http.post('your-backend-url', formData).subscribe(response => {
    //   console.log(response);
    //   // Handle response or any other necessary logic
    // }, error => {
    //   console.error(error);
    //   // Handle error or any other necessary logic
    // });
  }
}
