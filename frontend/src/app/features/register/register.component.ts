import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({selector:'app-register',standalone:true,imports:[CommonModule,ReactiveFormsModule,RouterLink],templateUrl:'./register.component.html'})
export class RegisterComponent {
  loading=false; errorMessage=''; successMessage='';
  form=this.fb.group({email:['',[Validators.required,Validators.email]],password:['',[Validators.required,Validators.minLength(8)]],confirmPassword:['',Validators.required]});
  constructor(private fb:FormBuilder,private auth:AuthService,private router:Router){}
  submit(){
    if(this.form.invalid){this.form.markAllAsTouched();return;}
    const {email,password,confirmPassword}=this.form.getRawValue();
    if(password!==confirmPassword){this.errorMessage='Passwords do not match';return;}
    this.loading=true;this.errorMessage='';
    this.auth.register(email!,password!).subscribe({next:r=>{this.loading=false;if(r.success){this.successMessage=r.message;setTimeout(()=>this.router.navigate(['/login']),800);}else this.errorMessage=r.message;},error:e=>{this.loading=false;this.errorMessage=e.error?.message||'Registration failed. Please try again.';}});
  }
}
