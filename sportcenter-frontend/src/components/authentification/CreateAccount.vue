<template>
  <div id="mainContainer">
    <h1>Create a new account</h1>
    <VBox id="verticalContainer">
      <VBox id="verticalContainer">
        <input
          id="inputBox"
          type="text"
          placeholder="Username"
          v-model="username"
        />
        <input
          id="inputBox"
          type="text"
          placeholder="Password"
          v-model="password"
        />
        <button id="mainButton" @click="createAccount()">
          <b>Create account</b>
        </button>
      </VBox>
      <br />
      <VBox id="verticalContainer" style="width: 40%; align-self: center">
        <v-divider>Other options</v-divider>
        <button id="subButton" @click="goToLogin()">
          I already have an account
        </button>
        <button id="subButton" @click="goToForgotPassword()">
          Forgot password
        </button>
        <button id="subButton" @click="goToContinueAsGuest()">
          Continue as a guest
        </button>
      </VBox>
    </VBox>
  </div>
</template>

<script>
import axios from "axios";
import config from "../../../config";

const frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "CreateAccount",
  data() {
    return {
      username:'',
      password: '',
    };
  },
  methods: {
    async createAccount() {
      let response = '';
      let response2 = '';
      try {
        response = await AXIOS.post( "/createAccount/" + this.username + "/" + this.password );
        console.log(response.data);
      } catch (error) {
        alert("Problem creating account. Please try again.", error);
      }
      try {
        response2 = await AXIOS.post( "/createCustomer/" + this.username );
        console.log(response2.data);
        this.clearInputs();
      } catch (error) {
        alert("Problem creating customer. Please try again.");
      }
      try {
        console.log(response.status);
        console.log(response2.status);
        if (response.status == 201 && response2.status == 201) {
          this.setLoggedIn(true);
          this.setAccountType("Customer");
          this.setUsername(this.username);
          this.$router.push("/");
        }
      } catch (error) {
        alert(error.message);
      }
    },
    goToLogin() {
      this.$router.push("/app/auth/login");
    },
    goToForgotPassword() {
      this.$router.push("/app/auth/forgotpassword");
    },
    goToContinueAsGuest() {
      this.$accountType = "Guest";
      this.$router.push("/");
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
  },
};
</script>

<style scoped src="../../assets/main.css"></style>
