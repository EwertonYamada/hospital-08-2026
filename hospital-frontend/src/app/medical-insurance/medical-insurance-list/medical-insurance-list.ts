import { Component, OnInit, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MedicalInsuranceApi } from '../medical-insurance-api';
import { MedicalInsuranceModel } from '../medical-insurance-model';

@Component({
  imports: [RouterLink],
  selector: 'app-medical-insurance-list',
  styleUrl: './medical-insurance-list.css',
  templateUrl: './medical-insurance-list.html',
})
export class MedicalInsuranceList implements OnInit {
  private readonly medicalInsuranceApi = inject(MedicalInsuranceApi);
  protected readonly medicalInsurances = signal<MedicalInsuranceModel[]>([]);

  ngOnInit(): void {
    this.loadMedicalInsurances();
  }

  protected delete(id: number): void {
    this.medicalInsuranceApi.delete(id).subscribe(() => {
      this.loadMedicalInsurances();
    });
  }

  private loadMedicalInsurances(): void {
    this.medicalInsuranceApi.findAll().subscribe(medicalInsurances => {
      this.medicalInsurances.set(medicalInsurances);
    });
  }
}