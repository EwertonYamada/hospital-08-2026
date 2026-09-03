import { Component, OnInit, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HospitalApi } from '../hospital-api';
import { HospitalModel } from '../hospital-model';

@Component({
  imports: [RouterLink],
  selector: 'app-hospital-list',
  styleUrl: './hospital-list.css',
  templateUrl: './hospital-list.html',
})
export class HospitalList implements OnInit {
  private readonly hospitalApi = inject(HospitalApi);
  protected readonly hospitals = signal<HospitalModel[]>([]);

  ngOnInit(): void {
    this.loadHospitals();
  }

  protected delete(id: number): void {
    this.hospitalApi.delete(id).subscribe(() => {
      this.loadHospitals();
    });
  }

  private loadHospitals(): void {
    this.hospitalApi.findAll().subscribe(hospitals => {
      this.hospitals.set(hospitals);
    });
  }
}