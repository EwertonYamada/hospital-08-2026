import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ServicesProvidedModel, ServicesProvidedRequestModel } from './services-provided-model';

@Service()
export class ServicesProvidedApi {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/servicesprovided';

  findAll(): Observable<ServicesProvidedModel[]> {
    return this.http.get<ServicesProvidedModel[]>(this.baseUrl);
  }

  findById(id: number): Observable<ServicesProvidedModel> {
    return this.http.get<ServicesProvidedModel>(`${this.baseUrl}/${id}`);
  }

  create(service: ServicesProvidedRequestModel): Observable<ServicesProvidedModel> {
    return this.http.post<ServicesProvidedModel>(this.baseUrl, service);
  }
}