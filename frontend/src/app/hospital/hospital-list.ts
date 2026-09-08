import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Hospital, HospitalRequest, HospitalService } from './hospital.service';

@Component({
  selector: 'app-hospital-list',
  imports: [FormsModule],
  template: `
    <h2>Hospitais</h2>

    @if (error()) {
      <p style="color: red">Erro: {{ error() }}</p>
    }

    <fieldset>
      <legend>{{ editingId() === null ? 'Novo hospital' : 'Editando #' + editingId() }}</legend>
      <p><label>Nome: <input [(ngModel)]="form.name" /></label></p>
      <p><label>CNPJ: <input [(ngModel)]="form.cnpj" /></label></p>
      <p><label>Telefone: <input [(ngModel)]="form.phoneNumber" /></label></p>

      @if (editingId() === null) {
        <p>
          <label>Ala inicial:
            <select [(ngModel)]="wardSpecialty">
              <option value="">(nenhuma)</option>
              @for (s of specialties; track s) {
                <option [value]="s">{{ s }}</option>
              }
            </select>
          </label>
          <label>Quartos: <input type="number" [(ngModel)]="numberOfRooms" /></label>
          <label>Leitos por quarto: <input type="number" [(ngModel)]="numberOfBeds" /></label>
        </p>
      }

      <button (click)="save()">{{ editingId() === null ? 'Criar' : 'Salvar' }}</button>
      @if (editingId() !== null) {
        <button (click)="cancel()">Cancelar</button>
      }
    </fieldset>

    <p><button (click)="load()">Recarregar</button></p>

    @if (loading()) {
      <p>Carregando...</p>
    } @else {
      <table border="1" cellpadding="6">
        <thead>
          <tr>
            <th>ID</th><th>Nome</th><th>CNPJ</th><th>Telefone</th><th>Alas</th><th>Ações</th>
          </tr>
        </thead>
        <tbody>
          @for (h of hospitals(); track h.id) {
            <tr>
              <td>{{ h.id }}</td>
              <td>{{ h.name }}</td>
              <td>{{ h.cnpj }}</td>
              <td>{{ h.phoneNumber }}</td>
              <td>{{ h.wards?.length ?? 0 }}</td>
              <td>
                <button (click)="startEdit(h)">Editar</button>
                <button (click)="remove(h)">Apagar</button>
              </td>
            </tr>
          } @empty {
            <tr><td colspan="6">Nenhum registro</td></tr>
          }
        </tbody>
      </table>
    }
  `,
})
export class HospitalList {
  private hospitalService = inject(HospitalService);

  readonly specialties = ['CARDIOLOGY', 'PEDIATRICS', 'NEUROLOGY', 'ORTHOPEDICS', 'ONCOLOGY'];

  hospitals = signal<Hospital[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);
  editingId = signal<number | null>(null);

  form = { name: '', cnpj: '', phoneNumber: '' };
  wardSpecialty = '';
  numberOfRooms = 1;
  numberOfBeds = 2;

  constructor() {
    this.load();
  }

  load() {
    this.loading.set(true);
    this.error.set(null);
    this.hospitalService.findAll().subscribe({
      next: (list) => { this.hospitals.set(list); this.loading.set(false); },
      error: (err) => { this.showError(err); this.loading.set(false); },
    });
  }

  save() {
    const id = this.editingId();
    const body = this.buildRequest();

    const call = id === null
      ? this.hospitalService.create(body)
      : this.hospitalService.update(id, body);

    call.subscribe({
      next: () => { this.cancel(); this.load(); },
      error: (err) => this.showError(err),
    });
  }

  startEdit(h: Hospital) {
    this.editingId.set(h.id);
    this.form = { name: h.name, cnpj: h.cnpj, phoneNumber: h.phoneNumber };
    this.error.set(null);
  }

  cancel() {
    this.editingId.set(null);
    this.form = { name: '', cnpj: '', phoneNumber: '' };
    this.wardSpecialty = '';
  }

  remove(h: Hospital) {
    if (!confirm(`Apagar "${h.name}"?`)) return;
    this.hospitalService.remove(h.id).subscribe({
      next: () => this.load(),
      error: (err) => this.showError(err),
    });
  }

  private buildRequest(): HospitalRequest {
    return {
      ...this.form,
      specialties: this.wardSpecialty
        ? [{
            specialty: this.wardSpecialty,
            roomRequest: {
              numberOfRooms: Number(this.numberOfRooms),
              bedRequest: { numberOfBeds: Number(this.numberOfBeds) },
            },
          }]
        : [],
    };
  }

  private showError(err: any) {
    this.error.set(`${err.status} - ${err.error?.message || err.message}`);
  }
}
