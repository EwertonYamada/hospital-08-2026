import { Component, OnInit, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ServicesProvidedApi } from '../services-provided-api';
import { ServicesProvidedModel } from '../services-provided-model';

@Component({
  imports: [RouterLink],
  selector: 'app-services-provided-list',
  styleUrl: './services-provided-list.css',
  templateUrl: './services-provided-list.html',
})
export class ServicesProvidedList implements OnInit {
  private readonly servicesProvidedApi = inject(ServicesProvidedApi);
  protected readonly services = signal<ServicesProvidedModel[]>([]);

  ngOnInit(): void {
    this.servicesProvidedApi.findAll().subscribe(services => {
      this.services.set(services);
    });
  }
}