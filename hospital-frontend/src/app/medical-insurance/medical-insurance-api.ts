import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MedicalInsuranceModel, MedicalInsuranceRequestModel } from './medical-insurance-model';

@Service()
export class MedicalInsuranceApi {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/medicalInsurance';

  findAll(): Observable<MedicalInsuranceModel[]> {
    return this.http.get<MedicalInsuranceModel[]>(this.baseUrl);
  }

  findById(id: number): Observable<MedicalInsuranceModel> {
    return this.http.get<MedicalInsuranceModel>(`${this.baseUrl}/${id}`);
  }

  create(medicalInsurance: MedicalInsuranceRequestModel): Observable<MedicalInsuranceModel> {
    return this.http.post<MedicalInsuranceModel>(this.baseUrl, medicalInsurance);
  }

  update(id: number, medicalInsurance: MedicalInsuranceRequestModel): Observable<MedicalInsuranceModel> {
    return this.http.put<MedicalInsuranceModel>(`${this.baseUrl}/${id}`, medicalInsurance);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}