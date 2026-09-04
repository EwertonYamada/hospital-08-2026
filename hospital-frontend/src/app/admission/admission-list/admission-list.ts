import { Component, OnInit, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AdmissionApi } from '../admission-api';
import { AdmissionModel } from '../admission-model';
import { DoctorApi } from '../../doctor/doctor-api';
import { DoctorModel } from '../../doctor/doctor-model';

@Component({
  imports: [RouterLink],
  selector: 'app-admission-list',
  styleUrl: './admission-list.css',
  templateUrl: './admission-list.html',
})
export class AdmissionList implements OnInit {
  private readonly admissionApi = inject(AdmissionApi);
  private readonly doctorApi = inject(DoctorApi);

  protected readonly admissions = signal<AdmissionModel[]>([]);
  protected readonly doctors = signal<DoctorModel[]>([]);

  ngOnInit(): void {
    this.loadAdmissions();
    this.doctorApi.findAll().subscribe(doctors => {
      this.doctors.set(doctors);
    });
  }

  protected discharge(admissionId: number): void {
    this.admissionApi.discharge(admissionId).subscribe(() => {
      this.loadAdmissions();
    });
  }

  protected linkDoctor(admissionId: number, doctorIdText: string): void {
    if (!doctorIdText) {
      return;
    }

    this.admissionApi.linkDoctor(admissionId, Number(doctorIdText)).subscribe(() => {
      this.loadAdmissions();
    });
  }

  private loadAdmissions(): void {
    this.admissionApi.findAll().subscribe(admissions => {
      this.admissions.set(admissions);
    });
  }
}