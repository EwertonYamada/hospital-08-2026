import { BedModel } from '../bed/bed-model';
import { PatientModel } from '../patient/patient-model';
import { DoctorModel } from '../doctor/doctor-model';

export interface AdmissionModel {
  id: number;
  bed: BedModel;
  patient: PatientModel;
  admittedAt: string;
  dischargedAt: string | null;
  doctors: DoctorModel[];
  status: 'ACTIVE' | 'INACTIVE';
}

export interface AdmissionRequestModel {
  bedId: number;
  patientId: number;
}