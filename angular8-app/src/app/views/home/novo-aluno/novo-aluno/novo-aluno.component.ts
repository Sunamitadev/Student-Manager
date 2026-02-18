import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  id: string = '';
  nome: string = '';
  email: string = '';
  telefone: string = '';
  cpf: string = '';


  fotoNome: string | null = null;
  fotoSelecionada: File | null = null;
  previewUrl: string | ArrayBuffer | null = null;
  trocouFoto = false;


  isEditando = false;
  status: string = '';
  
  constructor(
    private studentService: StudentService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private http: HttpClient
  ) {
    this.activatedRoute.data.subscribe(data => {
      this.isEditando = (data['mode'] === 'edit');
    });

  }

ngOnInit(): void {
  this.id = this.activatedRoute.snapshot.paramMap.get('id');

  if (this.isEditando && this.id) {
    this.studentService.buscarPorId(this.id).subscribe({
      next: (data: any) => {
        const { cpf, email, foto, nome, status, telefone } = data;

        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.status = status.toLowerCase() || '';
        this.telefone = telefone;
        if (foto) {
          this.fotoNome = foto;
          const url = this.buildUploadUrl(foto);
          this.previewUrl = url;

          this.carregarFotoExistenteComoFile(url, foto);
        }
      },
      error: (err) => {
        console.error(err);
        this.cancelar();
      }
    });
  }
}

private buildUploadUrl(nomeArquivo: string): string {
  return `http://localhost:8080/uploads/${nomeArquivo}`;
}


private carregarFotoExistenteComoFile(url: string, nomeArquivo: string): void {
  this.http.get(url, { responseType: 'blob'/*, withCredentials: true*/ }).subscribe({
    next: (blob) => {
      const tipo = blob.type || this.inferMimeFromName(nomeArquivo);
      this.fotoSelecionada = new File([blob], nomeArquivo, { type: tipo });
      this.trocouFoto = false; // ainda é a foto antiga (mas agora é um File)
    },
    error: (err) => {
      console.error('Falha ao carregar foto existente:', err);
      // Opcional: tratar fallback (ex.: impedir salvar sem foto)
    }
  });
}

private inferMimeFromName(nome: string): string {
  const ext = (nome.split('.').pop() || '').toLowerCase();
  switch (ext) {
    case 'jpg':
    case 'jpeg': return 'image/jpeg';
    case 'png':  return 'image/png';
    case 'gif':  return 'image/gif';
    case 'webp': return 'image/webp';
    default:     return 'application/octet-stream';
  }
}


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
    const telNums = this.telefone.replace(/\D/g, '');
    const telefoneValido = telNums.length >= 10;

    const temFoto = !!this.fotoSelecionada;    

    return (
      this.nome.trim() !== '' &&
      this.email.trim() !== '' &&
      cpfValido &&
      telefoneValido &&
      temFoto
    );
  }

  // =========================
  // FOTO
  // =========================

    onSelecionarFoto(event: any): void {
    const file: File = event.target.files[0];
    if (!file) return;

    this.fotoSelecionada = file;
    this.trocouFoto = true;

    const reader = new FileReader();
    reader.onload = () => (this.previewUrl = reader.result);
    reader.readAsDataURL(file);
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

 atualizar(): void {
  // Em edição, a validação permite manter a foto antiga
  if (!this.isEditando && !this.formularioValido()) return;

  
  if (!this.fotoSelecionada) {
    console.error('Sem fotoSelecionada — não deveria acontecer em edição.');
    return;
  }


  const formData = new FormData();
  formData.append('email', this.email.trim());
  formData.append('telefone', this.telefone.replace(/\D/g, ''));
  formData.append('status', this.status.toUpperCase());

  formData.append('foto', this.fotoSelecionada);

  this.studentService.atualizarComFoto(this.id, formData).subscribe({
    next: () => this.router.navigate(['/home']),
    error: (err) => console.error('Erro ao atualizar aluno:', err)
  });
}

  cancelar(): void {
    this.router.navigate(['/home']);
  }

}