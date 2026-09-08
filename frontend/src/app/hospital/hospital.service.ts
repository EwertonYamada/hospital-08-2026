import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Ward {
  id: number;
  specialty: string;
}

export interface Hospital {
  id: number;
  name: string;
  cnpj: string;
  phoneNumber: string;
  wards: Ward[];
}

export interface HospitalRequest {
  name: string;
  cnpj: string;
  phoneNumber: string;
  specialties: {
    specialty: string;
    roomRequest: {
      numberOfRooms: number;
      bedRequest: { numberOfBeds: number };
    };
  }[];
}

@Injectable({providedIn: 'root'})
export class HospitalService {
  private http = inject(HttpClient);
  private url = '/api/hospital';

  findAll() {return this.http.get<Hospital[]>(this.url);}
  getById(id: number) {return this.http.get<Hospital>(`${this.url}/${id}`); }
  create(body: HospitalRequest) {return this.http.post<Hospital>(this.url, body);}
  update(id: number, body: HospitalRequest) {return this.http.put<Hospital>(`${this.url}/${id}`, body);}
  remove(id: number) {return this.http.delete<void>(`${this.url}/${id}`); }
}
