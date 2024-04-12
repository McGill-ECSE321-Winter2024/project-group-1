<template>
    <div id="mainContainer">
        <h1><b>Password reset</b></h1>
        <VBox id="verticalContainer">
            <VBox id="verticalContainer">
                <input id="inputBox" type="username" placeholder="Existing username"></input>
                <input id="inputBox" type="password" placeholder="New password"></input>
                <button id="mainButton" @click="forgotPassword()"><b>Reset my password</b></button>
            </VBox>
            <br>
            <VBox id="verticalContainer" style="width: 40%; align-self: center;">
                <v-divider>Other options</v-divider>
                <button id="subButton" @click="goToLogin()"><b>I already have an account</b></button>
                <button id="subButton" @click="goToCreateAccount()"><b>Create a new account</b></button>
                <button id="subButton" @click="goToContinueAsGuest()">Continue as a guest</button>
            </VBox>
        </VBox>
    </div>
</template>

<script scoped>
    export default {
        name: 'ForgotPassword',
        methods: {
            forgotPassword() {
                alert("Forgot Password button clicked");
                console.log("Forgot Password button clicked");
            },
            goToCreateAccount() {
                this.$router.push('/app/auth/createaccount');
            },
            goToLogin() {
                this.$router.push('/app/auth/login');
            },
            goToContinueAsGuest() {
                this.$accountType = 'Guest';
                this.$router.push('/');
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
    }
</script>

<style scoped src="../../assets/main.css"></style>