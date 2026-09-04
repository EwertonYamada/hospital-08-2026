import { TestBed } from '@angular/core/testing';
import { ServicesProvidedApi } from './services-provided-api';

describe('ServicesProvidedApi', () => {
  let service: ServicesProvidedApi;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicesProvidedApi);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
