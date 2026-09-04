import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ServicesProvidedList } from './services-provided-list';

describe('ServicesProvidedList', () => {
  let component: ServicesProvidedList;
  let fixture: ComponentFixture<ServicesProvidedList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicesProvidedList],
    }).compileComponents();

    fixture = TestBed.createComponent(ServicesProvidedList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
