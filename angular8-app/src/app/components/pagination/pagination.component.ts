import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {

  @Input() paginaAtual: number = 0;
  @Input() totalPaginas: number = 0;
  @Input() ultimaPagina: boolean = false;

  @Output() paginaMudou = new EventEmitter<number>();

  paginaAnterior(): void {
    if (this.paginaAtual > 0) {
      this.paginaMudou.emit(this.paginaAtual - 1);
    }
  }

  proximaPagina(): void {
    if (!this.ultimaPagina) {
      this.paginaMudou.emit(this.paginaAtual + 1);
    }
  }

  irParaPagina(pagina: number): void {
    this.paginaMudou.emit(pagina);
  }

}