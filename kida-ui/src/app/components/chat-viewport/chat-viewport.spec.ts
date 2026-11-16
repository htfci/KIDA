import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ChatViewport } from './chat-viewport';

describe('ChatViewport', () => {
  let component: ChatViewport;
  let fixture: ComponentFixture<ChatViewport>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChatViewport],
    }).compileComponents();

    fixture = TestBed.createComponent(ChatViewport);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
