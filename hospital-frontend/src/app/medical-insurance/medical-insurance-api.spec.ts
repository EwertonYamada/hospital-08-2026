import { TestBed } from '@angular/core/testing';
import { MedicalInsuranceApi } from './medical-insurance-api';

describe('MedicalInsuranceApi', () => {
  let service: MedicalInsuranceApi;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MedicalInsuranceApi);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
