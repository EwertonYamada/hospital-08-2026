import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AdmissionApi } from '../admission-api';
import { AdmissionRequestModel } from '../admission-model';
import { PatientApi } from '../../patient/patient-api';
import { PatientModel } from '../../patient/patient-model';
import { BedApi } from '../../bed/bed-api';
import { BedModel } from '../../bed/bed-model';

@Component({
  imports: [FormsModule],
  selector: 'app-admission-form',
  styleUrl: './admission-form.css',
  templateUrl: './admission-form.html',
})
export class AdmissionForm implements OnInit {
  private readonly admissionApi = inject(AdmissionApi);
  private readonly patientApi = inject(PatientApi);
  private readonly bedApi = inject(BedApi);
  private readonly router = inject(Router);

  private readonly patients = signal<PatientModel[]>([]);
  private readonly beds = signal<BedModel[]>([]);

  protected readonly availablePatients = computed(() =>
    this.patients().filter(patient => !patient.hospitalized)
  );

  protected readonly availableBeds = computed(() =>
    this.beds().filter(bed => bed.status === 'UNOCCUPIED')
  );

  protected patientId = '';
  protected bedId = '';

  ngOnInit(): void {
    this.patientApi.findAll().subscribe(patients => this.patients.set(patients));
    this.bedApi.findAll().subscribe(beds => this.beds.set(beds));
  }

  protected save(): void {
    const admission: AdmissionRequestModel = {
      patientId: Number(this.patientId),
      bedId: Number(this.bedId)
    };

    this.admissionApi.create(admission).subscribe(() => {
      this.router.navigateByUrl('/internacao');
    });
  }
}