export enum MedicalInsuranceType {
  UNIMED = 'UNIMED',
  SANTA_RITA = 'SANTA_RITA',
  SANTA_CASA = 'SANTA_CASA',
  BRADESCO_SAUDE = 'BRADESCO_SAUDE',
  AMIL = 'AMIL',
  SUL_AMERICANA = 'SUL_AMERICANA',
  PORTO_SEGURO = 'PORTO_SEGURO'
}

export interface MedicalInsuranceModel {
  id: number;
  type: MedicalInsuranceType;
  coverageRate: number;
}

export interface MedicalInsuranceRequestModel {
  type: MedicalInsuranceType;
  coverageRate: number;
}