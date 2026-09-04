import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DailyModel, DailyRequestModel } from './daily-model';

@Service()
export class DailyApi {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/daily';

  findAll(): Observable<DailyModel[]> {
    return this.http.get<DailyModel[]>(`${this.baseUrl}/all`);
  }

  findById(id: number): Observable<DailyModel> {
    return this.http.get<DailyModel>(`${this.baseUrl}/${id}`);
  }

  create(daily: DailyRequestModel): Observable<DailyModel> {
    return this.http.post<DailyModel>(this.baseUrl, daily);
  }

  update(id: number, daily: DailyRequestModel): Observable<DailyModel> {
    return this.http.put<DailyModel>(`${this.baseUrl}/${id}`, daily);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}