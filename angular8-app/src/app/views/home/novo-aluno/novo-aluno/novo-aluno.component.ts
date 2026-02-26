import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentService } from 'src/app/models/student.service';

//  import para Reactive Forms
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-novo-aluno',
  templateUrl: './novo-aluno.component.html',
  styleUrls: ['./novo-aluno.component.css']
})
export class NovoAlunoComponent implements OnInit {

 
  //   usando FormGroup
  
  form!: FormGroup;

  id: string = '';

  fotoNome: string | null = null;
  fotoSelecionada: File | null = null;
  previewUrl: string | ArrayBuffer | null = null;
  trocouFoto = false;

  isEditando = false;

  constructor(
    //  injetado FormBuilder
    private fb: FormBuilder,
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

    //  criação do FormGroup
    this.form = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      cpf: ['', Validators.required],
      telefone: ['', Validators.required],
      status: ['ATIVO']
      
    });

    if (this.isEditando) {
  const nomeControl = this.form.get('nome');
  const cpfControl = this.form.get('cpf');

  if (nomeControl) {
    nomeControl.disable();
  }

  if (cpfControl) {
    cpfControl.disable();
  }
}



    this.id = this.activatedRoute.snapshot.paramMap.get('id');

    if (this.isEditando && this.id) {
      this.studentService.buscarPorId(this.id).subscribe({
        next: (data: any) => {

          const { cpf, email, foto, nome, status, telefone } = data;

          // usando patchValue
          this.form.patchValue({
            cpf,
            email,
            nome,
            status,
            telefone
          });

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
    this.http.get(url, { responseType: 'blob' }).subscribe({
      next: (blob) => {
        const tipo = blob.type || this.inferMimeFromName(nomeArquivo);
        this.fotoSelecionada = new File([blob], nomeArquivo, { type: tipo });
        this.trocouFoto = false;
      },
      error: (err) => {
        console.error('Falha ao carregar foto existente:', err);
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

  const cpfControl = this.form.get('cpf');
  if (!cpfControl) return;

  const cpf = cpfControl.value;
  if (!cpf) return;

  let numeros = cpf.replace(/\D/g, '');

  if (numeros.length > 11) {
    numeros = numeros.substring(0, 11);
  }

  numeros = numeros.replace(/(\d{3})(\d)/, '$1.$2');
  numeros = numeros.replace(/(\d{3})(\d)/, '$1.$2');
  numeros = numeros.replace(/(\d{3})(\d{1,2})$/, '$1-$2');

  cpfControl.setValue(numeros);
}

  formatarTelefone(): void {

  const telefoneControl = this.form.get('telefone');
  if (!telefoneControl) return;

  const telefone = telefoneControl.value;
  if (!telefone) return;

  let numeros = telefone.replace(/\D/g, '');

  if (numeros.length > 11) {
    numeros = numeros.substring(0, 11);
  }

  numeros = numeros.replace(/(\d{2})(\d)/, '($1) $2');
  numeros = numeros.replace(/(\d{5})(\d)/, '$1-$2');

  telefoneControl.setValue(numeros);
}

get formularioCompleto(): boolean {
  return this.form.valid && !!this.fotoSelecionada;
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

    //usando form.invalid
    if (!this.formularioCompleto) return;
    

    const formValue = this.form.value;

    const formData = new FormData();
    formData.append('nome', formValue.nome.trim());
    formData.append('email', formValue.email.trim());
    formData.append('cpf', formValue.cpf.replace(/\D/g, ''));
    formData.append('telefone', formValue.telefone.replace(/\D/g, ''));
    formData.append('foto', this.fotoSelecionada as File);

    

    this.studentService.criarComFoto(formData).subscribe({
      next: () => this.router.navigate(['/home']),
      error: (err) => console.error('Erro ao cadastrar aluno:', err)
    });
  }

  atualizar(): void {

    if (this.form.invalid) return;

    if (!this.fotoSelecionada) {
      console.error('Sem fotoSelecionada.');
      return;
    }

    const formValue = this.form.value;

    const formData = new FormData();
    formData.append('email', formValue.email.trim());
    formData.append('telefone', formValue.telefone.replace(/\D/g, ''));
    formData.append('status', formValue.status);
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