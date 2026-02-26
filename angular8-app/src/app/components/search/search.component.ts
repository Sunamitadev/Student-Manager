import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  filtro: string = '';

  @Output() filtroMudou = new EventEmitter<string>();

  onInputChange() {
    this.filtroMudou.emit(this.filtro);
  }
}