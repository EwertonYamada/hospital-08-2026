import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MedicalInsuranceForm } from './medical-insurance-form';

describe('MedicalInsuranceForm', () => {
  let component: MedicalInsuranceForm;
  let fixture: ComponentFixture<MedicalInsuranceForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedicalInsuranceForm],
    }).compileComponents();

    fixture = TestBed.createComponent(MedicalInsuranceForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
