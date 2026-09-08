import { Routes } from '@angular/router';

export const routes: Routes = [
  {path: '', redirectTo: 'doctor', pathMatch: 'full'},
  {path: 'doctor', loadComponent: () => import('./doctor/doctor-list').then(m => m.DoctorList)},
  {path: 'hospital', loadComponent: () => import('./hospital/hospital-list').then(m => m.HospitalList)},
];
