import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MedicalInsuranceList } from './medical-insurance-list';

describe('MedicalInsuranceList', () => {
  let component: MedicalInsuranceList;
  let fixture: ComponentFixture<MedicalInsuranceList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedicalInsuranceList],
    }).compileComponents();

    fixture = TestBed.createComponent(MedicalInsuranceList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
