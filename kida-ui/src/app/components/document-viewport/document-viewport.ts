import { Component } from '@angular/core';
import { DocumentUpload } from './document-upload/document-upload';
import { PromptInput } from './prompt-input/prompt-input';

@Component({
  imports: [DocumentUpload, PromptInput],
  selector: 'app-document-viewport',
  styleUrl: './document-viewport.css',
  templateUrl: './document-viewport.html',
})
export class DocumentViewport { }

