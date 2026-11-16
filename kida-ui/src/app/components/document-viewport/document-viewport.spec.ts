import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DocumentViewport } from './document-viewport';

describe('DocumentViewport', () => {
  let component: DocumentViewport;
  let fixture: ComponentFixture<DocumentViewport>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DocumentViewport],
    }).compileComponents();

    fixture = TestBed.createComponent(DocumentViewport);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
