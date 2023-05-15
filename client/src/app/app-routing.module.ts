import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadComponent } from './components/upload.component';
// import { DetailsComponent } from './components/details.component';
import { MainComponent } from './components/main.component';



const routes: Routes = [
  { path:'', component: MainComponent },
  { path: 'upload', component: UploadComponent },
  { path: "**", redirectTo: "/", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
