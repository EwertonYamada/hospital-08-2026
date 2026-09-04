import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DailyApi } from '../daily-api';
import { BedType, DailyRequestModel, Specialty } from '../daily-model';

@Component({
  imports: [FormsModule],
  selector: 'app-daily-form',
  styleUrl: './daily-form.css',
  templateUrl: './daily-form.html',
})
export class DailyForm implements OnInit {
  private readonly dailyApi = inject(DailyApi);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  private dailyId: number | null = null;

  protected readonly specialties = Object.values(Specialty);

  protected type: BedType | '' = '';
  protected specialty: Specialty | '' = '';
  protected value: number | null = null;

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.dailyId = Number(idParam);
      this.dailyApi.findById(this.dailyId).subscribe(daily => {
        this.type = daily.type;
        this.specialty = daily.specialty;
        this.value = daily.value;
      });
    }
  }

  protected save(): void {
    const daily: DailyRequestModel = {
      type: this.type as BedType,
      specialty: this.specialty as Specialty,
      value: this.value as number
    };

    const request$ = this.dailyId
      ? this.dailyApi.update(this.dailyId, daily)
      : this.dailyApi.create(daily);

    request$.subscribe(() => {
      this.router.navigateByUrl('/diaria');
    });
  }
}