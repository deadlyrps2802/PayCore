import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({selector:'app-register',standalone:true,imports:[CommonModule,ReactiveFormsModule,RouterLink],templateUrl:'./register.component.html'})
export class RegisterComponent {
  loading=false; errorMessage=''; successMessage='';
  form=this.fb.group({
    fullName:['',[Validators.required, Validators.minLength(2)]],
    dateOfBirth:['',Validators.required],
    email:['',[Validators.required,Validators.email,Validators.pattern(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)]],
    password:['',[Validators.required,Validators.minLength(6)]],
    confirmPassword:['',Validators.required]
  });
  constructor(private fb:FormBuilder,private auth:AuthService,private router:Router){}

  submit(){
    if(this.form.invalid){this.form.markAllAsTouched();return;}
    const {fullName,dateOfBirth,email,password,confirmPassword}=this.form.getRawValue();
    const dob=new Date(dateOfBirth!); const today=new Date();
    let age=today.getFullYear()-dob.getFullYear();
    const month=today.getMonth()-dob.getMonth();
    if(month<0 || (month===0 && today.getDate()<dob.getDate())) age--;
    if(age<18){this.errorMessage='You must be at least 18 years old to register';return;}
    const domain=(email||'').split('@')[1]?.toLowerCase() || '';
    const personal=['gmail.com','yahoo.com','hotmail.com','outlook.com','live.com','icloud.com'];
    if(personal.includes(domain)){this.errorMessage='Please use your company email address';return;}
    if(password!==confirmPassword){this.errorMessage='Passwords do not match';return;}
    this.loading=true;this.errorMessage='';
    this.auth.register(email!,password!,fullName!,dateOfBirth!).subscribe({next:r=>{this.loading=false;if(r.success){this.successMessage=r.message;setTimeout(()=>this.router.navigate(['/login']),800);}else this.errorMessage=r.message;},error:e=>{this.loading=false;this.errorMessage=e.error?.message||'Registration failed. Please try again.';}});
  }
}
