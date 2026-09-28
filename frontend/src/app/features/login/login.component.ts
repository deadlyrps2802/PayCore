import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({selector:'app-login',standalone:true,imports:[CommonModule,ReactiveFormsModule,RouterLink],templateUrl:'./login.component.html',styleUrls:['./login.component.css']})
export class LoginComponent {
  loginForm: FormGroup; errorMessage=''; loading=false;
  constructor(private fb:FormBuilder,private authService:AuthService,private router:Router){
    if(this.authService.currentUserValue)this.router.navigate(['/dashboard']);
    this.loginForm=this.fb.group({email:['',[Validators.required,Validators.email]],password:['',Validators.required]});
  }
  onSubmit(){
    if(this.loginForm.invalid){this.loginForm.markAllAsTouched();return;}
    this.loading=true;this.errorMessage='';const {email,password}=this.loginForm.value;
    this.authService.login(email,password).subscribe({next:r=>{this.loading=false;if(r.success)this.router.navigate(['/dashboard']);else this.errorMessage=r.message||'Login failed.';},error:e=>{this.loading=false;this.errorMessage=e.error?.message||'Invalid email or password. Please try again.';}});
  }
  fillDemo(email:string){this.loginForm.patchValue({email,password:'Password123!'});}
}
