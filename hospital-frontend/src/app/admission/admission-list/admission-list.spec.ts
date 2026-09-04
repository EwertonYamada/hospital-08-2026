import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdmissionList } from './admission-list';

describe('AdmissionList', () => {
  let component: AdmissionList;
  let fixture: ComponentFixture<AdmissionList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdmissionList],
    }).compileComponents();

    fixture = TestBed.createComponent(AdmissionList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
