import { Component, OnInit, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DailyApi } from '../daily-api';
import { DailyModel } from '../daily-model';

@Component({
  imports: [RouterLink],
  selector: 'app-daily-list',
  styleUrl: './daily-list.css',
  templateUrl: './daily-list.html',
})
export class DailyList implements OnInit {
  private readonly dailyApi = inject(DailyApi);
  protected readonly dailies = signal<DailyModel[]>([]);

  ngOnInit(): void {
    this.loadDailies();
  }

  protected delete(id: number): void {
    this.dailyApi.delete(id).subscribe(() => {
      this.loadDailies();
    });
  }

  private loadDailies(): void {
    this.dailyApi.findAll().subscribe(dailies => {
      this.dailies.set(dailies);
    });
  }
}