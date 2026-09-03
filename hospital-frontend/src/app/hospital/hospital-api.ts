import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HospitalModel, HospitalRequestModel } from './hospital-model';

@Service()
export class HospitalApi {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/hospital';

  findAll(): Observable<HospitalModel[]> {
    return this.http.get<HospitalModel[]>(this.baseUrl);
  }

  findById(id: number): Observable<HospitalModel> {
    return this.http.get<HospitalModel>(`${this.baseUrl}/${id}`);
  }

  create(hospital: HospitalRequestModel): Observable<HospitalModel> {
    return this.http.post<HospitalModel>(this.baseUrl, hospital);
  }

  update(id: number, hospital: HospitalRequestModel): Observable<HospitalModel> {
    return this.http.put<HospitalModel>(`${this.baseUrl}/${id}`, hospital);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}