import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DoctorModel } from './doctor-model';

@Service()
export class DoctorApi {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/doctor';

  findAll(): Observable<DoctorModel[]> {
    return this.http.get<DoctorModel[]>(this.baseUrl);
  }
}