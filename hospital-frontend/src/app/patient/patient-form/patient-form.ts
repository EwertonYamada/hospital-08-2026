import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PatientApi } from '../patient-api';
import { PatientRequestModel } from '../patient-model';

@Component({
  imports: [FormsModule],
  selector: 'app-patient-form',
  styleUrl: './patient-form.css',
  templateUrl: './patient-form.html',
})
export class PatientForm {
  private readonly patientApi = inject(PatientApi);
  private readonly router = inject(Router);

  protected name = '';
  protected document = '';
  protected birthDate = '';

  protected save(): void {
    const patient: PatientRequestModel = {
      name: this.name,
      document: this.document,
      birthDate: this.birthDate
    };

    this.patientApi.create(patient).subscribe(() => {
      this.router.navigateByUrl('/paciente');
    });
  }
}