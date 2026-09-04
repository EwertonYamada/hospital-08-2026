import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PatientModel, PatientRequestModel } from './patient-model';

@Service()
export class PatientApi {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/patient';

  findAll(): Observable<PatientModel[]> {
    return this.http.get<PatientModel[]>(this.baseUrl);
  }

  findById(id: number): Observable<PatientModel> {
    return this.http.get<PatientModel>(`${this.baseUrl}/${id}`);
  }

  create(patient: PatientRequestModel): Observable<PatientModel> {
    return this.http.post<PatientModel>(this.baseUrl, patient);
  }

  linkMedicalInsurance(patientId: number, medicalInsuranceId: number): Observable<PatientModel> {
    return this.http.patch<PatientModel>(`${this.baseUrl}/${patientId}/medicalinsurance/${medicalInsuranceId}`, {});
  }
}