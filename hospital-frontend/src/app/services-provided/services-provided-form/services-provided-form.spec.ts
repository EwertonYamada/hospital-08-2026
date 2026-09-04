import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ServicesProvidedForm } from './services-provided-form';

describe('ServicesProvidedForm', () => {
  let component: ServicesProvidedForm;
  let fixture: ComponentFixture<ServicesProvidedForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicesProvidedForm],
    }).compileComponents();

    fixture = TestBed.createComponent(ServicesProvidedForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
