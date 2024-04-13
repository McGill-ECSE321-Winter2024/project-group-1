<template>
    <div id="mainContainer">
        <h1>Login</h1>
        <VBox id="verticalContainer">
            <VBox id="verticalContainer">
                <input id="inputBox" type="text" placeholder="Username" v-model="username"></input>
                <input id="inputBox" type="password" placeholder="Password" v-model="password"></input>
                <HBox id="horizontalContainer">
                    <button id="mainButton" @click="loginCustomer()"><b>Login as Customer</b></button>
                    <button id="mainButton" @click="loginInstructor()"><b>Login as Instructor</b></button>
                    <button id="mainButton" @click="loginOwner()"><b>Login as Owner</b></button>
                </HBox>
            </VBox>
            <br>
            <VBox id="verticalContainer" style="width: 40%; align-self: center;">
                <v-divider>Other options</v-divider>
                <button id="subButton" @click="goToCreateAccount()"><b>Create a new account</b></button>
                <button id="subButton" @click="goToContinueAsGuest()">Continue as a Guest</button>
            </VBox>
        </VBox>
    </div>
</template>

<script>
import axios from "axios";
import config from "../../../config";

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: "Login",
    data () {
        return {
            username: null,
            password: null
        };
    },
    methods: {


        async loginCustomer() {
            try{
                const response = await AXIOS.get('/login/' + this.username + '/' + this.password + '/customer');
                console.log(response.data);
                
              if (response.status == 200) {
                this.setLoggedIn(true);
                this.setAccountType('Customer');
                this.setUsername(this.username);


                  //Set up ID

                  let id = '0';
                  const responseForId = await AXIOS.get('/getAllAccounts');
                  console.log(responseForId.data);

                  for (const account of responseForId.data) {

                    if (account.username === this.username && account.password === this.password) {
                      id = account.accountId;
                      break; // Exit the loop once we find a matching account
                    }
                  }

                  this.setAccountId(id);                 



                this.clearInputs();
                this.$router.push('/');
              }  else {
                // Handle unsuccessful login
                console.log('Login unsuccessful');
              } 


            } catch(error){
                alert(error.message);
            }

        },

        async loginInstructor() {
          try{
                const response = await AXIOS.get('/login/' + this.username + '/' + this.password + '/instructor');
                console.log(response);

                //console.log(response.status);

                if (response.status == 200) {
                  this.setLoggedIn(true);
                  this.setAccountType('Instructor');
                  this.setUsername(this.username);

                  //Set up ID

                  let id = '0';
                  const responseForId = await AXIOS.get('/getAllAccounts');
                  console.log(responseForId.data);

                  for (const account of responseForId.data) {

                    if (account.username === this.username && account.password === this.password) {
                      id = account.accountId;
                      break; // Exit the loop once we find a matching account
                    }
                  }

                  this.setAccountId(id); 
                    

                  this.clearInputs();
                  this.$router.push('/');
                }  else {
                // Handle unsuccessful login
                console.log('Login unsuccessful');
                } 

            } catch(error){
                alert(error.message);
            }
        },
       
        async loginOwner() {
          try{
                const response = await AXIOS.get('/login/' + this.username + '/' + this.password + '/owner');
                //console.log(response.data);


              if (response.status == 200) {
                this.setLoggedIn(true);
                this.setAccountType('Owner');
                this.setUsername(this.username);

                  //Set up ID

                  let id = '0';
                  const responseForId = await AXIOS.get('/getAllAccounts');

                  for (const account of responseForId.data) {

                    if (account.username === this.username && account.password === this.password) {
                      id = account.accountId;
                      break; // Exit the loop once we find a matching account
                    }
                  }

                  this.setAccountId(id); 



                this.clearInputs();
                this.$router.push('/');
              }  else {
                // Handle unsuccessful login
                console.log('Login unsuccessful');
              } 

            } catch(error){
                alert(error.message);
            }


        },

        goToForgotPassword() {
            this.$router.push('/app/auth/forgotpassword');
        },
        goToCreateAccount() {
            this.$router.push('/app/auth/createaccount');
        },
        goToContinueAsGuest() {
            this.$accountType = 'Guest';
            this.$username = 'JoeMama';
            this.$loggedIn = false;
            this.$router.push('/');
        },
        clearInputs() {
            this.username = null;
            this.password = null;
        },
            getAccountType() {
      return localStorage.getItem('accountType');
    },

    setAccountId(id) {
      localStorage.setItem('id', id);
    },
    getAccountId() {
      return localStorage.getItem('id');
    },
    setAccountType(accountType) {
      localStorage.setItem('accountType', accountType);
    },
    getUsername() {
      return localStorage.getItem('username');
    },
    setUsername(username) {
      localStorage.setItem('username', username);
    },
    getLoggedIn() {
      return localStorage.getItem('loggedIn');
    },
    setLoggedIn(loggedIn) {
      localStorage.setItem('loggedIn', loggedIn);
    },
    getTime() {
      return localStorage.getItem('time');
    },
    setTime(time) {
      localStorage.setItem('time', time);
    },
    getDebuggingMode() {
      return localStorage.getItem('debugging_mode') === 'true';
    },
    setDebuggingMode(debugging_mode) {
      localStorage.setItem('debugging_mode', debugging_mode);
    },
    getLanguage() {
      return localStorage.getItem('language');
    },
    setLanguage(language) {
      localStorage.setItem('language', language);
    },
    getDarkMode() {
      return localStorage.getItem('dark_mode') === 'true';
    },
    setDarkMode(dark_mode) {
      localStorage.setItem('dark_mode', dark_mode);
    },



    }
};

</script>

<style scoped src="../../assets/main.css"></style>