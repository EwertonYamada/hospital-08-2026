import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicalInsuranceApi } from '../medical-insurance-api';
import { MedicalInsuranceRequestModel, MedicalInsuranceType } from '../medical-insurance-model';

@Component({
  imports: [FormsModule],
  selector: 'app-medical-insurance-form',
  styleUrl: './medical-insurance-form.css',
  templateUrl: './medical-insurance-form.html',
})
export class MedicalInsuranceForm implements OnInit {
  private readonly medicalInsuranceApi = inject(MedicalInsuranceApi);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  private medicalInsuranceId: number | null = null;

  protected readonly types = Object.values(MedicalInsuranceType);

  protected type: MedicalInsuranceType | '' = '';
  protected coverageRate: number | null = null;

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.medicalInsuranceId = Number(idParam);
      this.medicalInsuranceApi.findById(this.medicalInsuranceId).subscribe(medicalInsurance => {
        this.type = medicalInsurance.type;
        this.coverageRate = medicalInsurance.coverageRate;
      });
    }
  }

  protected save(): void {
    const medicalInsurance: MedicalInsuranceRequestModel = {
      type: this.type as MedicalInsuranceType,
      coverageRate: this.coverageRate as number
    };

    const request$ = this.medicalInsuranceId
      ? this.medicalInsuranceApi.update(this.medicalInsuranceId, medicalInsurance)
      : this.medicalInsuranceApi.create(medicalInsurance);

    request$.subscribe(() => {
      this.router.navigateByUrl('/convenio');
    });
  }
}