import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './views/home/home.component';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './views/login/login.component';
import { NovoAlunoComponent } from './views/home/novo-aluno/novo-aluno/novo-aluno.component';
import { AlunoDetalheComponent } from './views/home/aluno-detalhe/aluno-detalhe.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { StatusBadgeComponent } from './components/status-badge/status-badge.component';
import { SearchComponent } from './components/search/search.component';
import { AlunoRowComponent } from './components/aluno-row-component/aluno-row-component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    NovoAlunoComponent,
    AlunoDetalheComponent,
    PaginationComponent,
    StatusBadgeComponent,
    SearchComponent,
    AlunoRowComponent,
   
  ],
  imports: [
    BrowserModule,
    FormsModule, 
    ReactiveFormsModule,     
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
