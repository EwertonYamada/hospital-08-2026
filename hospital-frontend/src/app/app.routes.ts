import { Routes } from '@angular/router';
import { HospitalList } from './hospital/hospital-list/hospital-list';
import { HospitalForm } from './hospital/hospital-form/hospital-form';
import { MedicalInsuranceList } from './medical-insurance/medical-insurance-list/medical-insurance-list';
import { MedicalInsuranceForm } from './medical-insurance/medical-insurance-form/medical-insurance-form';

export const routes: Routes = [
  { path: 'hospital', component: HospitalList },
  { path: 'hospital/novo', component: HospitalForm },
  { path: 'hospital/editar/:id', component: HospitalForm },
  { path: 'convenio', component: MedicalInsuranceList },
  { path: 'convenio/novo', component: MedicalInsuranceForm },
  { path: 'convenio/editar/:id', component: MedicalInsuranceForm },
];