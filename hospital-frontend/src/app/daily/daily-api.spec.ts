import { TestBed } from '@angular/core/testing';
import { DailyApi } from './daily-api';

describe('DailyApi', () => {
  let service: DailyApi;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DailyApi);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
