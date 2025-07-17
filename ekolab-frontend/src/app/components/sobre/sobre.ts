import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-sobre',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './sobre.html',
  styleUrls: ['./sobre.css']
})
export class SobreComponent implements OnInit {
  contatoForm!: FormGroup;
  isLoading = false;
  successMessage = '';
  errorMessage = '';

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.initializeForm();
  }

  private initializeForm() {
    this.contatoForm = this.formBuilder.group({
      nome: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      assunto: ['', [Validators.required, Validators.minLength(5)]],
      mensagem: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  enviarMensagem() {
    if (this.contatoForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';
      this.successMessage = '';

      // Simular envio da mensagem
      setTimeout(() => {
        this.isLoading = false;
        this.successMessage = 'Mensagem enviada com sucesso! Entraremos em contato em breve.';
        this.contatoForm.reset();
      }, 2000);

      // Aqui seria feita a integração com o backend para enviar o email
      console.log('Dados do formulário:', this.contatoForm.value);
    } else {
      this.markFormGroupTouched();
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.contatoForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  private markFormGroupTouched() {
    Object.keys(this.contatoForm.controls).forEach(key => {
      const control = this.contatoForm.get(key);
      control?.markAsTouched();
    });
  }
}

