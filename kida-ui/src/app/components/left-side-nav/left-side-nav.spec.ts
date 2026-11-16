import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LeftSideNav } from './left-side-nav';

describe('LeftSideNav', () => {
  let component: LeftSideNav;
  let fixture: ComponentFixture<LeftSideNav>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeftSideNav],
    }).compileComponents();

    fixture = TestBed.createComponent(LeftSideNav);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
