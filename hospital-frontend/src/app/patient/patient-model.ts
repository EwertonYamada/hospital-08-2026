import { MedicalInsuranceModel } from '../medical-insurance/medical-insurance-model';

export interface PatientModel {
  id: number;
  name: string;
  document: string;
  birthDate: string;
  hospitalized: boolean;
  medicalInsurance: MedicalInsuranceModel | null;
}

export interface PatientRequestModel {
  name: string;
  document: string;
  birthDate: string;
}