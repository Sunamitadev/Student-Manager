import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentService } from 'src/app/models/student.service';
import { Aluno } from 'src/app/models/aluno.model';

@Component({
  selector: 'app-aluno-detalhe',
  templateUrl: './aluno-detalhe.component.html',
  styleUrls: ['./aluno-detalhe.component.css']
})
export class AlunoDetalheComponent implements OnInit {

  aluno!: Aluno;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private studentService: StudentService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.studentService.buscarPorId(id).subscribe({
        next: (data) => this.aluno = data,
        error: (err) => {
          console.error(err);
          this.voltar();
        }
      });
    }
  }

  voltar(): void {
    this.router.navigate(['/home']);
  }
}