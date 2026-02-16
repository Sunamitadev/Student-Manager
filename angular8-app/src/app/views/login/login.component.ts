import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../models/service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  usuario: string = '';
  senha: string = '';
  erro ='';

  isValido(valor: string): boolean {
    if (valor.length < 8 ){
      return false;
    }

    const temLetra  = /[a-zA-Z]/.test(valor);
    const temNumero = /[0-9]/.test(valor);

    return temLetra && temNumero;
  }
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  entrar() {
    this.authService.login(this.usuario, this.senha)
      .subscribe({
        next: (response) => {
          console.log(response.token);

          // salvar token
          localStorage.setItem('token', response.token);

          // redirecionar
          this.router.navigate(['/home']);
        },
        error: () => {
          this.erro = 'Usuário ou senha inválidos';
        }
      });
  }

  ngOnInit() {

    //this.loginData = {email: '', password: ''};

    //if (localStorage.getItem('loginData')) {
    
  }

}
