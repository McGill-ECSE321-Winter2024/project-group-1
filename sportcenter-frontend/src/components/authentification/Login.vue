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
                <button id="subButton" @click="goToForgotPassword()"><b>Forgot password</b></button>
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


        loginCustomer() {
            try{
                const response = AXIOS.get('/login/' + this.username + '/' + this.password + '/customer');
                console.log(response.data);
                //alert(response.data.message);
                // Set user to logged in
                // this.$loggedIn = true;
                // this.$accountType = 'Customer';
                // this.$username = this.username;
                setLoggedIn('true');
                setAccountType('Customer');
                setUsername(this.username);
                this.clearInputs();
                this.$router.push('/');
            } catch(error){
                alert(error.message);
            }
        },

        loginInstructor() {
            try{
                const response = AXIOS.get('/login/' + this.username + '/' + this.password + '/instructor');
                console.log(response.data);
                // Set user to logged in
                this.$loggedIn = true;
                this.$accountType = 'Instructor';
                this.$username = this.username;
                LocalStorage.setItem('accountType',JSON.stringify(accountType));
                LocalStorage.setItem('username',JSON.stringify(username));
                this.clearInputs();
                this.$router.push('/');
            } catch(error){
                alert(error.message);
            }
        },
       
        loginOwner() {
            try{
                const response = AXIOS.get('/login/' + this.username + '/' + this.password + '/owner');
                console.log(response.data);
                // Set user to logged in
                this.$loggedIn = true;
                this.$accountType = 'Owner';
                this.$username = this.username;
                LocalStorage.setItem('accountType',JSON.stringify(accountType));
                LocalStorage.setItem('username',JSON.stringify(username));
                this.clearInputs();
                this.$router.push('/');
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
      return localStorage.getItem('loggedIn') === 'true';
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