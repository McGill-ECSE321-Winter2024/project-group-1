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
      username: null,
      password: null,
    };
  },
  methods: {
    async createAccount() {
      try {
        const response = await AXIOS.post(
          "/createAccount/" + this.username + "/" + this.password
        );
      } catch (error) {
        console.error("Error fetching accounts", error.message);
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
  },
};
</script>

<style scoped src="../../assets/main.css"></style>
