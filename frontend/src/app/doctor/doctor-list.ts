import { Component,inject,signal } from '@angular/core';
import { Doctor, DoctorService } from './doctor.service';

@Component({
  selector: 'app-doctor-list',
  template: `
    <h2>Médicos</h2>
    <button (click)="load()">Recarregar</button>
    @if (loading()) {
      <p>Carregando...</p>
    } @else if (error()) {
      <p style="color: red">Erro: {{ error() }}</p>
    } @else {
      <table border="1" cellpadding="6">
        <thead>
          <tr><th>ID</th><th>Nome</th><th>CRM</th><th>Especialidade</th></tr>
        </thead>
        <tbody>
          @for (d of doctors(); track d.id) {
            <tr>
              <td>{{ d.id }}</td>
              <td>{{ d.name }}</td>
              <td>{{ d.crm }}</td>
              <td>{{ d.specialty }}</td>
            </tr>
          } @empty {
            <tr><td colspan="4">Nenhum registro</td></tr>
          }
        </tbody>
      </table>
    }
  `,
})
export class DoctorList {
  private doctorService = inject(DoctorService);

  doctors = signal<Doctor[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);

  constructor() {
    this.load();
}

  load() {
    this.loading.set(true);
    this.error.set(null);
    this.doctorService.findAll().subscribe({
      next : (list) => {this.doctors.set(list); this.loading.set(false);},
      error : (error) => {this.error.set(error.message); this.loading.set(false);},
    });
  }
}
