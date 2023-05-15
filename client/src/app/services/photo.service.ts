import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UploadResults } from '../model/upload.results';
import { first, firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PhotoService {

  constructor(private httpClient: HttpClient) { }

  upload(form: any){
    const formData = new FormData();
    formData.set("name", form['name']);
    formData.set("title", form['title']);
    formData.set("comments", form['comments']);
    formData.set("file", form['file']);
    
    return firstValueFrom(this.httpClient.post<UploadResults>("/upload", formData));
  }
}
