import { Component } from '@angular/core';
import { PromptInput } from './prompt-input/prompt-input';
import { Header } from './header/header'
import { Chat } from './chat/chat';

@Component({
  imports: [PromptInput, Header, Chat],
  selector: 'app-document-viewport',
  styleUrl: './document-viewport.css',
  templateUrl: './document-viewport.html',
})
export class DocumentViewport { }

