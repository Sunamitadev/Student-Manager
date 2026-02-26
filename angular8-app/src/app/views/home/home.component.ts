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
  

  loadingDeleteId = '';
  alunos: Aluno[] = [];
  alunoSelecionado: Aluno;
  filtro: string = '';

  paginaAtual = 0;
  totalPaginas = 0;
  ultimaPagina = false;

  constructor(
    private studentService: StudentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarAlunos();
    
  }


  carregarAlunos(): void {
    this.studentService.listar(this.paginaAtual).subscribe({
      next: (response: any) => {

        this.alunos = response.conteudo;
        this.totalPaginas = response.totalPaginas;
        this.ultimaPagina = response.ultima;
    console.log(response.conteudo[0]);
    console.log('Status :', response.conteudo[0].status);

      },
      error: (err) => console.error(err)
    });
  }

 verDetalhe(id: string): void {
  this.router.navigate(['/aluno-detalhe', id]);
}


 editarAluno(id: string): void {
  this.router.navigate(['/editar-aluno', id]);
}


apagarAluno(id: string) {
  // opcional: marque o ID em carregamento para desabilitar o botão
  this.loadingDeleteId = id;

  this.studentService.buscarPorId(id).subscribe({
    next: (aluno: Aluno) => {
      this.alunoSelecionado = aluno;

      // Só aqui (quando JÁ tem os dados) você confirma
      const confirmar = confirm(
        `Tem certeza que deseja excluir o aluno ${aluno.nome} (matrícula ${aluno.matricula || 's/ matrícula'})?`
      );

      if (!confirmar) {
        this.loadingDeleteId = null;
        return;
      }

      this.studentService.apagarAluno(id).subscribe({
        next: () => {
          // Atualiza a listagem após excluir
          this.carregarAlunos();
          this.loadingDeleteId = null;
        },
        error: (err) => {
          console.error('Erro ao excluir:', err);
          this.loadingDeleteId = null;
        }
      });
    },
    error: (err) => {
      console.error('Falha ao buscar aluno:', err);
      this.loadingDeleteId = null;
    }
  });
}
//*** */
atualizarFiltro(valor: string): void {
  this.filtro = valor;
}

  // FILTRO LOCAL
  get alunosFiltrados(): Aluno[] {

    if (!this.filtro.trim()) {
      return this.alunos;
    }

    const termo = this.filtro.toLowerCase();

    return this.alunos.filter(a =>
      a.nome.toLowerCase().includes(termo) ||
      (a.matricula && a.matricula.toLowerCase().includes(termo))
    );
  }

  irParaNovoAluno(): void {
    this.router.navigate(['/novo-aluno']);
  }

  irParaPagina(pagina: number): void {
    this.paginaAtual = pagina;
    this.carregarAlunos();
  }

  paginaAnterior(): void {
    if (this.paginaAtual > 0) {
      this.paginaAtual--;
      this.carregarAlunos();
    }
  }

  proximaPagina(): void {
    if (!this.ultimaPagina) {
      this.paginaAtual++;
      this.carregarAlunos();
    }
  }
  

  
}