import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Aluno } from './aluno.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private apiUrl = 'http://localhost:8080/alunos';

  constructor(private http: HttpClient) {}

  // =========================
  // TOKEN HEADER (SEM Content-Type)
  // =========================
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');

    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // =========================
  // LISTAR
  // =========================
  listar(): Observable<Aluno[]> {
    return this.http.get<Aluno[]>('http://localhost:8080/alunos/todos');
  }

  // =========================
  // CRIAR COM FOTO (MULTIPART)
  // =========================
  criarComFoto(formData: FormData): Observable<any> {
    return this.http.post(
      this.apiUrl,
      formData,
      { headers: this.getAuthHeaders() }
    );
  }
}