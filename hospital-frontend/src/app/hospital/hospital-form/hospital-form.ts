import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HospitalApi } from '../hospital-api';
import { HospitalRequestModel } from '../hospital-model';

@Component({
  imports: [FormsModule],
  selector: 'app-hospital-form',
  styleUrl: './hospital-form.css',
  templateUrl: './hospital-form.html',
})
export class HospitalForm implements OnInit {
  private readonly hospitalApi = inject(HospitalApi);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  private hospitalId: number | null = null;

  protected name = '';
  protected cnpj = '';
  protected phoneNumber = '';

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.hospitalId = Number(idParam);
      this.hospitalApi.findById(this.hospitalId).subscribe(hospital => {
        this.name = hospital.name;
        this.cnpj = hospital.cnpj;
        this.phoneNumber = hospital.phoneNumber;
      });
    }
  }

  protected save(): void {
    const hospital: HospitalRequestModel = {
      name: this.name,
      cnpj: this.cnpj,
      phoneNumber: this.phoneNumber,
      specialties: []
    };

    const request$ = this.hospitalId
      ? this.hospitalApi.update(this.hospitalId, hospital)
      : this.hospitalApi.create(hospital);

    request$.subscribe(() => {
      this.router.navigateByUrl('/hospital');
    });
  }
}