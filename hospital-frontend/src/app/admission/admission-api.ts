import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AdmissionModel, AdmissionRequestModel } from './admission-model';

@Service()
export class AdmissionApi {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/admission';

  findAll(): Observable<AdmissionModel[]> {
    return this.http.get<AdmissionModel[]>(this.baseUrl);
  }

  findById(id: number): Observable<AdmissionModel> {
    return this.http.get<AdmissionModel>(`${this.baseUrl}/${id}`);
  }

  create(admission: AdmissionRequestModel): Observable<AdmissionModel> {
    return this.http.post<AdmissionModel>(this.baseUrl, admission);
  }

  discharge(admissionId: number): Observable<AdmissionModel> {
    return this.http.post<AdmissionModel>(`${this.baseUrl}/${admissionId}/discharge`, {});
  }

  linkDoctor(admissionId: number, doctorId: number): Observable<AdmissionModel> {
    return this.http.post<AdmissionModel>(`${this.baseUrl}/${admissionId}/doctors/${doctorId}`, {});
  }
}