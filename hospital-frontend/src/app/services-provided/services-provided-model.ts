export enum ServicesType {
  DRUG = 'DRUG',
  EXAM = 'EXAM',
  DAILY = 'DAILY'
}

export interface ServicesProvidedModel {
  id: number;
  admissionId: number;
  doctorId: number | null;
  type: ServicesType;
  count: number;
  unitValue: number;
  totalValue: number;
  drugId: number | null;
  dailyId: number | null;
  examId: number | null;
  createdAt: string;
}

export interface ServicesProvidedRequestModel {
  admissionId: number;
  doctorId: number | null;
  type: ServicesType;
  count: number;
  drugId: number | null;
  dailyId: number | null;
  examId: number | null;
}