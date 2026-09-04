import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ServicesProvidedApi } from '../services-provided-api';
import { ServicesProvidedRequestModel, ServicesType } from '../services-provided-model';
import { AdmissionApi } from '../../admission/admission-api';
import { AdmissionModel } from '../../admission/admission-model';
import { DoctorApi } from '../../doctor/doctor-api';
import { DoctorModel } from '../../doctor/doctor-model';
import { DailyApi } from '../../daily/daily-api';
import { DailyModel } from '../../daily/daily-model';

@Component({
  imports: [FormsModule],
  selector: 'app-services-provided-form',
  styleUrl: './services-provided-form.css',
  templateUrl: './services-provided-form.html',
})
export class ServicesProvidedForm implements OnInit {
  private readonly servicesProvidedApi = inject(ServicesProvidedApi);
  private readonly admissionApi = inject(AdmissionApi);
  private readonly doctorApi = inject(DoctorApi);
  private readonly dailyApi = inject(DailyApi);
  private readonly router = inject(Router);

  private readonly admissions = signal<AdmissionModel[]>([]);
  protected readonly activeAdmissions = computed(() =>
    this.admissions().filter(admission => admission.status === 'ACTIVE')
  );
  protected readonly doctors = signal<DoctorModel[]>([]);
  protected readonly dailies = signal<DailyModel[]>([]);

  protected admissionId = '';
  protected doctorId = '';
  protected type: ServicesType | '' = '';
  protected count: number | null = null;
  protected relatedId = '';
  protected dailyId = '';

  ngOnInit(): void {
    this.admissionApi.findAll().subscribe(admissions => this.admissions.set(admissions));
    this.doctorApi.findAll().subscribe(doctors => this.doctors.set(doctors));
    this.dailyApi.findAll().subscribe(dailies => this.dailies.set(dailies));
  }

  protected save(): void {
    const request: ServicesProvidedRequestModel = {
      admissionId: Number(this.admissionId),
      doctorId: this.doctorId ? Number(this.doctorId) : null,
      type: this.type as ServicesType,
      count: Number(this.count),
      drugId: null,
      dailyId: null,
      examId: null
    };

    switch (request.type) {
      case ServicesType.DRUG:
        request.drugId = Number(this.relatedId);
        break;
      case ServicesType.EXAM:
        request.examId = Number(this.relatedId);
        break;
      case ServicesType.DAILY:
        request.dailyId = Number(this.dailyId);
        break;
    }

    this.servicesProvidedApi.create(request).subscribe(() => {
      this.router.navigateByUrl('/servicos');
    });
  }
}