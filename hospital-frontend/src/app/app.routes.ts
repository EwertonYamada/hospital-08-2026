import { Routes } from '@angular/router';
import { HospitalList } from './hospital/hospital-list/hospital-list';
import { HospitalForm } from './hospital/hospital-form/hospital-form';
import { MedicalInsuranceList } from './medical-insurance/medical-insurance-list/medical-insurance-list';
import { MedicalInsuranceForm } from './medical-insurance/medical-insurance-form/medical-insurance-form';
import { PatientList } from './patient/patient-list/patient-list';
import { PatientForm } from './patient/patient-form/patient-form';
import { AdmissionList } from './admission/admission-list/admission-list';
import { AdmissionForm } from './admission/admission-form/admission-form';

export const routes: Routes = [
  { path: 'hospital', component: HospitalList },
  { path: 'hospital/novo', component: HospitalForm },
  { path: 'hospital/editar/:id', component: HospitalForm },
  { path: 'convenio', component: MedicalInsuranceList },
  { path: 'convenio/novo', component: MedicalInsuranceForm },
  { path: 'convenio/editar/:id', component: MedicalInsuranceForm },
  { path: 'paciente', component: PatientList },
  { path: 'paciente/novo', component: PatientForm },
  { path: 'internacao', component: AdmissionList },
{ path: 'internacao/novo', component: AdmissionForm },
];