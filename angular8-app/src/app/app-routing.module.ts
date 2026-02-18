import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './views/login/login.component';
import { HomeComponent } from './views/home/home.component';
import { NovoAlunoComponent } from './views/home/novo-aluno/novo-aluno/novo-aluno.component';
import { AlunoDetalheComponent } from './views/home/aluno-detalhe/aluno-detalhe.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  {path: 'novo-aluno', component: NovoAlunoComponent  },
  {path: 'editar-aluno/:id', component: NovoAlunoComponent,  data: { mode: 'edit'} },
  {path: '', redirectTo: 'home', pathMatch:'full'},
{ path: 'aluno-detalhe/:id', component: AlunoDetalheComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
