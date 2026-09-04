export interface BedModel {
  id: number;
  bedNumber: number;
  status: 'OCCUPIED' | 'UNOCCUPIED' | 'IN_PREPARATION';
  bedType: 'INFIRMARY' | 'UTI';
}