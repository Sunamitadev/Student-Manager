import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-aluno-row',
  templateUrl: './aluno-row-component.html',
  
 
})
export class AlunoRowComponent {

  @Input() aluno: any;

  @Output() ver = new EventEmitter<string>();
  @Output() editar = new EventEmitter<string>();
  @Output() deletar = new EventEmitter<string>();

}