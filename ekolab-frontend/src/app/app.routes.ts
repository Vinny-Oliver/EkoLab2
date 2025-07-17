import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home';
import { LoginComponent } from './components/login/login';
import { CadastroComponent } from './components/cadastro/cadastro';
import { CursosComponent } from './components/cursos/cursos';
import { SobreComponent } from './components/sobre/sobre';
import { AdminComponent } from './components/admin/admin';
import { DashboardComponent } from './components/dashboard/dashboard'; // ajuste o caminho se necessário




export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'cadastro', component: CadastroComponent },
  { path: 'cursos', component: CursosComponent },
  { path: 'sobre', component: SobreComponent },
  { path: 'admin', component: AdminComponent },  
  { path: 'dashboard', component: DashboardComponent },
  { path: '**', redirectTo: '' }
];

