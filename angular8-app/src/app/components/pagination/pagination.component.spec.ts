import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PaginationComponent } from './pagination.component';
import { By } from '@angular/platform-browser';

fdescribe('PaginationComponent', () => {
  let component: PaginationComponent;
  let fixture: ComponentFixture<PaginationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaginationComponent ]
    }).compileComponents();

    fixture = TestBed.createComponent(PaginationComponent);
    component = fixture.componentInstance;
  });

  fit('deve emitir evento ao mudar página', () => {

    spyOn(component.paginaMudou, 'emit');

    component.paginaAtual = 0;
    component.totalPaginas = 5;

    component.irParaPagina(1);

    expect(component.paginaMudou.emit).toHaveBeenCalledWith(1);
  });
});