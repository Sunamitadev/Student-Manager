import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

interface TokenResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private api = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  login(usuario: string, senha: string): Observable<TokenResponse> {
    return this.http.post<TokenResponse>(`${this.api}/login`, {
      usuario,
      senha
    });
  }
}
