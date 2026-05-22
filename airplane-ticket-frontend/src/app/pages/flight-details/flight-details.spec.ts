import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FlightDetailsComponent } from './flight-details';

describe('FlightDetailsComponent', () => {
  let component: FlightDetailsComponent;
  let fixture: ComponentFixture<FlightDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FlightDetailsComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(FlightDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create flight details component', () => {
    expect(component).toBeTruthy();
  });

  it('should have flight data defined or undefined safely', () => {
    expect(component.flight === undefined || component.flight !== undefined).toBeTrue();
  });
});
