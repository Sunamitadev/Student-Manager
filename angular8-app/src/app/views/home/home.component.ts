import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Aluno } from 'src/app/models/aluno.model';
import { StudentService } from 'src/app/models/student.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  alunos: Aluno[] = [];
  filtro: string = '';

  constructor(
    private studentService: StudentService,
    private router: Router
  ) {}
ngOnInit(): void {
  this.carregarAlunos();
}

carregarAlunos(): void {
  this.studentService.listar().subscribe({
    next: (response: Aluno[]) => {
      console.log("ALUNOS RECEBIDOS:", response);
      this.alunos = response;
    },
    error: (err) => console.error(err)
  });
}

  get alunosFiltrados(): Aluno[] {
    if (!this.filtro.trim()) {
      return this.alunos;
    }

    const termo = this.filtro.toLowerCase();

    return this.alunos.filter(a =>
      a.nome.toLowerCase().includes(termo) ||
      a.matricula.toLowerCase().includes(termo)
    );
  }

  irParaNovoAluno(): void {
    this.router.navigate(['/novo-aluno']);
  }

}