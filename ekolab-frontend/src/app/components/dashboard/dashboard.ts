import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent {
  tarefas = [
    { id: 1, titulo: 'Estudar Angular', descricao: 'Praticar componentes e serviços', editando: false },
    { id: 2, titulo: 'Ler artigo', descricao: 'Ler artigo sobre boas práticas', editando: false }
  ];

  addTarefa() {
    const novaTarefa = {
      id: this.tarefas.length > 0 ? this.tarefas[this.tarefas.length - 1].id + 1 : 1,
      titulo: 'Nova tarefa',
      descricao: 'Descrição da nova tarefa',
      editando: false
    };
    this.tarefas.push(novaTarefa);
  }

  editarTarefa(tarefa: any) {
    tarefa.editando = true;
  }

  salvarTarefa(tarefa: any) {
    tarefa.editando = false;
  }

  removerTarefa(id: number) {
    this.tarefas = this.tarefas.filter(t => t.id !== id);
  }
}
