import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DailyList } from './daily-list';

describe('DailyList', () => {
  let component: DailyList;
  let fixture: ComponentFixture<DailyList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DailyList],
    }).compileComponents();

    fixture = TestBed.createComponent(DailyList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
