import { Component, OnInit } from '@angular/core';
import {Agency, LoginInfo} from "../../model/agency";
import {FormBuilder, FormGroup} from "@angular/forms";
import {AuthenticationService} from "../../service/authentication.service";
import {lastValueFrom} from "rxjs";
import {environment} from "../../../environments/environment";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-login-registration',
  templateUrl: './login-registration.component.html',
  styleUrls: ['./login-registration.component.css']
})
export class LoginRegistrationComponent implements OnInit {

  private agencyId!: string;
  private token!: string;
  showLogin: boolean = true;
  agency: Agency = new Agency();
  loginInfo: LoginInfo = new LoginInfo();
  loginData: any;
  form: FormGroup = new FormGroup({});
  isRegistered: boolean = false;

  constructor(private formBuilder: FormBuilder,private authenticationService: AuthenticationService, private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  async loginUser() {
    if (this.loginInfo.mail === '' || this.loginInfo.password === '') {
      //this.toastr.error('Please fill in all fields!');
      alert('Please fill in all fields!')
    } else {
      await this.login();
    }
  }

  async login() {
    try {
      const data = await lastValueFrom(
        this.authenticationService.loginUser(this.loginInfo)
      );
      console.log(data);
      if (data == null) {
        //this.toastr.error('Login failed', 'Upss..');
        alert('Login failed')
        return;
      }
      this.loginData = data;
      console.log(this.loginData)
      window.location.href = `${environment.psp_front_url}/list-subscriptions?agencyId=${this.loginData.agencyId}&token=${this.loginData.token}`;

    } catch (error) {
      //this.toastr.error('Wrong username or password', 'Upss..');
      alert('Wrong username or password')
    }
  }


  registerClick() {
    this.showLogin = false;
  }

  registerUser() {
    console.log(this.agency)
    if(this.agency.name == ''){
      alert('Please enter all fields.');
      return;
    }
    this.isRegistered = true;

    this.authenticationService.registerUser(this.agency).subscribe({
      next: (data) => {
        //this.toastr.success('Activation email is sent', 'Info');
        alert('register')
        this.isRegistered = false;
        window.location.href = environment.psp_front_url+ '/login';
      },
      error: (err) => {
        if (err.status == 400)
          // this.toastr.error('Use a stronger password.', 'Upss..');
          alert('Use a stronger password.')
        else //this.toastr.error('Something went wrong', 'Upss..');
          alert('Something went wrong')
        this.isRegistered = false;
      },
    });
  }


}
