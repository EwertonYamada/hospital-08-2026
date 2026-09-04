import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BedModel } from './bed-model';

@Service()
export class BedApi {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/bed';

  findAll(): Observable<BedModel[]> {
    return this.http.get<BedModel[]>(this.baseUrl);
  }
}