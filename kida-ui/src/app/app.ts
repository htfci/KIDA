import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DocumentList } from './components/document-list/document-list';
import { DocumentViewport } from './components/document-viewport/document-viewport';
import { DocumentActions } from './components/document-actions/document-actions';

@Component({
  imports: [RouterOutlet, DocumentList, DocumentViewport, DocumentActions],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('kida-ui');
}
