import { Component, OnInit, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PatientApi } from '../patient-api';
import { PatientModel } from '../patient-model';
import { MedicalInsuranceApi } from '../../medical-insurance/medical-insurance-api';
import { MedicalInsuranceModel } from '../../medical-insurance/medical-insurance-model';

@Component({
  imports: [RouterLink],
  selector: 'app-patient-list',
  styleUrl: './patient-list.css',
  templateUrl: './patient-list.html',
})
export class PatientList implements OnInit {
  private readonly patientApi = inject(PatientApi);
  private readonly medicalInsuranceApi = inject(MedicalInsuranceApi);

  protected readonly patients = signal<PatientModel[]>([]);
  protected readonly medicalInsurances = signal<MedicalInsuranceModel[]>([]);

  ngOnInit(): void {
    this.loadPatients();
    this.medicalInsuranceApi.findAll().subscribe(medicalInsurances => {
      this.medicalInsurances.set(medicalInsurances);
    });
  }

  protected linkInsurance(patientId: number, insuranceIdText: string): void {
    if (!insuranceIdText) {
      return;
    }

    this.patientApi.linkMedicalInsurance(patientId, Number(insuranceIdText)).subscribe(() => {
      this.loadPatients();
    });
  }

  private loadPatients(): void {
    this.patientApi.findAll().subscribe(patients => {
      this.patients.set(patients);
    });
  }
}