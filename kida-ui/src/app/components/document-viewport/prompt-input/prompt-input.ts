import { Component } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-prompt-input',
  styleUrl: './prompt-input.css',
  templateUrl: './prompt-input.html',
})
export class PromptInput {
  private isOpenTools: boolean = false;
  
  constructor() {
    
  }

  public uploadFile(): void { }

  public summarize(): void { }

  public translate(): void { }

}
