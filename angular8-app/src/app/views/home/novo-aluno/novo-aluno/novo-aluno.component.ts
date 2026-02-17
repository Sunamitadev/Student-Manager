import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { StudentService } from 'src/app/models/student.service';

@Component({
  selector: 'app-novo-aluno',
  templateUrl: './novo-aluno.component.html',
  styleUrls: ['./novo-aluno.component.css']
})
export class NovoAlunoComponent {

  // =========================
  // PROPRIEDADES
  // =========================

  nome: string = '';
  email: string = '';
  telefone: string = '';
  cpf: string = '';

  fotoSelecionada: File | null = null;
  previewUrl: string | ArrayBuffer | null = null;

  constructor(
    private studentService: StudentService,
    private router: Router
  ) {}

  // =========================
  // FORMATADORES
  // =========================

  formatarCpf(): void {
    if (!this.cpf) return;

    let numeros = this.cpf.replace(/\D/g, '');

    if (numeros.length > 11) {
      numeros = numeros.substring(0, 11);
    }

    numeros = numeros.replace(/(\d{3})(\d)/, '$1.$2');
    numeros = numeros.replace(/(\d{3})(\d)/, '$1.$2');
    numeros = numeros.replace(/(\d{3})(\d{1,2})$/, '$1-$2');

    this.cpf = numeros;
  }

  formatarTelefone(): void {
    if (!this.telefone) return;

    let numeros = this.telefone.replace(/\D/g, '');

    if (numeros.length > 11) {
      numeros = numeros.substring(0, 11);
    }

    numeros = numeros.replace(/(\d{2})(\d)/, '($1) $2');
    numeros = numeros.replace(/(\d{5})(\d)/, '$1-$2');

    this.telefone = numeros;
  }

  // =========================
  // VALIDAÇÃO
  // =========================

 formularioValido(): boolean {
  const cpfValido = this.cpf.replace(/\D/g, '').length === 11;
  const telefoneNumeros = this.telefone.replace(/\D/g, '');
  const telefoneValido = telefoneNumeros.length >= 10;

  return (
    this.nome.trim() !== '' &&
    this.email.trim() !== '' &&
    cpfValido &&
    telefoneValido &&
    this.fotoSelecionada !== null
  );
}

  // =========================
  // FOTO
  // =========================

  onSelecionarFoto(event: any): void {
    const file: File = event.target.files[0];

    if (file) {
      this.fotoSelecionada = file;

      const reader = new FileReader();
      reader.onload = () => {
        this.previewUrl = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  // =========================
  // AÇÕES
  // =========================

  cadastrar(): void {

  if (!this.formularioValido()) return;

  const formData = new FormData();

  formData.append('nome', this.nome.trim());
  formData.append('email', this.email.trim());
  formData.append('cpf', this.cpf.replace(/\D/g, ''));
  formData.append('telefone', this.telefone.replace(/\D/g, ''));
  formData.append('foto', this.fotoSelecionada as File);

  this.studentService.criarComFoto(formData).subscribe({
    next: () => {
      this.router.navigate(['/home']);
    },
    error: (err) => {
      console.error('Erro ao cadastrar aluno:', err);
    }
  });
}

  cancelar(): void {
    this.router.navigate(['/home']);
  }

}