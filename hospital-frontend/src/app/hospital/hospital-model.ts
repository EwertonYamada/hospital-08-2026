export interface HospitalModel {
  id: number;
  name: string;
  cnpj: string;
  phoneNumber: string;
}

export interface HospitalRequestModel {
  name: string;
  cnpj: string;
  phoneNumber: string;
  specialties: unknown[];
}