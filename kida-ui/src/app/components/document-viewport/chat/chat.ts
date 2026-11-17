import { Component } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-chat',
  styleUrl: './chat.css',
  templateUrl: './chat.html',
})
export class Chat {
  private fileName: String = "Jaarverslag_2026_Definitief.pdf";
  private fileStatus: String = "Geanalyseerd";
  private classification: String = "Financieel rapport";
  private docId: String = "#DOS-8942-X";
  private docSize: String = "2.4 MB";
  private status: String = "Gekoppeld"

  private userMsg: String = "Hello.";
  private aiMsg: String = "Hi, welcome to KIDA";
}
