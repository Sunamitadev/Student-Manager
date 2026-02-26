import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AlunoRowComponent } from './aluno-row-component';

fdescribe('AlunoRowComponentComponent', () => {
  let component: AlunoRowComponent;
  let fixture: ComponentFixture<AlunoRowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlunoRowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlunoRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  fit('should create', () => {
    expect(component).toBeTruthy();
  });
});
