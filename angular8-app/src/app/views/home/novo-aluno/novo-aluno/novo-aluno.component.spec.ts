import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NovoAlunoComponent } from './novo-aluno.component';
import { ReactiveFormsModule } from '@angular/forms';

describe('NovoAlunoComponent', () => {
  let component: NovoAlunoComponent;
  let fixture: ComponentFixture<NovoAlunoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NovoAlunoComponent ],
      imports: [ReactiveFormsModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NovoAlunoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
