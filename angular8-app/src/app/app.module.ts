import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './views/home/home.component';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './views/login/login.component';
import { NovoAlunoComponent } from './views/home/novo-aluno/novo-aluno/novo-aluno.component';
import { AlunoDetalheComponent } from './views/home/aluno-detalhe/aluno-detalhe.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    NovoAlunoComponent,
    AlunoDetalheComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,      
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
