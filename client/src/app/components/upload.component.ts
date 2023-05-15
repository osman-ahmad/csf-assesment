import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PhotoService } from '../services/photo.service';
// import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})

export class UploadComponent implements OnInit {
  uploadPhoto!: FormGroup;
  blob!: Blob;

  constructor(private router: Router, private fb: FormBuilder, 
    private photosvc: PhotoService) {}

    
       ngOnInit(): void {
      this.uploadPhoto = this.createForm();
    }
  
    createForm() {
      return this.fb.group({
        name: this.fb.control<string>('', [Validators.required]),
        title: this.fb.control<string>('', [Validators.required]),
        comments: this.fb.control<string>(''),
        archive: this.fb.control('', [Validators.required]),
      });
    }
    
    onFileChange(event: any) {
      const file = event.target.files[0];
      this.blob = file;
    } 

    upload() {  
      const formData = new FormData();
      formData.set("name", this.uploadPhoto.value['name']);
      formData.set("title", this.uploadPhoto.value['title']);
      formData.set("comments", this.uploadPhoto.value['comments']);
      formData.set("file", this.blob);

      this.photosvc.upload(formData)
    .then((val: any) => {  
      console.log(val);
    })
    .catch((error: any) => {
      console.log(error);
    });
      

  }


}