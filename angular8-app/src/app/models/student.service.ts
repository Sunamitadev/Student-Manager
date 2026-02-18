
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

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');

    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // LISTAR PAGINADO
  listar(page: number): Observable<any> {
    return this.http.get<any>(
      `${this.apiUrl}?page=${page}`
    );
  }

  // CRIAR COM FOTO
  criarComFoto(formData: FormData): Observable<any> {
    return this.http.post(
      this.apiUrl,
      formData,
      { headers: this.getAuthHeaders() }
    );
  }

   atualizarComFoto(id: string, formData: FormData): Observable<any> {
    return this.http.put(
        `${this.apiUrl}/${id}`,
      formData,
      { headers: this.getAuthHeaders() }
    );
  }

 buscarPorId(id: string): Observable<Aluno> {
  return this.http.get<Aluno>(`${this.apiUrl}/${id}`);
}

 apagarAluno(id: string): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/${id}`);
}
}