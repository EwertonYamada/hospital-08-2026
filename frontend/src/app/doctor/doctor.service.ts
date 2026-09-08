import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Doctor {
  id: number;
  name: string;
  crm: string;
  specialty: string;
}

@Injectable({providedIn: 'root'})
export class DoctorService {
  private http = inject(HttpClient);
  private url = '/api/doctor';

  findAll() {return this.http.get<Doctor[]>(this.url);}
  getById(id: number) {return this.http.get<Doctor>(`${this.url}/${id}`); }
  create(body: Omit<Doctor, 'id'>) {return this.http.post<Doctor>(this.url, body);}
}
